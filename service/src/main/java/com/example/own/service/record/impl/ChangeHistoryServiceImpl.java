package com.example.own.service.record.impl;

import com.example.own.common.utils.CopyObjectUtils;
import com.example.own.core.mongo.IChangeHistoryDao;
import com.example.own.core.mongo.entity.ChangeHistoryDO;
import com.example.own.service.record.IChangeHistoryService;
import com.example.own.service.record.dto.ChangeHistoryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service("changeHistoryService")
public class ChangeHistoryServiceImpl implements IChangeHistoryService {


    @Resource
    IChangeHistoryDao changeHistoryDao;


    @Override
    public void insertChangeHistoryList(Collection<ChangeHistoryDTO> changeHistoryDTOCollection) {

        Collection<ChangeHistoryDO> changeHistoryDOCollection = CopyObjectUtils.copyAlistToBlist(changeHistoryDTOCollection, ChangeHistoryDO.class);

        changeHistoryDao.insertChangeHistoryList(changeHistoryDOCollection);
    }

    @Override
    public void insertChangeHistory(ChangeHistoryDTO changeHistoryDTO) {
        ChangeHistoryDO changeHistoryDO = CopyObjectUtils.copyAtoB(changeHistoryDTO, ChangeHistoryDO.class);
        changeHistoryDao.insertChangeHistory(changeHistoryDO);
    }
}
