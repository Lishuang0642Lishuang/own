package com.example.own.service.record;

import com.example.own.service.record.dto.ChangeHistoryDTO;

import java.util.Collection;

public interface IChangeHistoryService {

    void insertChangeHistoryList(Collection<ChangeHistoryDTO> changeHistoryDTOCollection);

    void insertChangeHistory(ChangeHistoryDTO changeHistoryDTO);

}
