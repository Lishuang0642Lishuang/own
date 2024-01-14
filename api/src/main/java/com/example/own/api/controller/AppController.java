package com.example.own.api.controller;

import com.example.own.api.dto.QueryAppRequest;
import com.example.own.common.annotation.FlowLimit;
import com.example.own.common.model.WindowWrap;
import com.example.own.common.utils.DateTimeUtils;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.core.redis.TimedScheduledExecutorPoolExecutor;
import com.example.own.service.app.IAppService;
import com.example.own.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReferenceArray;

@Slf4j
@Controller
@RequestMapping("/app")
public class AppController {

    @Resource
    IAppService appService;

    @Resource
    Executor ownExecutor;

    @Resource
    IUserService userService;

    @Resource
    RedisTemplate redisTemplate;


    private final AtomicReferenceArray<WindowWrap<Object>> arrayRef = new AtomicReferenceArray<>(2);


    @ResponseBody
    @PostMapping("testRedis")
    public void testRedis() {

        Long timeMills = System.currentTimeMillis();

        redisTemplate.opsForValue().set(timeMills,timeMills, 60L, TimeUnit.SECONDS);
        redisTemplate.opsForSet().add("testSet",timeMills);
    }



    @ResponseBody
    @GetMapping("/appList")
//    @FlowLimit(group = "appList", maxTimes = 5, identifier = "sn")
    public List<AppDO> getAppList(Integer num) throws ExecutionException, InterruptedException {
        log.info("getAppList:{}", "query");
        redisTemplate.opsForValue().set("test", Boolean.FALSE);


        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new TimedScheduledExecutorPoolExecutor(4);
        ScheduledFuture future = scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::test ,0,1, TimeUnit.SECONDS);




        //延时任务关闭
        scheduledThreadPoolExecutor.schedule(() -> future.cancel(true), 20L, TimeUnit.SECONDS);

//        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
//
//        FutureTask future = new FutureTask(this::test);
//
//
//        ScheduledFuture future = scheduledThreadPoolExecutor.scheduleWithFixedDelay(this::test, 1,1, TimeUnit.SECONDS);
//
//        Thread.sleep(20000L);
//        future.cancel(true);


//
//        SingleThreadEventExecutor executor = new SingleThreadEventExecutor() {
//            @Override
//            protected void run() {
//
//            }
//        }


//        ScheduledFutureTask scheduledFutureTask = new ScheduledFutureTask();

//        future.cancel(false);




//        CompletableFuture.runAsync(()-> appService.getAppList(), ownExecutor);
//
//
//        List<AppDO> appList = appService.getAppList();
//        List<AppDO> newList = new ArrayList<>();
//        for (int i = 0; i < num; i++) {
//            newList.add(appList.get(i));
//        }

        log.info("================================================================================================");
//        Integer zero = 0;
//        Integer num = 1/zero;

//        return newList;
        return null;
    }

    public Boolean test() {


        log.info("sdfdsf");


        return Boolean.TRUE;
    }


    @ResponseBody
    @RequestMapping("/static")
    @FlowLimit(group = "getStaticResult", identifier = "queryAppRequest.id",unit = 3, maxTimes = 5)
    public String getStaticResult(QueryAppRequest queryAppRequest) {
        DateTimeUtils.parseMinuteToTime(10);
        return "hello";
    }

    @ResponseBody
    @GetMapping("/unionQuery")
    public String unionQuery(String name, String orgId) {

        appService.unionQuery(name, orgId);
        return "hello";
    }

    @ResponseBody
    @PostMapping("/save")
    public void save(AppDO appDO) {
        AppDO appDO1 = new AppDO();
        appDO1.setAppId(String.valueOf(RandomUtils.nextInt()));
        appDO1.setName(String.valueOf(RandomUtils.nextInt()));
        appDO1.setOrgId(String.valueOf(RandomUtils.nextInt()));

        appService.baseSave(appDO1);
        userService.baseSave();
    }


    public static void main(String[] args) throws Exception {


        double a = 15.0345;
        long b = (long) a;

        System.out.println(b);
//
//        EnumJson json = null;
//        Optional.ofNullable(json).orElseThrow(()-> new Exception());


//        String b =  "{\"defaultValue\":\"0\",\"enums\":[{\"key\":\"0\",\"value\":\"ice\"},{\"key\":\"1\",\"value\":\"airConditioner\"},{\"key\":\"2\",\"value\":\"battery\"}]}";
//        EnumJson json = new Gson().fromJson(b,EnumJson.class);
//
//        String a = "{\"defaultValue\":\"0\",\"enums\":[{\"key\":\"0\",\"value\":\"冰箱\"},{\"key\":\"1\",\"value\":\"空调\"},{\"key\":\"2\",\"value\":\"电源\"}]}";
//
//        EnumJson enumJson = new Gson().fromJson(a, EnumJson.class);
//
//        System.out.println(enumJson);
    }

}
