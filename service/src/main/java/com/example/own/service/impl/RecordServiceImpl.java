package com.example.own.service.impl;

import com.example.own.common.utils.CopyObjectUtils;
import com.example.own.core.mongo.IRecordMongoDao;
import com.example.own.core.mongo.entity.RecordDO;
import com.example.own.service.IRecordService;
import com.example.own.service.dto.RecordDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("recordService")
public class RecordServiceImpl implements IRecordService {


    @Resource
    IRecordMongoDao recordMongoDao;

    @Override
    public boolean insertRecord(RecordDTO recordDTO) {

        RecordDO recordDO = CopyObjectUtils.copyAtoB(recordDTO, RecordDO.class);

        recordMongoDao.insertRecord(recordDO);
        return Boolean.TRUE;
    }
}
