package com.example.own.service.reflection.impl;

import com.example.own.service.reflection.IReflectionService;

public class ReflectionServiceImpl implements IReflectionService {


    @Override
    public void testReflection() {

// 测试反射，根据构造器和参数获取对象
//        for (DeviceClient deviceClient: deviceClientList) {
//
//           for (String taskName: taskNameList) {
//
//               String fullName = TaskEnum.getFullName(taskName);
//
//               try {
//                   DeviceAbstractTask task =  (DeviceAbstractTask)Class.forName(fullName).getConstructor(new Class[]{DeviceClient.class}).newInstance(deviceClient);
//                   executorService.scheduleAtFixedRate(task, initialDelay, period, timeUnit);
//               } catch (Exception e) {
//                   log.info("创建对象异常: {}", fullName);
//               }
//           }
//
//
//        }
    }
}
