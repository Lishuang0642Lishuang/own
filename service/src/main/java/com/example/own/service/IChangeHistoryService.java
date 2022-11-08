package com.example.own.service;

import com.example.own.service.dto.ChangeHistoryDTO;

import java.util.Collection;

public interface IChangeHistoryService {

    void insertChangeHistoryList(Collection<ChangeHistoryDTO> changeHistoryDTOCollection);

}
