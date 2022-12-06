package com.example.own.core.snowflake;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeGenerator {

    //数据中心[0,31] 配置文件中不配置就是0
    private long dataCenterId;

    //机器标识[0,31] 配置文件中不配置就是0
    private long machineId;

    @Bean
    public SnowFlakeFactory getSnowFlakeFactory() {
        SnowFlakeFactory snowFlakeFactory = new SnowFlakeFactory(dataCenterId, machineId);
        return snowFlakeFactory;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }

    public long getMachineId() {
        return machineId;
    }

    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public String getNextId() {
        return getSnowFlakeFactory().nextId(SnowFlakeBusiness.DEFAULT);
    }

    public String getNextId(SnowFlakeBusiness snowFlakeBusiness) {
        return getSnowFlakeFactory().nextId(snowFlakeBusiness);
    }
}
