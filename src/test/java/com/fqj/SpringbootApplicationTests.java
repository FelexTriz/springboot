package com.fqj;

import com.alibaba.fastjson.JSON;
import com.fqj.dao.BookDao;
import com.fqj.domain.Book;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;


import java.io.IOException;
import java.util.List;

@SpringBootTest
class SpringbootApplicationTests {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private BookDao bookDao;
    @Test
    void test() throws IOException {
        client.indices().create(new CreateIndexRequest("books"), RequestOptions.DEFAULT);


        client.close();
    }

    @Test
    void testd() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("books");
        String json = "{\n" +
                "    \"mappings\":{\n" +
                "        \"properties\":{\n" +
                "            \"id\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"name\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"description\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\",\n" +
                "                \"copy_to\":\"all\"\n" +
                "            },\n" +
                "            \"type\":{\n" +
                "                \"type\":\"keyword\"\n" +
                "            },\n" +
                "            \"all\":{\n" +
                "                \"type\":\"text\",\n" +
                "                \"analyzer\":\"ik_max_word\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
        request.source(json, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);


        client.close();
    }
    @Test
    void testCreatDoc() throws IOException {
        Book book = bookDao.selectById(10);
        IndexRequest request = new IndexRequest("books").id("10");

        String json = JSON.toJSONString(book);
        request.source(json,XContentType.JSON );
        client.index(request, RequestOptions.DEFAULT);
    }
}
