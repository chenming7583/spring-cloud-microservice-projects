package com.chenm.microservice.elasticsearch.Service;

import javax.naming.directory.SearchResult;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElasticsearchServiceImpl implements ElasticsearchService {
	
	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
	
	private SearchAnalyzeService searchAnalyzeService;

	@Override
	public JSONObject recommend(SearchResult searchResult, String queryWord) {
		/*try {
	        String keywordsToSearch = queryWord;
	        // 搜索推荐分两部分
	        // 1) 第一部分是最常见的情况，包括有结果、根据SKN搜索、关键词未出现在空结果Redis ZSet里
	        if (containsProductInSearchResult(searchResult)) {
	            // 1.1） 搜索有结果的 优先从搜索结果聚合出品牌等关键词进行查询
	            String aggKeywords = aggKeywordsByProductList(searchResult);
	            keywordsToSearch = queryWord + " " + aggKeywords;
	        } else if (isQuerySkn(queryWord)) {
	            // 1.2） 如果是查询SKN 没有查询到的 后续的逻辑也无法推荐 所以直接到ES里去获取关键词
	            keywordsToSearch = aggKeywordsBySkns(queryWord);
	            if (StringUtils.isEmpty(keywordsToSearch)) {
	                return defaultSuggestRecommendation();
	            }
	        }
	        Double count = searchKeyWordService.getKeywordCount(RedisKeys.SEARCH_KEYWORDS_EMPTY, queryWord);
	        if (count == null || queryWord.length() >= 5) {
	            // 1.3) 如果该关键词一次都没有出现在空结果列表或者长度大于5 则该词很有可能是可以搜索出结果的
	            //      因此优先取suggest_index去搜索一把 减少后面的查询动作
	            JSONObject recommendResult = recommendBySuggestIndex(queryWord, keywordsToSearch, false);
	            if (isNotEmptyResult(recommendResult)) {
	                return recommendResult;
	            }
	        }
	        // 2) 第二部分是通过Conversion和拼写纠错去获取关键词 由于很多品牌的拼写可能比较相近 因此先走Conversion然后再拼写检查
	        String spellingCorrentWord = null, dest = null;
	        if (allowGetingDest(queryWord) && StringUtils.isNotEmpty((dest = getSuggestConversionDestBySource(queryWord)))) {
	            // 2.1) 爬虫和自定义的Conversion处理
	            keywordsToSearch = dest;
	        } else if (allowSpellingCorrent(queryWord) 
			         && StringUtils.isNotEmpty((spellingCorrentWord = suggestService.getSpellingCorrectKeyword(queryWord)))) {
	            // 2.2) 执行拼写检查 由于在搜索建议的时候会进行拼写检查 所以缓存命中率高
	            keywordsToSearch = spellingCorrentWord;
	        } else {
	            // 2.3) 如果两者都没有 则直接返回
	            return defaultSuggestRecommendation();
	        }
	        JSONObject recommendResult = recommendBySuggestIndex(queryWord, keywordsToSearch, dest != null);
	        return isNotEmptyResult(recommendResult) ? recommendResult : defaultSuggestRecommendation();
	    } catch (Exception e) {
	        logger.error("[func=recommend][queryWord=" + queryWord + "]", e);
	        return defaultSuggestRecommendation();
	    }
	}
	
	private JSONObject recommendBySuggestIndex(String srcQueryWord, String keywordsToSearch, boolean isLimitKeywords) {
	    // 1) 先对keywordsToSearch进行分词
	    List<String> terms = null;
	    if (isLimitKeywords) {
	        terms = Arrays.stream(keywordsToSearch.split(",")).filter(term -> term != null && term.length() > 1)
	                      .distinct().collect(Collectors.toList());
	    } else {
	        terms = searchAnalyzeService.getAnalyzeTerms(keywordsToSearch, "ik_smart");
	    }
	    if (CollectionUtils.isEmpty(terms)) {
	        return new JSONObject();
	    }
	    // 2) 根据terms搜索构造搜索请求
	    SearchParam searchParam = new SearchParam();
	    searchParam.setPage(1);
	    searchParam.setSize(3);
	    // 2.1) 构建FunctionScoreQueryBuilder
	    QueryBuilder queryBuilder = isLimitKeywords ? buildQueryBuilderByLimit(terms)
	                                  : buildQueryBuilder(keywordsToSearch, terms);
	    searchParam.setQuery(queryBuilder);
	    
	    // 2.2) 设置过滤条件
	    BoolQueryBuilder boolFilter = QueryBuilders.boolQuery();
	    boolFilter.must(QueryBuilders.rangeQuery("count").gte(20));
	    boolFilter.mustNot(QueryBuilders.termQuery("keyword.keyword_lowercase", srcQueryWord.toLowerCase()));
	    if (isLimitKeywords) {
	        boolFilter.must(QueryBuilders.termsQuery("keyword.keyword_lowercase", terms.stream()
	            .map(String::toLowerCase).collect(Collectors.toList())));
	    }
	    searchParam.setFiter(boolFilter);
	    // 2.3) 按照得分、权重、数量的规则降序排序
	    List<SortBuilder> sortBuilders = new ArrayList<>(3);
	    sortBuilders.add(SortBuilders.fieldSort("_score").order(SortOrder.DESC));
	    sortBuilders.add(SortBuilders.fieldSort("weight").order(SortOrder.DESC));
	    sortBuilders.add(SortBuilders.fieldSort("count").order(SortOrder.DESC));
	    searchParam.setSortBuilders(sortBuilders);
	    // 4) 先从缓存中获取
	    final String indexName = SearchConstants.INDEX_NAME_SUGGEST;
	    JSONObject suggestResult = searchCacheService.getJSONObjectFromCache(indexName, searchParam);
	    if (suggestResult != null) {
	        return suggestResult;
	    }
	    // 5) 调用ES执行搜索
	    SearchResult searchResult = searchCommonService.doSearch(indexName, searchParam);
	    // 6) 构建结果加入缓存
	    suggestResult = new JSONObject();
	    List<String> resultTerms = searchResult.getResultList().stream()
	            .map(map -> (String) map.get("keyword")).collect(Collectors.toList());
	    suggestResult.put("search_recommendation", resultTerms);
	    searchCacheService.addJSONObjectToCache(indexName, searchParam, suggestResult);
	    return suggestResult;
	}
	
	private QueryBuilder buildQueryBuilderByLimit(List<String> terms) {
	    FunctionScoreQueryBuilder functionScoreQueryBuilder
	        = new FunctionScoreQueryBuilder(QueryBuilders.matchAllQuery());
	    // 给品类类型的关键词加分
	    functionScoreQueryBuilder.add(QueryBuilders.termQuery("type", Integer.valueOf(2)),
	        ScoreFunctionBuilders.weightFactorFunction(3));
	    // 按词出现的顺序加分
	    for (int i = 0; i < terms.size(); i++) {
	        functionScoreQueryBuilder.add(QueryBuilders.termQuery("keyword.keyword_lowercase", 
	   terms.get(i).toLowerCase()),
	            ScoreFunctionBuilders.weightFactorFunction(terms.size() - i));
	    }
	    functionScoreQueryBuilder.boostMode(CombineFunction.SUM);
	    return functionScoreQueryBuilder;
	}
	
	private QueryBuilder buildQueryBuilder(String keywordsToSearch, Set<String> termSet) {
	    // 1) 对于suggest的multi-fields至少要有一个字段匹配到 匹配得分为常量1
	    MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keywordsToSearch.toLowerCase(),
	            "keyword.keyword_ik", "keyword.keyword_pinyin", 
	            "keyword.keyword_first_py", "keyword.keyword_lowercase")
	        .analyzer("ik_smart")
	        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS)
	        .operator(Operator.OR)
	        .minimumShouldMatch("1");
	    FunctionScoreQueryBuilder functionScoreQueryBuilder
	        = new FunctionScoreQueryBuilder(QueryBuilders.constantScoreQuery(queryBuilder));
				
	    for (String term : termSet) {
	        // 2) 对于完全匹配Term的加1分
	        functionScoreQueryBuilder.add(QueryBuilders.termQuery("keyword.keyword_lowercase", term.toLowerCase()),
	            ScoreFunctionBuilders.weightFactorFunction(1));
	        // 3) 对于匹配到一个Term的加2分
	        functionScoreQueryBuilder.add(QueryBuilders.termQuery("keyword.keyword_ik", term),
	            ScoreFunctionBuilders.weightFactorFunction(2));
	    }
	    functionScoreQueryBuilder.boostMode(CombineFunction.SUM);
	    return functionScoreQueryBuilder;
	    */
		return null;
	}

}
