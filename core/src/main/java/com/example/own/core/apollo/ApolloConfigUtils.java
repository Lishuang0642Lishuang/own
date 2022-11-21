package com.example.own.core.apollo;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;

/**
 * @desc: 获取apollo的配置
 * @author: 英布
 * @date: 2022/11/19 9:06 下午
 *
 */

@Slf4j
public class ApolloConfigUtils {


    public static String getConfigByNamespaceAndKey(String namespace, String key) {
        Config config = ConfigService.getConfig("testnamespace");
        String testEnv =  config.getProperty("test", "hhh");
        log.info("runtime.env:{}", testEnv);
        return testEnv;
    }


}
