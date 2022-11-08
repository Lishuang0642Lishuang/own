package com.example.own.api.controller;

import com.example.own.core.entity.RecordDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SkipOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MyController {



    @Resource
    MongoTemplate mongoTemplate;


    @ResponseBody
    @RequestMapping("/hello")
    public String testHello() {

        RecordDO recordDO = new RecordDO();
        recordDO.setName("sf");
        recordDO.setPrice("price");

        mongoTemplate.insert(recordDO);


        return "testHello";
    }


    @ResponseBody
    @RequestMapping("/mongo")
    public String testInsertMongo(String name, String price) {

        List<RecordDO> recordDOList = new ArrayList<>();


        for (int i = 0; i < 100; i++) {
            RecordDO recordDO = new RecordDO();
            recordDO.setName("sf"+ i);
            recordDO.setPrice("price" + i);
            recordDO.setCode("code" + i/5);
            recordDO.setEnv("name");

            recordDO.setUpdateTime(i);
            recordDOList.add(recordDO);

        }

        mongoTemplate.insertAll(recordDOList);


        return "testHello";
    }

    @ResponseBody
    @RequestMapping("/aggregation")
    public List<RecordDO> aggregation() {




        List<AggregationOperation> operations = new ArrayList<>();

        Criteria criteria = Criteria.where("env").is("name");
        List<RecordDO> list = mongoTemplate.find(Query.query(criteria), RecordDO.class);
        System.out.println(list);
        System.out.println(list.size());

        GroupOperation group = Aggregation.group("code").first("code").as("code")
                .first("name").as("name")
                .first("env").as("env")
                .first("updateTime").as("updateTime")
                .first("price").as("price");

        SortOperation sort = Aggregation.sort(Sort.Direction.ASC, "updateTime");

        LimitOperation limit = Aggregation.limit(5);



        SkipOperation skip = Aggregation.skip(3l);

        operations.add(Aggregation.match(criteria));
        operations.add(group);
        operations.add(sort);
        operations.add(skip);
        operations.add(limit);

        Aggregation aggregation = Aggregation.newAggregation(operations);

        AggregationResults<RecordDO>  results = mongoTemplate.aggregate(aggregation, "recordDO", RecordDO.class);

        System.out.println(results.getMappedResults());

        return results.getMappedResults();



    }

}
