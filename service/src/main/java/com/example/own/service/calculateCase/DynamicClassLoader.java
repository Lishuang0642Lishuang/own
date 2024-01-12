package com.example.own.service.calculateCase;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class DynamicClassLoader extends ClassLoader{

    private String classPath;

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public DynamicClassLoader(String classPath, ClassLoader parent) {
        super(parent);
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte[] classBytes = loadClassBytes(className);
            return defineClass(className, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to load class: " + className, e);
        }
    }

    private byte[] loadClassBytes(String className) throws IOException {
        Path classFilePath = Paths.get(classPath, className.replace('.', '/') + ".class");
        return Files.readAllBytes(classFilePath);
    }

    public Class<?> loadClassFromFile(String className, String javaFilePath) throws Exception {

            Path path = Paths.get(javaFilePath);
            byte[] bytes = Files.readAllBytes(path);

            Class<?> clazz = defineClass(className, bytes, 0, bytes.length);

            // 使用defineClass加载类
            return clazz;
    }
//
//    private String readJavaFile(String className) throws IOException {
//        String javaFilePath = basePath + className.replace('.', '\\') + ".java";
//        Path path = Paths.get(javaFilePath);
//        byte[] bytes = Files.readAllBytes(path);
//        return new String(bytes);
//    }
//
//    private byte[] compileJavaCode(String className, String javaCode) throws IOException {
//        // 编译.java文件的代码略，可以使用Java Compiler API或其他方式实现
//        // 这里简化为直接返回.java文件的字节码，实际中需要调用编译器来完成这个步骤
//        // 参考前面提到的编译Java文件的示例
//
//
//        return javaCode.getBytes();
//    }
}
