package com.chenm.microservice.elasticsearch.Service;

import java.util.List;

public interface SearchAnalyzeService {

	public List<String> getAnalyzeTerms(String keywordsToSearch, String string);
}
