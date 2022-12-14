package com.example.own.service.mongo;
/**
 * @desc: mongo的一些使用方式
 * @author: 英布
 * @date: 2022/11/9 4:39 下午
 *
 */

public interface IMongoAggregateService {

    /**
     * 1、根据两个字段的组合来进行查重，并获取id的集合
     */
    void aggregateDuplication();

    /**
     * 1、要先group再 sort
     * 2、如何获取原始的id?
     */
    void aggregateOnlyCode();
}
