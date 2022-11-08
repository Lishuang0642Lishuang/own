package com.example.own.core.mongo.impl;

import com.example.own.core.mongo.IRecordMongoDao;
import com.example.own.core.mongo.entity.RecordDO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service("recordMongoDao")
public class RecordMongoDaoImpl extends BaseMongoDaoImpl<RecordDO> implements IRecordMongoDao {

    @Resource
    MongoTemplate mongoTemplate;


    public void insertRecord(RecordDO recordDO) {

        mongoTemplate.insert(recordDO);
    }

    @Override
    public void insertRecordList(Collection<RecordDO> recordDOCollection) {

        mongoTemplate.insertAll(recordDOCollection);

    }


}
