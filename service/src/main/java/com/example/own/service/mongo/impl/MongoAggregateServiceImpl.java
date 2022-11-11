package com.example.own.service.mongo.impl;

import com.example.own.service.mongo.IMongoAggregateService;
import com.example.own.service.record.vo.ChangeHistoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("mongoAggregateService")
public class MongoAggregateServiceImpl implements IMongoAggregateService {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void aggregateDuplication() {

        List<AggregationOperation> operations = new ArrayList<>();
        GroupOperation group =
                Aggregation.group("code", "operateType").count().as("total").addToSet("_id").as("ids")
                        .first("status").as("status")
                        .first("tuyaEnv").as("tuyaEnv")
                        .first("editor").as("editor")
                        .first("code").as("code");
        MatchOperation matchOperation = Aggregation.match(Criteria.where("total").gt(1));
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "status");
        operations.add(group);
        operations.add(matchOperation);
        operations.add(sortOperation);
        Aggregation aggregation = Aggregation.newAggregation(operations);

        log.info("a");

        AggregationResults<ChangeHistoryVO> results =
                mongoTemplate.aggregate(aggregation, "multi_lang_change_history", ChangeHistoryVO.class);
        List<ChangeHistoryVO> list = results.getMappedResults();
        log.info("list:{}", list);


    }

    @Override
    public void aggregateOnlyCode() {

        List<AggregationOperation> operations = new ArrayList<>();

        SortOperation sortOperation = Aggregation.sort(Sort.Direction.ASC, "status");
        GroupOperation groupOperation = Aggregation.group("code")
                .first("_id").as("realId")
                .first("code").as("code")
                .first("operateType").as("operateType")
                .first("editor").as("editor")
                .first("status").as("status")
                .first("source").as("source");
        ProjectionOperation projectionOperation = Aggregation.project("code","editor").and("realId").as("id");
        operations.add(groupOperation);
        operations.add(sortOperation);
        operations.add(projectionOperation);
        Aggregation aggregation = Aggregation.newAggregation(operations);


        AggregationResults<ChangeHistoryVO> results =
                mongoTemplate.aggregate(aggregation, "multi_lang_change_history", ChangeHistoryVO.class);
        List<ChangeHistoryVO> list = results.getMappedResults();
        log.info("list:{}", list);
    }
}
