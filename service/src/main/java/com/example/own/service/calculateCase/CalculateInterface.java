package com.example.own.service.calculateCase;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @desc: 这个需要放到各个应用里面，应用多了会比较麻烦
 * @author: link.li
 * @date: 2024/1/11
 *
 */
public class CalculateInterface {

    public static final String METHOD_PREFIX = "bns_iot_";


    public static void main(String[] args) throws Exception {

       Package currentPackage = CalculateInterface.class.getPackage();

//        String basePackage = "com.ecoflow.iot.smartvoice.controller";
        String basePackage = currentPackage.getName();
        List<Class<?>> classList = getClassesByPackageName(basePackage);
        List<GeneratedBean> beanList = generateBeanListByClassList(classList);

        List<GeneratedBean> relBeanList = beanList.stream().filter(item -> StringUtils.isNotEmpty(item.getApiAddr())).collect(Collectors.toList());

        System.out.println(relBeanList);

        Set<String> testCaseSet = getInterfaceSet();

//        int count = 0;
//        for (int i=0; i < relBeanList.size(); i++) {
//            for (String interfaceName: testCaseSet) {
//                if (interfaceName.contains(relBeanList.get(i).apiAddr)) {
//                    count = count +1;
//                }
//            }
////            for (int j = 0; j < testCaseSet.size(); j++) {
////                if (testCaseSet.get(j).contains(relBeanList.get(i).apiAddr)) {
////                    count = count + 1;
////                    continue;
////                }
////            }
//        }
//
//        int rate = count / relBeanList.size();
//        System.out.println(rate);
//        System.out.println(relBeanList.size());

//        generateCodeByBeanList(beanList);
    }


