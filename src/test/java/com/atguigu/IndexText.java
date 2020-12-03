package com.atguigu;


import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

/**
 * @author Hobo
 * @create 2020-12-03 11:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexText {
    @Autowired
    RestHighLevelClient client;

    //测试client注入是否成功
    @Test
    public void clientTest(){
        System.out.println(client);
    }

    //添加索引
    @Test
    public void addIndex() throws Exception{
        //获取操作索引的对象
        IndicesClient indices = client.indices();

        //具体操作 获取返回值
        CreateIndexRequest request = new CreateIndexRequest("yeah");

        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);

        System.out.println(response.isAcknowledged());

    }

    //添加索引和映射
    @Test
    public void addIndexAndMapping() throws Exception{
        //获取操作索引的对象
        IndicesClient indices = client.indices();
        //获取具体操作返回值
        CreateIndexRequest request = new CreateIndexRequest("goods");

        String mapping = "{\n" +
                "      \"properties\" : {\n" +
                "        \"address\" : {\n" +
                "          \"type\" : \"text\",\n" +
                "          \"analyzer\" : \"ik_max_word\"\n" +
                "        },\n" +
                "        \"age\" : {\n" +
                "          \"type\" : \"long\"\n" +
                "        },\n" +
                "        \"name\" : {\n" +
                "          \"type\" : \"keyword\"\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        request.mapping(mapping, XContentType.JSON);

        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        System.out.println(response.isShardsAcknowledged());
    }

    //查询索引和映射
    @Test
    public void queryIndex() throws Exception{
        //创建操作索引的对象
        IndicesClient indices = client.indices();

        //具体操作 返回值
        GetIndexRequest request = new GetIndexRequest("try");

        GetIndexResponse response = indices.get(request, RequestOptions.DEFAULT);
        Map<String, MappingMetaData> mappings = response.getMappings();
        for (String key : mappings.keySet()) {
            System.out.println("key = " + mappings.get(key).getSourceAsMap());

        }
    }


    //删除索引
    @Test
    public void deleteIndex() throws Exception{
        IndicesClient indices = client.indices();
        DeleteIndexRequest request = new DeleteIndexRequest("yeah");
        AcknowledgedResponse delete = indices.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }


    //判断索引是否存在
    @Test
    public void existIndex() throws Exception{
        IndicesClient indices = client.indices();
        GetIndexRequest request = new GetIndexRequest("try");
        boolean b = indices.exists(request, RequestOptions.DEFAULT);
        System.out.println(b);
    }
}

