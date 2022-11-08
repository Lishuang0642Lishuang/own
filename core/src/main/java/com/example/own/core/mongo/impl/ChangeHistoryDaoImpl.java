package com.example.own.core.mongo.impl;

import com.example.own.core.mongo.IChangeHistoryDao;
import com.example.own.core.mongo.entity.ChangeHistoryDO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service("changeHistoryDao")
public class ChangeHistoryDaoImpl extends BaseMongoDaoImpl<ChangeHistoryDO> implements IChangeHistoryDao {

    @Resource
    MongoTemplate mongoTemplate;


    @Override
    public void insertChangeHistoryList(Collection<ChangeHistoryDO> changeHistoryDOCollection) {

        mongoTemplate.insertAll(changeHistoryDOCollection);

    }
}
