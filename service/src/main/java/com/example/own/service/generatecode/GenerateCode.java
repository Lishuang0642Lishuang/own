package com.example.own.service.generatecode;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author link.li
 * @date 2023/4/4
 */
public class GenerateCode {


    /**
     * 生成 Python 方法前缀
     */
    public static final String METHOD_PREFIX = "bns_iot_";

    /**
     * url前缀
     */
    public static final String URL_PREFIX = "/general";

    /**
     * 文件名称
     */
    public static final String FILE_NAME = "general.py";

    /**
     * 类的全称的前缀
     */
    public static final String CLASS_ADDRESS_PREFIX = "com.ecoflow";

    /**
     * 扫描包名
     */
    public static String basePackage = "com.ecoflow.iot.controller";

    /**
     * 扫描类名
     */
    public static String className = "com.ecoflow.iot.controller.open.OpenUserDeviceController";

    /**
     * 是否按照类名生成代码
     *
     * true: 按照类名，生成一个类中的所有接口测试用例
     * false: 按照包名，生成一个包下所有接口测试用例
     */
    public static boolean generateByClass = true;

    /**
     * 模板路径
     */
    public static String templatePath = "D:\\temp\\template.txt";

    /**
     * 生成代码目录
     */
    public static String generatedCodePath = "D:\\temp\\";


    public static void main(String[] args) throws Exception {
        List<Class<?>> classList ;
        if (generateByClass) {
            classList = Collections.singletonList(Class.forName(className));
        } else {
            classList = getClassesByPackageName(basePackage);
        }
        List<GeneratedBean> beanList = generateBeanListByClassList(classList);
        generateCodeByBeanList(beanList);
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
                bean.setHashRequestBody(false);
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

        StringBuilder codeBuilder = new StringBuilder();
        for (GeneratedBean bean: beanList) {

            Map<String, String> valueMap = new HashMap<>(4);
            valueMap.put("param", typeParamToString(bean.getTypeParams()));
            valueMap.put("methodName", bean.getTestMethodName());
            valueMap.put("apiDesc", bean.getApiDesc());
            valueMap.put("apiAddr", bean.getApiAddr());
            valueMap.put("reqMethod", bean.getReqMethod());
            valueMap.put("reqType", bean.getReqType());
            valueMap.put("reqData",reqDataToString(bean));
            valueMap.put("queryData", queryDataToString(bean));
            StringSubstitutor sub = new StringSubstitutor(valueMap);
            codeBuilder.append(sub.replace(new String(Files.readAllBytes(Paths.get(templatePath)))));
            codeBuilder.append("\n\n");
        }
        String targetPath = generatedCodePath + className.substring(className.lastIndexOf(".") + 1) + ".py";;
        if (!generateByClass) {
            targetPath = generatedCodePath + FILE_NAME;
        }
        Files.write(Paths.get(targetPath), codeBuilder.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("代码生成完成 !!!!!!!!");

    }

    private static String queryDataToString(GeneratedBean bean) {
        if (bean.hashRequestBody) {
            return "";
        }
        return getRequestData(bean);
    }

    /**
     * 获取 body 请求参数
     *
     * @param bean
     * @return
     */
    public static String reqDataToString(GeneratedBean bean) {
        if (!bean.hashRequestBody) {
            return "";
        }
        return getRequestData(bean);
    }



    /**
     * 获取请求参数
     *
     * @param bean
     * @return
     */
    private static String getRequestData(GeneratedBean bean) {
        try {
            StringBuilder dataBuilder = new StringBuilder();
            for (GeneratedBean.TypeParam typeParam : bean.getTypeParams()) {
                // 封装对象
                if (typeParam.getType().contains(CLASS_ADDRESS_PREFIX)) {
                    // 遍历属性
                    Class clazz = Class.forName(typeParam.getType());
                    Field[] fields = clazz.getDeclaredFields();
                    if (fields.length > 0) {
                        for (int i = 0; i < fields.length; i++) {
                            dataBuilder.append("\"").append(fields[i].getName()).append("\":")
                                    .append(fields[i].getName()).append(",");
                        }
                    }
                } else {
                    // 非封装对象
                    dataBuilder.append("\"").append(typeParam.getName()).append("\":")
                            .append(typeParam.getName()).append(",");
                }
            }
            if (StringUtils.isNotBlank(dataBuilder.toString())) {
                return dataBuilder.substring(0, dataBuilder.length() - 1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * 将请求参数转换为字符串
     *
     * @param typeParams
     * @return
     */
    public static String typeParamToString(List<GeneratedBean.TypeParam> typeParams) {
        try {
            StringBuilder result = new StringBuilder();
            for (GeneratedBean.TypeParam typeParam : typeParams) {
                // 封装对象
                if (typeParam.getType().contains(CLASS_ADDRESS_PREFIX)) {
                    // 遍历属性
                    Class clazz = Class.forName(typeParam.getType());
                    Field[] fields = clazz.getDeclaredFields();
                    if (fields.length > 0) {
                        for (int i = 0; i < fields.length; i++) {
                            result.append(fields[i].getName()).append("=None, ");
                        }
                    }
                } else {
                    // 非封装对象
                    result.append(typeParam.getName()).append("=None, ");
                }
            }
            if (StringUtils.isNotBlank(result.toString())) {
                return result.substring(0, result.length() - 1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        List<GeneratedBean.TypeParam> typeParams = new ArrayList<>();
        for (Parameter parameter: parameters) {

            // Python 请求参数字符串
            GeneratedBean.TypeParam typeParam = new GeneratedBean.TypeParam();
            typeParam.setType(parameter.getType().getName());
            typeParam.setName(parameter.getName());
            typeParams.add(typeParam);

            // Python req_data or query_data
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            if (Objects.nonNull(requestBody)) {
                bean.setHashRequestBody(true);
            }
        }
        bean.setTypeParams(typeParams);
    }


    /**
     * @desc 设置reqType, 一般有json和 urlencoded
     * @author link.li
     * @date 2023/4/5 23:02
     */
    public static void setReqType(GeneratedBean bean, Method method) throws Exception {
        if (Objects.nonNull(method.getAnnotation(GetMapping.class))) {
            bean.setReqType("urlencoded");
            return;
        }
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (Objects.nonNull(requestMapping) && ArrayUtils.isNotEmpty(requestMapping.method())
                && String.valueOf(requestMapping.method()[0]).equalsIgnoreCase("get")) {
            bean.setReqType("urlencoded");
            return;
        }
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
     * @desc 设置 isLogin
     *
     * @author link.li
     * @date 2023/4/5 22:48
     */
    public static void setIsLogin(GeneratedBean bean, Method method) throws Exception {
//        if (Objects.nonNull(method.getAnnotation(Token.class))) {
//            bean.setIsLogin(true);
//            return;
//        }
        bean.setIsLogin(false);
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
        if (Objects.nonNull(apiOperation)) {
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

        String[] baseUrl = requestMapping.value();

        return URL_PREFIX + baseUrl[0];
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