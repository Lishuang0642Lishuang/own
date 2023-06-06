package com.example.own.core;

import com.example.own.core.mysql.bean.AppDO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.collections.MapUtils;
import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args){


        Map<String,String> map = new HashMap<>();
        String m = MapUtils.getString(map, null, "");


        String returnStr = opportunityCodeToName("01,02");



        Long time = System.currentTimeMillis();
        System.out.println(time);


        Long a = 10000L;
        Long b = 10000L;

        long c = a + b;
        System.out.println(c);


        String value = "SELECT\n" + "  d.product_catagory_id catagoryid,\n" + "  d.general_key catagorykey,\n"
                + "  d.NAME catagoryname,\n" + "  t.* \n" + "FROM\n" + "  (\n" + "  SELECT\n" + "    a.spu_id spuid,\n"
                + "    a.general_key spukey,\n" + "    a.NAME spuname,\n" + "    b.pid,\n"
                + "    b.product_catagory_id serialid,\n" + "    b.general_key serialkey,\n" + "    b.NAME serialname,\n"
                + "    (select e.sku_id from efcms_sku e where e.sku_id = c.pid AND e.product_type != '0' and\n"
                + "    e.deleted = '0'   LIMIT 1) as spuskuid,\n" + "    c.sku_id skuid,\n" + "    c.general_key skukey,\n"
                + "    c.NAME skuname,\n" + "    c.product_type producttype,\n" + "    c.model,\n" + "    c.sn snprefix \n"
                + "  FROM\n" + "    efcms_spu a,\n" + "    efcms_product_catagory b,\n" + "    efcms_sku c \n" + "  WHERE\n"
                + "    a.product_catagory_id = b.product_catagory_id \n" + "    AND a.spu_id = c.spu_id \n"
                + "    AND a.deleted = '0' \n" + "    AND b.deleted = '0' \n" + "    AND c.deleted = '0' \n"
                + "    AND c.sn IS NOT NULL \n" + "    and c.product_type != '0'\n"
                + "    AND b.product_catagory_id NOT IN ( SELECT product_catagory_id FROM `efcms_product_catagory` WHERE pid = '0' AND deleted = '0' ) \n"
                + "  ORDER BY\n" + "    producttype,\n" + "    model,\n" + "    spukey,\n" + "    skukey \n" + "  ) t\n"
                + "  LEFT JOIN efcms_product_catagory d ON t.pid = d.product_catagory_id \n" + "WHERE\n"
                + "  d.deleted = '0' \n" + "ORDER BY\n" + "  catagoryid,\n" + "  serialid,\n" + "  spuid,\n" + "  skuid";

        System.out.println(value);
        System.out.println("============================================");

        String value2 = "SELECT\n" + "  sku_id skuId,\n" + "  general_key skuCode,\n" + "  NAME skuName,\n"
                + "  product_type productType,\n" + "  model \n" + "FROM\n" + "  efcms_sku \n" + "WHERE\n" + "  pid = '0' \n"
                + "  AND product_type != '0' \n" + "  AND deleted = '0' \n" + "ORDER BY\n"
                + "  cast( product_type AS UNSIGNED INTEGER ),\n" + "  cast( model AS UNSIGNED INTEGER )";
        System.out.println(value2);


    }


    private static String opportunityCodeToName(String opportunity) {

        String[] opportunityArray = opportunity.split(",");
        Map<String, String> dictKeyValueMap = new HashMap<>();

        dictKeyValueMap.put("01", "ll");
        dictKeyValueMap.put("02", "ls");
//        List<DictEntity> dictEntityList = getTaskHintPageEntity();
//        Map<String, String> dictKeyValueMap = dictEntityList.stream().collect(Collectors.toMap(DictEntity::getDictKey, DictEntity::getDictValue));
        return Arrays.stream(opportunityArray).map(item -> MapUtils.getString(dictKeyValueMap, item)).collect(Collectors.joining(","));
    }



    private static String print() {
        System.out.println("print");
        return "sdf";

    }

    private static String getSpuName(String name) {
        String spuName = "";
        String spuName2 = "";
        try {
            JsonObject jsonObject = new Gson().fromJson(name, JsonObject.class);
            spuName = jsonObject.get("en_US").getAsString();
            spuName2 = jsonObject.get("en_US").toString();
        } catch (Exception e) {
            spuName = name;
        }

        System.out.println(spuName);
        System.out.println(spuName2);
        return spuName;
    }



}
