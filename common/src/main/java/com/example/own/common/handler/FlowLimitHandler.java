package com.example.own.common.handler;

import com.example.own.common.model.LimitDetail;

/**
 * @desc: 处理各种不同类型限流器的handler
 * @author: link.li
 * @date: 2023/10/29
 *
 */
public interface FlowLimitHandler {

    /**
     * 是否放行请求*
     *
     * @param limitDetail@return
     */
    Boolean addRequest(LimitDetail limitDetail);




}
