package com.famargon.tgd.warehouse.nosql.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class MongoDBConfiguration {

	//mongodb://<dbuser>:<dbpassword>@ds133017.mlab.com:33017/warehouse
	//warehouseadmin
	
    @Bean
    public Mongo mongo() throws Exception {
//        return new MongoClient("localhost");
    	return new MongoClient(new ServerAddress("ds133017.mlab.com",33017),
    			Arrays.asList(MongoCredential.createCredential("warehouseadmin", "warehouse", "warehouseadmin".toCharArray())));
    }
    
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "warehouse");
    }
	
}
