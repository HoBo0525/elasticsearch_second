package com.atguigu;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hobo
 * @create 2020-12-03 14:21
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocText {
    @Autowired
    RestHighLevelClient client;

    //使用Map对象 添加Doc字段
    @Test
    public void addDoc() throws Exception{
        //创建Map对象
        Map map = new HashMap();
        map.put("address", "黑龙江庆安");
        map.put("age", 18);
        map.put("name", "小范同学");

        //获取操作响应对象
        IndexRequest request = new IndexRequest("try").id("1").source(map);

        //具体操作
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());

    }

    //使用对象 添加Doc字段
    @Test
    public void addDoc2() throws Exception{
        //创建添加对象
        Person p = new Person("2", "黑龙江哈尔滨", 22, "小小同学");
        //把对象转化为JSON
        String data = JSON.toJSONString(p);

        //获取添加操作响应对象
        IndexRequest request = new IndexRequest("try").id(p.getId()).source(data, XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());
    }

    //修改Doc  有id是修改操作  无id是增加操作
    @Test
    public void updateDoc() throws Exception{
        //创建对象
        Person p1 = new Person("3", "黑龙江", 50, "同学");
        Person p2 = new Person("2", "绥化庆安", 45, "小小同学");

        //把对象转化为JSON
        String data1 = JSON.toJSONString(p1);
        String data2 = JSON.toJSONString(p2);

        //获取添加操作响应对象
        IndexRequest request1 = new IndexRequest("try").id(p1.getId()).source(data1, XContentType.JSON);
        IndexRequest request2 = new IndexRequest("try").id(p2.getId()).source(data2, XContentType.JSON);

        //具体操作
        IndexResponse response1 = client.index(request1, RequestOptions.DEFAULT);
        IndexResponse response2 = client.index(request2, RequestOptions.DEFAULT);

        System.out.println("response1 = " + response1.getId());
        System.out.println("response2 = " + response2.getId());
    }


    //删除Doc
    @Test
    public void deleteDoc() throws Exception{
        DeleteRequest request = new DeleteRequest("try").id("3");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.getId());
        System.out.println(response.getResult());
    }

    //查询Doc
    @Test
    public void findDoc() throws Exception{
        GetRequest request = new GetRequest("try").id("2");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
    }


    //批量操作
    @Test
    public void bulkDoc() throws Exception{
        //获取批量操作对象
        BulkRequest request = new BulkRequest();
        //添加操作
        Person p = new Person("5", "黑龙江xxx", 50, "xxx同学");
        String data = JSON.toJSONString(p);
        IndexRequest indexRequest = new IndexRequest("try").id(p.getId()).source(data, XContentType.JSON);
        request.add(indexRequest);

        //修改操作
        Person p1 = new Person("2", "黑龙江xxx", 50, "xxx同学");
        String data1 = JSON.toJSONString(p1);
        IndexRequest indexRequest1 = new IndexRequest("try").id(p1.getId()).source(data1, XContentType.JSON);
        request.add(indexRequest1);

        //删除操作
        DeleteRequest deleteRequest = new DeleteRequest("try").id("3");
        request.add(deleteRequest);

        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.status());


    }
}
