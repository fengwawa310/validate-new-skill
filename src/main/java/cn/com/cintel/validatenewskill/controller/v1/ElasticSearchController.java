package cn.com.cintel.validatenewskill.controller.v1;

import cn.com.cintel.validatenewskill.service.impl.ElasticSearchUtils;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

    @GetMapping(value = "/insertMapping",produces = "application/json;charset=utf-8")
    public void insertMapping(){
        transportClient.admin().indices().prepareCreate("book").execute().actionGet();
        XContentBuilder mapping = null;
        try {
            mapping = jsonBuilder()
                    .startObject()
                    .startObject("book")
                    .startObject("properties")
                    .startObject("title").field("type", "text").field("store", true).endObject()
                    .startObject("description").field("type", "text").field("index", false).endObject()
                    .startObject("price").field("type", "double").endObject()
                    .startObject("onSale").field("type", "boolean").endObject()
                    .startObject("type").field("type", "integer").endObject()
                    //设置Date的格式
                    .startObject("createDate").field("type", "date").field("format","yyyy-MM-dd HH:mm:ss") .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutMappingRequest mappingRequest =
                Requests.putMappingRequest("book").type("book").source(mapping);
        transportClient.admin().indices().putMapping(mappingRequest).actionGet();
    }


    /**
     * 向type中插入数据
     */
    @GetMapping(value = "/insertData",produces = "application/json;charset=utf-8")
    public IndexResponse insertData(){
        XContentBuilder doc = null;
        try {
            doc = jsonBuilder()
                    .startObject()
                    .field("title", "this is a title!")
                    .field("description", "descript what?")
                    .field("price", 100)
                    .field("onSale", true)
                    .field("type", 1)
                    .field("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexResponse response = transportClient.prepareIndex("book","book")
                .setSource(doc)
                .execute()
                .actionGet();
        return response;
    }


    /**
     * 更新索引数据
     */
    @PostMapping(value = "/updateData",produces = "application/json;charset=utf-8")
    public UpdateResponse updateData(@RequestParam(name = "index")String index,
                                     @RequestParam(name = "type")String type,
                                     @RequestParam(name = "id")String id){
        XContentBuilder doc = null;
        try {
            doc = jsonBuilder()
                    .startObject()
                    .field("title", "cintel")
                    .field("description", "descript what?")
                    .field("price", 200)
                    .field("onSale", true)
                    .field("type", 1)
                    .field("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(index)
                .type(type)
                .id(id)
                .doc(doc);
        //执行修改
        UpdateResponse response = null;
        try {
            response = transportClient.update(updateRequest).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return response;
    }


    /**
     * 删除api允许从特定索引通过id删除json文档。
     * 一是通过id删除
     */
    @PostMapping(value = "/deleteDataById",produces = "application/json;charset=utf-8")
    public DeleteResponse deleteData(@RequestParam(name = "index")String index,
                           @RequestParam(name = "type")String type,
                           @RequestParam(name = "id")String id){
        DeleteResponse response = transportClient.prepareDelete(index, type, id)
                .execute()
                .actionGet();
        return response;

    }



}
