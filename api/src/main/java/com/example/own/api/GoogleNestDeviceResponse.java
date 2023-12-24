package com.example.own.api;

import com.google.gson.JsonObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class GoogleNestDeviceResponse implements Serializable {

    private static final long serialVersionUID = 7736208035998092282L;

    private String name;

    private String type;

    private String assignee;

    private JsonObject traits;

//
//    /**
//     * 获取在线状态,并转化成Integer
//     * @return
//     */
//    public Integer getOnlineStatus() {
//        JsonElement jsonElement = traits.get(BizConstant.ONLINE_STATUS);
//        Map map = new Gson().fromJson(jsonElement.getAsString(), Map.class);
//
//        String statusDescription = MapUtils.getString(map, BizConstant.STATUS);
//        return OnlineStatusEnum.getStatusByDescription(statusDescription);
//    }
//
//    /**
//     * 返回用户的自定义的名字
//     * @return
//     */
//    public String getCustomName() {
//        JsonElement jsonElement = traits.get(BizConstant.DEVICE_INFO);
//        return jsonElement.getAsJsonObject().get(BizConstant.CUSTOM_NAME).getAsString();
//    }

}
