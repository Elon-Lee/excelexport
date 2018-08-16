package com.test.tdq.service;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 根据properties中配置的路径把jar和配置文件加载到classpath中。
 * @author jnbzwm
 *
 */
public final class ExtClasspathLoader {
    /** URLClassLoader的addURL方法 */
    private static Method addURL = initAddMethod();

    private static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 初始化addUrl 方法.
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            add.setAccessible(true);
            return add;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载jar classpath。
     */
    public static void loadClasspath() {
        List<String> files = getJarFiles();
        for (String f : files) {
            loadClasspath(f);
        }

        List<String> resFiles = getResFiles();

        for (String r : resFiles) {
            loadResourceDir(r);
        }
    }

    static void loadClasspath(String filepath) {
    	try { 
			final File jarFile = new File(ExtClasspathLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			System.out.println(jarFile);
			if(jarFile.isFile()) {  // Run with JAR file
			    final JarFile jar = new JarFile(jarFile);
			    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
			    List<URL> listUrls = new ArrayList<URL>();
			    while(entries.hasMoreElements()) {
                    JarEntry entry = (JarEntry)entries.nextElement();
			        final String name = entry.getName();
			        jar.getInputStream(entry);
			        if(name.matches(".*\\.jar")){
			        	System.out.println("jar:file:"+jarFile.getPath()+"!/"+name);
			           File file = new File(ExtClasspathLoader.class.getResource("/"+name).getPath());
			           listUrls.add( new URL("jar:file:"+jarFile.getPath()+"!/"+name)); 
			        } 
			   }
			    // Create a classloader and load the entry point class
                URLClassLoader urlClassLoader = new URLClassLoader((URL[]) listUrls.toArray(new URL[0]));
                System.out.println(listUrls.toArray(new URL[0]).length+"sssssssssssss");
                // Load the target class
                Class beanClass = urlClassLoader.loadClass("oracle.jdbc.driver.OracleDriver");
                Thread.currentThread().setContextClassLoader(urlClassLoader);
			    jar.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    private static void loadResourceDir(String filepath) {
        File file = new File(filepath);
        loopDirs(file);
    }

    /**    
     * 循环遍历目录，找出所有的资源路径。
     * @param file 当前遍历文件
     */
    private static void loopDirs(File file) {
        // 资源文件只加载路径
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopDirs(tmp);
            }
        }
    }

    /**    
     * 循环遍历目录，找出所有的jar包。
     * @param file 当前遍历文件
     */
    private static void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp);
            }
        }
        else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {
            	System.out.println("load jar file "+file.getPath());
                addURL(file);
            }
        }
    }

    /**
     * 通过filepath加载文件到classpath。
     * @param filePath 文件路径
     * @return URL
     * @throws Exception 异常
     */
    private static void addURL(File file) {
        try {
            addURL.invoke(classloader, new Object[] { file.toURI().toURL() });
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * 从配置文件中得到配置的需要加载到classpath里的路径集合。
     * @return
     */
    private static List<String> getJarFiles() {
        // TODO 从properties文件中读取配置信息略
        return null;
    }

    /**
     * 从配置文件中得到配置的需要加载classpath里的资源路径集合
     * @return
     */
    private static List<String> getResFiles() {
        //TODO 从properties文件中读取配置信息略
        return null;
    }

    public static void main(String[] args) {
        ExtClasspathLoader.loadClasspath("../../../../lib");
    }
}