    private static Set<String> getInterfaceSet() {

        // 文件路径
//        String filePath = "C:\\Users\\link.li\\IdeaProjects\\own\\service\\src\\main\\java\\com\\example\\own\\service\\htmltrans\\OnlineTestReport.html";
        String filePath = "C:\\Users\\link.li\\IdeaProjects\\own\\service\\src\\main\\java\\com\\example\\own\\service\\htmltrans\\OnlineTestReport.html";


        List<String> urlLst = new ArrayList<>();
        HashMap<String, String> map = new HashMap<>();

        // 读取文件内容
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // 定义正则表达式
            String regex = "https://api-e.ecoflow.com/*";
//            String regex = "https://api-sit.ecoflow.com/*";
            Pattern pattern = Pattern.compile(regex);

            // 遍历每一行并进行匹配
            for (String line : lines) {
                Matcher matcher = pattern.matcher(line);

                // 判断是否匹配
                while (matcher.find()) {
                    if (line.contains("?")) {
                       String[] strings = line.split("\\?");
                       map.put(strings[0].trim(), "1");
                    } else {
                        map.put(line, "1");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String key: map.keySet()) {
            System.out.println(key);
        }

        return  map.keySet();
    }


    /**
     * @desc 根据List<Class < ?>> 进行遍历，生成指定bean,用以组合生成代码
     * @author link.li
     * @date 2023/4/5 0:00
     */
    public static List<GeneratedBean> generateBeanListByClassList(List<Class<?>> classList) throws Exception {

        List<GeneratedBean> beanList = new ArrayList<>();

        for (Class<?> clazz : classList) {
            String baseUrl = getBaseUrl(clazz);

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                GeneratedBean bean = new GeneratedBean();
                bean.setBaseAddr(baseUrl);
                buildGeneratedBean(bean, method);
                beanList.add(bean);
            }
        }

        return beanList;
    }

    /**
     * @desc 根据beanList生成代码
     * @author link.li
     * @date 2023/4/5 23:59
     */
    public static void generateCodeByBeanList(List<GeneratedBean> beanList) throws Exception {


        String data = new String(Files.readAllBytes(Paths.get("D:\\vim\\file\\template.txt")));

        for (GeneratedBean bean: beanList) {

            Map<String, String> valueMap = new HashMap<>(4);


            valueMap.put("param", paramToString(bean.getParams()));
            valueMap.put("methodName", bean.getTestMethodName());
            valueMap.put("apiDesc", bean.getApiDesc());
            valueMap.put("apiAddr", bean.getApiAddr());
            valueMap.put("reqMethod", bean.getReqMethod());
            valueMap.put("reqType", bean.getReqType());
            StringSubstitutor sub = new StringSubstitutor(valueMap);
            String resolvedString = sub.replace(data);
            System.out.println(resolvedString);

        }

    }

    public static String paramToString(List<String> params) throws Exception {
        String result = "";
        for (String param : params) {
            result = result + param + "=None, ";
        }
        if(StringUtils.isNotBlank(result)) {
            return result.substring(0, result.length()-1);
        }
        return "";
    }

    /**
     * @desc 根据传入的bean和方法，组装GeneratedBean
     * @author link.li
     * @date 2023/4/5 0:59
     */
    public static void buildGeneratedBean(GeneratedBean bean, Method method) throws Exception {
        setIsLogin(bean, method);
        setTestMethodName(bean, method);
        setReqType(bean, method);
        setParams(bean, method);
        bean.setApiDesc(getApiDesc(method));
        setApiAddrAndReqMethod(bean, method);
    }

    /**
     * @desc 把参数解析出来
     * @author link.li
     * @date 2023/4/5 23:07
     */
    public static void setParams(GeneratedBean bean, Method method) throws Exception {

        Parameter[] parameters = method.getParameters();

        List<String> params = new ArrayList<>();
        for (Parameter parameter: parameters) {
            params.add(parameter.getName());
        }

        bean.setParams(params);
    }


    /**
     * @desc 设置reqType, 一般有json和 urlencoded,先设置个json吧 todo
     * @author link.li
     * @date 2023/4/5 23:02
     */
    public static void setReqType(GeneratedBean bean, Method method) throws Exception {
        bean.setReqType("json");
    }

    /**
     * @desc 设置类的名字
     * @author link.li
     * @date 2023/4/5 22:51
     */
    public static void setTestMethodName(GeneratedBean bean, Method method) throws Exception {

        bean.setTestMethodName(METHOD_PREFIX + method.getName());
    }

    /**
     * @desc 设置 isLogin 先放个true  todo
     * @author link.li
     * @date 2023/4/5 22:48
     */
    public static void setIsLogin(GeneratedBean bean, Method method) throws Exception {
        bean.setIsLogin(Boolean.TRUE);
    }


    /**
     * @desc 拿到getMapping、postMapping、putMapping中的url  并设置reqMethod
     * @author link.li
     * @date 2023/4/5 16:35
     */
    public static void setApiAddrAndReqMethod(GeneratedBean bean, Method method) throws Exception {

        Annotation[] annotations = method.getDeclaredAnnotations();

        for (Annotation annotation : annotations) {
            String fullName = annotation.annotationType().getName();

            if (fullName.endsWith("Mapping")) {
                Class<?> clazz = Class.forName(fullName);
                Method valueMethod = clazz.getDeclaredMethod("value");
                String[] result = (String[]) valueMethod.invoke(annotation);
                if (result.length > 0) {
                    bean.setApiAddr(bean.getBaseAddr() + result[0]);
                } else {
                    bean.setApiAddr(bean.getBaseAddr());
                }

                String simpleName = annotation.annotationType().getSimpleName();
                String reqMethod = simpleName.substring(0, simpleName.length() - 7).toLowerCase();
                bean.setReqMethod(reqMethod);
            }
        }
    }


    /**
     * @desc 根据方法拿到备注
     * @author link.li
     * @date 2023/4/5 1:04
     */
    public static String getApiDesc(Method method) {
        ApiOperation apiOperation = method.getDeclaredAnnotation(ApiOperation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            return apiOperation.value();
        }
        return "";
    }

    /**
     * @desc 获取requestMapping注解上的url
     * @author link.li
     * @date 2023/4/5 0:55
     */
    public static String getBaseUrl(Class<?> clazz) {

        RequestMapping requestMapping = clazz.getDeclaredAnnotation(RequestMapping.class);

        if(ObjectUtils.isEmpty(requestMapping)) {
            return "";
        }

        String[] baseUrl = requestMapping.value();

        return baseUrl[0];
    }


    /**
     * @desc 根据包名反射获取所有的类
     * @author link.li
     * @date 2023/4/4 23:40
     */
    public static List<Class<?>> getClassesByPackageName(String packageName) throws Exception {

        //1、设置pattern，可以解析包下类，以及子包的类
        String resourcePattern = "/**/*.class";
        String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(packageName) + resourcePattern;

        //2、设置解析器
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources(pattern);

        List<Class<?>> classList = new ArrayList<>();
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        for (Resource resource : resources) {

            //3、用于读取类信息
            MetadataReader reader = readerFactory.getMetadataReader(resource);

            //4、扫描到的class
            String classname = reader.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(classname);
            classList.add(clazz);
        }

        return classList;
    }
}
