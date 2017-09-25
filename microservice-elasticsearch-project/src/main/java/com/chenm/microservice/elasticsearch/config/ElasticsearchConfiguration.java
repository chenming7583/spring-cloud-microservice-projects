package com.chenm.microservice.elasticsearch.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ElasticsearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);
	
	@Value("${spring.data.elasticsearch.cluster-nodes}")
	private String clusterNodes;
	
	@Value("${spring.data.elasticsearch.cluster-name}")
	private String clusterName;
	
	private TransportClient transportClient;
	
	private PreBuiltTransportClient preBuiltTransportClient;
	
	@Override
	public void destroy() throws Exception {
		try {
			logger.info("closing elasticsearch client");
			if(transportClient != null){
				transportClient.close();
			}
		} catch (final Exception e) {
			logger.error("error closing elasticsearch client", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		buildClient();
	}
	
	/**
	 * 构建client
	 */
	protected void buildClient() {
		try {
			preBuiltTransportClient = new PreBuiltTransportClient(settings());
			String InetSocket[] = clusterNodes.split(":");
			String address = InetSocket[0];
			Integer port = Integer.valueOf(InetSocket[1]);
			transportClient = preBuiltTransportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(address), port));
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 初始化默认client配置
	 * @return
	 */
	private Settings settings() {
		/**
		 * cluster.name:集群名称
		 * client.transport.sniff:
		 * 		客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
		 * 		这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，
		 * 		 它会自动帮你添加，并且自动发现新加入集群的机器
		 * 	  注意：当ES服务器监听使用内网服务器IP而访问使用外网IP时，
		 * 		不要使用client.transport.sniff为true，
		 * 		在自动发现时会使用内网IP进行通信，导致无法连接到ES服务器，
		 * 		而直接使用addTransportAddress方法进行指定ES服务器
		 */
//		Settings settings = Settings.builder().put("cluster.name","clusterName").put("client.transport.sniff",true).build();
		Settings settings = Settings.builder().put("cluster.name", clusterName)
				//.put("client.transport.ignore_cluster_name",true) // 设置为true时忽略集群名验证，忽略集群名字验证, 打开后集群名字不对也能连接上
				//.put("client.transport.nodes_sampler_interval", 5) // 节点之间互相ping，互连检测时间间隔
				//.put("client.transport.ping_timeout", 5) // 等待ping命令返回结果时间，默认为5秒
				.build();
		
		return settings;
	}

	@Override
	public TransportClient getObject() throws Exception {
		return transportClient;
	}

	@Override
	public Class<TransportClient> getObjectType() {
		return TransportClient.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
