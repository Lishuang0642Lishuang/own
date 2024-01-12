package com.example.own.service.calculateCase;

public class ClassLoaderExample {

    public static void main(String[] args) {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("System Class Loader: " + systemClassLoader);

        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        System.out.println("Extension Class Loader: " + extensionClassLoader);

        // Bootstrap Class Loader 通常为 null，因为它是由 C++ 编写的，无法在 Java 代码中直接引用
        ClassLoader bootstrapClassLoader = extensionClassLoader.getParent();
        System.out.println("Bootstrap Class Loader: " + bootstrapClassLoader);

        // 获取类加载路径
        System.out.println("Classpath: " + System.getProperty("java.class.path"));

        String classPath = System.getProperty("java.class.path");
        String[] array = classPath.split(";");
        for (String str: array) {
            System.out.println(str);
        }
        System.out.println("Extension Library Path: " + System.getProperty("java.ext.dirs"));

    }
}
