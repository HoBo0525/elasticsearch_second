package com.atguigu.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hobo
 * @create 2020-12-03 11:23
 */
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchConfig {
    private String host;
    private Integer port;

    public ElasticsearchConfig() {
    }

    public ElasticsearchConfig(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ElasticsearchConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }


    @Bean
    public RestHighLevelClient client(){
       return new RestHighLevelClient(RestClient.builder(new HttpHost(host,port, "http")));
    }
}
