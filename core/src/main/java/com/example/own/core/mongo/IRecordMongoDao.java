package com.example.own.core.mongo;

import com.example.own.core.mongo.entity.RecordDO;

import java.util.Collection;

/**
 * @desc: record 集合的表
 * @author: 英布
 * @date: 2022/11/8 11:47 上午
 *
 */

public interface IRecordMongoDao extends IBaseMongoDao<RecordDO> {


    void insertRecord(RecordDO recordDO);


    void insertRecordList(Collection<RecordDO> recordDOCollection);




}
