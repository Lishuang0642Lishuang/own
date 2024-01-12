package com.example.own.service.calculateCase;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc: 试一下直接扫代码，不要加载类
 * @author: link.li
 * @date: 2024/1/11
 *
 */
@Slf4j
public class CalculateInterface3 {

    public static final String METHOD_PREFIX = "bns_iot_";


    public static void main(String[] args) throws Exception {

        //1、获取所有应用的应用名
        Map<String, String> nameDescMap = ApplicationEnum.nameAddressMap;

        //2、对应用进行遍历
        for (String applicationName: nameDescMap.keySet()) {

            //3、获取controller地址
            String controllerAddress =buildControllerAddress(applicationName);

            //4、扫描获取controller类
            List<Class<?>> classList = getClassList(controllerAddress);

            //5、根据classList来获取List<Bean>
            List<GeneratedBean> generatedBeanList = generateBeanListByClassList(classList);


            System.out.println("sf");

        }




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
     * @desc 设置 isLogin 先放个true  todo
     * @author link.li
     * @date 2023/4/5 22:48
     */
    public static void setIsLogin(GeneratedBean bean, Method method) throws Exception {
        bean.setIsLogin(Boolean.TRUE);
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
     * 根据class地址获取所有的class*
     * @param controllerAddress
     * @return
     * @throws Exception
     */
    public static List<Class<?>> getClassList(String controllerAddress) throws Exception{

        File directory = new File(controllerAddress);

        List<File> fileList = new ArrayList<>();
        read(fileList, directory);

        List<Class<?>> classList = getClassListByFileList(fileList);
        return classList;

    }


    /**
     * 根据fileList 加载出classList*
     * @param fileList
     * @return
     */
    public static List<Class<?>> getClassListByFileList(List<File> fileList) throws Exception {

        List<Class<?>> classList = new ArrayList<>();
        for (File file : fileList) {
            try {
                log.info("文件名称：{}", file.getName());
                Class<?> clazz = getClassFromFile(file);
                classList.add(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

    /**
     * 递归扫描，获取class文件*
     * @param fileList
     * @param dir
     */
    public static void read(List<File> fileList, File dir) {
        File[] files = dir.listFiles();
        if(ObjectUtils.isNotEmpty(files)) {
            for (File f: files) {
                if (f.isFile()) {
                    fileList.add(f);
                } else {
                    read(fileList, f);
                }
            }
        }
    }

    /**
     * 根据地址获取类名*
     * @param classFile
     * @return
     * @throws ClassNotFoundException
     * @throws MalformedURLException
     */
    private static Class<?> getClassFromFile(File classFile)
            throws Exception {

        int idx = classFile.getPath().indexOf("com");
        String className = classFile.getPath().substring(idx, classFile.getPath().length() - 6).replace("\\",".");

        DynamicClassLoader classLoader = new DynamicClassLoader(ClassLoader.getSystemClassLoader());
        return classLoader.loadClassFromFile(className, classFile.getPath());
    }



    /**
     * 组装应用的controller地址*
     * @param applicationName
     * @return
     */
    public static String buildControllerAddress(String applicationName) {

        String controllerAddress = "C:\\Users\\link.li\\IdeaProjects\\ecoflow-applicationName\\applicationName-service\\target\\classes\\com\\ecoflow\\app\\controller";
        return StringUtils.replace(controllerAddress, "applicationName", applicationName);
    }




}
