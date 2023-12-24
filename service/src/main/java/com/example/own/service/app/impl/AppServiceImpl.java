package com.example.own.service.app.impl;

import com.example.own.core.batch.SqlBatchOperateComponent;
import com.example.own.core.mysql.IAppDao;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.core.mysql.mapper.AppMapper;
import com.example.own.service.app.IAppService;
import com.example.own.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service("appService")
public class AppServiceImpl extends BaseServiceImpl<AppMapper, AppDO> implements IAppService {

    @Resource
    IAppDao appDao;

    @Resource
    AppMapper appMapper;

    @Resource
    SqlBatchOperateComponent<AppDO> component;

    private LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(20);



    @Override
    public List<AppDO> getAppList() {
        log.info("getAppList:{}", "appList");
        return appDao.list();
    }

    @Override
    public List<AppDO> unionQuery(String name, String orgId) {

       List<AppDO> appDOList = appMapper.unionQueryDevice(name, orgId);
       log.info("appDOList:{}", appDOList);


        return appDOList;
    }

    @Override
    public void baseSave(AppDO appDO) {
        
        for (int i=0; i<300; i++) {

            AppDO newAppDO = new AppDO();
            newAppDO.setAppId(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setName(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setOrgId(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setCreateTime(RandomUtils.nextLong());
            newAppDO.setCreateUser("sdf");
            newAppDO.setId(RandomUtils.nextLong());
            component.saveWithCyclicBarrier(this, newAppDO);

            String s = "{\"command\":\"setMode\",\"params\":{\"mode\":\"mode1\"}}";
        }
    }


    public static void main(String[] args) {



        String m = "sdm.devices.types.THERMOSTAT";
        String[] typeArray = m.split("[.]");
        String finalType = typeArray[typeArray.length -1];
        System.out.println("sd");



//        String sn ="00112145214";
//        String final4 = sn.substring(sn.length() -4);
//
//        System.out.println(final4);
//
//        String a = "{\"error\":{\"code\":429,\"message\":\"Rate limited for the Thermostat.\",\"status\":\"RESOURCE_EXHAUSTED\"}}";
//        JsonObject jsonObject = new Gson().fromJson(a, JsonObject.class);
//        System.out.println(jsonObject);



//        String a = "{\"command\":\"sdm.devices.commands.ThermostatTemperatureSetpoint.SetCool\",\"params\":{\"coolCelsius\":24.5}}";
//        JsonObject jsonObject = new Gson().fromJson(a, JsonObject.class);
//
//        JsonElement paramElement = jsonObject.get("params");
//
//        for (Map.Entry<String, JsonElement> entry : paramElement.getAsJsonObject().entrySet()) {
//            System.out.println( entry.getValue());
//        }
//
//        System.out.println("sdf");



//        Boolean result = StringUtils.is("19.5");
//        Boolean twoResult =  StringUtils.isNumeric("19");

//        UserDO userDO = UserDO.builder().type("2").build();
//       Boolean result =  AppEnum.UNLIKE.getCode().equals(Integer.parseInt(userDO.getType()));
//        System.out.println(result);



//        JsonObject jsonObject1 = new JsonObject();
//        jsonObject1.add("mode", "sdf");
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.add("command", "setMode");
//        jsonObject.add("params", jsonObject1);

//        System.out.println(new Gson().toJson(jsonObject));;



//
//        String taskId = "1470297572831395840";
//        String sn = "0011" + StringUtils.substring(taskId, 7);
//        System.out.println(sn.length());
//        System.out.println(sn);
//
//
//        AppDO appDO = new AppDO();
//        System.out.println(appDO.getClass().getName());
    }


}
