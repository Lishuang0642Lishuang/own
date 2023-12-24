package com.example.own.common.handler;

import com.example.own.common.constant.FlowLimitConstant;
import com.example.own.common.enums.LimitTimeUnitEnum;
import com.example.own.common.model.LimitDetail;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc: 抽象类
 * @author: link.li
 * @date: 2023/10/29
 *
 */
@Slf4j
public class FlowLimitAbstractHandler implements FlowLimitHandler{
    @Override
    public Boolean addRequest(LimitDetail limitDetail) {
        return null;
    }


    /**
     * 根据 limitDetail 获取slot
     * 每秒2个， 每分钟5个
     * 非秒非分钟的话，默认两个
     * * @param limitDetail
     * @return
     */
    protected Integer getBaseSlotNum(LimitDetail limitDetail) {


        if (LimitTimeUnitEnum.MINUTE.getCode().equals(limitDetail.getUnit())) {
            return LimitTimeUnitEnum.MINUTE.getSlotNum();
        }

        return LimitTimeUnitEnum.SECOND.getSlotNum();
    }

    /**
     * 基础slot数量 * 持续时间，得到最终的数量
     * @param limitDetail
     * @return
     */
    protected Integer getFinalSlotNum(LimitDetail limitDetail) {
        return getBaseSlotNum(limitDetail) * limitDetail.getUnitTime();
    }

    /**
     * 获取每个slot的时长
     * @param limitDetail
     * @return
     */
    protected Integer getSlotTime(LimitDetail limitDetail) {
        if (LimitTimeUnitEnum.MINUTE.getCode().equals(limitDetail.getUnit())) {
            return FlowLimitConstant.SLOT_TIME_TWELVE_THOUSAND;
        }
        return FlowLimitConstant.SLOT_TIME_FIVE_HUNDRED;
    }
}
