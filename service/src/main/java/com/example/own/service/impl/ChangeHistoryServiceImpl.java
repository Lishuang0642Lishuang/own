package com.example.own.service.impl;

import com.example.own.common.utils.CopyObjectUtils;
import com.example.own.core.mongo.IChangeHistoryDao;
import com.example.own.core.mongo.entity.ChangeHistoryDO;
import com.example.own.service.IChangeHistoryService;
import com.example.own.service.dto.ChangeHistoryDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
