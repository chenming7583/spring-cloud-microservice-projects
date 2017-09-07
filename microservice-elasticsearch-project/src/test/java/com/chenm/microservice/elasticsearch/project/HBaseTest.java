package com.chenm.microservice.elasticsearch.project;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //SpringJUnit支持，由此引入Spring-Test框架支持
@SpringBootTest(classes=ElasticSearchApplication.class)
public class HBaseTest {
	
	private static final String TABLE_NAME = "test";

    private static final String ROW_KEY = "row1";

    private static final String COLUMN_FAMILY = "cf";

    private static final String QUALIFIER = "a";
    
    @Autowired
    private Admin hbaseAdmin;
    
    @Test
    public void test(){
    	try {
			createTable("doc","cf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void createTable(String tableName, String column) throws Exception {
		if(hbaseAdmin.tableExists(TableName.valueOf(tableName))){
			System.out.println(tableName+"表已经存在！");
		}else{
			HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
			tableDesc.addFamily(new HColumnDescriptor(column.getBytes()));
			hbaseAdmin.createTable(tableDesc);
			System.out.println(tableName+"表创建成功！");
		}
	}

}
