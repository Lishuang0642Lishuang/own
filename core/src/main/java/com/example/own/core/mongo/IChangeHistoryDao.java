package com.example.own.core.mongo;

import com.example.own.core.mongo.entity.ChangeHistoryDO;

import java.util.Collection;

public interface IChangeHistoryDao {


    void insertChangeHistoryList(Collection<ChangeHistoryDO> changeHistoryDOCollection);
}
