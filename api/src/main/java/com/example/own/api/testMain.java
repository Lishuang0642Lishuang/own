package com.example.own.api;

import org.apache.commons.lang3.RandomUtils;

public class testMain {

//
//    public static void test(Father father) {
//        Son son = (Son) father;
//
//        log.info("sdf, {}",son);
//
//    }

    public static void main(String[] args) {

        for (int i=0; i<1000;i++) {
            System.out.println(RandomUtils.nextInt(0,2));
        }






//        Old old = new Old();
//        old.setA("sdf");
//        old.setB("sdf");
//
//        Son son = CopyObjectUtils.copyAtoB(old, Son.class);
//        test(son);
//        System.out.println();


//        Map<String, String> accountMap = new HashMap<>();
//        accountMap.put("account", "account");
//        accountMap.put("password", "password");
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("account", "a");
//        jsonObject.addProperty("account", "a");
//        jsonObject.addProperty("password", "a");
//
//
//        log.info("map.toString:{}", accountMap.toString());
//
//
//        StringEntity stringEntity = new StringEntity(accountMap.toString(), "UTF-8");
//        StringEntity stringEntity1 = new StringEntity(jsonObject.toString(), "UTF-8");
//        log.info("sdf");


//        List<EnumBean> beanList = new ArrayList<>();
//
//        EnumBean aBean = new EnumBean();
//        aBean.setKey("123");
//        beanList.add(aBean);
//
//        EnumBean bBean = new EnumBean();
//        bBean.setKey("456");
//        beanList.add(bBean);
//
//        Map<String, String> map = new HashMap<>();
//        map.put("123", "sdf");
//
//        log.info(new Gson().toJson(beanList));
//        beanList = beanList.stream().filter(item -> map.containsKey(item.getKey())).collect(Collectors.toList());
//        log.info(new Gson().toJson(beanList));


//        Long a = 12546511225L;
//        List<Long> list = new ArrayList<>();
//        list.add(a);
//
//        Boolean result = list.contains(1254651122L);
//        System.out.println(result);
//
//
//        String s = "enterprises/577956ed-babe-409e-be4c-0cf47da0a971/devices/AVPHwEsk2E48lMBnWleZFKKnIbJQXL6tEgdrTh1meU4bEGLS5TxgVJVBAc9oSbiZQopmXIyQkrCUobLRmz4SqOst_Sc6jg";
//        String[] deviceNameArray = s.split("/");
//        String thirdDeviceId = deviceNameArray[3];
//
//        Map<String, String> map = new HashMap<>();
//        Arrays.stream(AppEnum.values()).forEach(item -> map.put(item.name(), item.getDesc()));
//
//        System.out.println(map);
    }
}
