package com.chenm.microservice.elasticsearch.config;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

@org.springframework.context.annotation.Configuration
public class HbaseConfiguration implements FactoryBean<Admin>, InitializingBean, DisposableBean{
	private static final Logger logger = LoggerFactory.getLogger(HbaseConfiguration.class);
	
	private Admin admin;
	public static Configuration conf;
	public static Connection conn;

	@Override
	public Admin getObject() throws Exception {
		return admin;
	}

	@Override
	public Class<Admin> getObjectType() {
		return Admin.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Configuration conf = HBaseConfiguration.create();
//		conf.set("hbase.zookeeper.quorum","192.168.20.128:2181");
		conf.set("hbase.rootdir", "192.168.20.128:9000/hbase");
		conf.set("hbase.zookeeper.quorum", "192.168.20.128");
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		try {
			conn = ConnectionFactory.createConnection(conf);
			admin = conn.getAdmin();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() throws Exception {
		try {
			logger.info("closing elasticsearch client");
			if(admin != null){
				admin.close();
			}
			if(conn != null){
				conn.close();
			}
		} catch (final Exception e) {
			logger.error("error closing Hbase client", e);
		}
	}

}
