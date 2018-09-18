package cn.com.cintel.validatenewskill.controller.v1;

import cn.com.cintel.validatenewskill.service.impl.ElasticSearchUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * @Company Name:
 * @Author: sky
 * @CreatDate: 2018/9/18 16:25
 * @ClassName: cn.com.cintel.validatenewskill.controller.v1
 * @Description:
 * @Modified By:
 * @ModifyDate: 2018/9/18 16:25
 */
@RestController
@RequestMapping(value = "/elastic")
public class ElasticSearchController {

    @Resource
    TransportClient transportClient;

    @GetMapping(value = "/insert",produces = "application/json;charset=utf-8")
    public void insert(){
        transportClient.admin().indices().prepareCreate("book02").execute().actionGet();
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("book02")
                    .startObject("properties")
                    .startObject("title").field("type", "text").field("store", true).endObject()
                    .startObject("description").field("type", "text").field("index", false).endObject()
                    .startObject("price").field("type", "double").endObject()
                    .startObject("onSale").field("type", "boolean").endObject()
                    .startObject("type").field("type", "integer").endObject()
                    .startObject("createDate").field("type", "date").endObject()
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutMappingRequest mappingRequest =
                Requests.putMappingRequest("book02").type("book02").source(mapping);
        transportClient.admin().indices().putMapping(mappingRequest).actionGet();
    }



}
