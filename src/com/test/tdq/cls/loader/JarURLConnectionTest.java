package com.test.tdq.cls.loader;

 
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import sun.net.www.protocol.jar.Handler;
 
/**
 * @author ashraf
 *
 */
public class JarURLConnectionTest {
     
     private final static String JAR_URL = "jar:file:/home/litao/test.jar!/lib/ojdbc14-10.2.0.4.0.jar";
     private final static String JAR_FILE_PATH = "file:/home/litao/myworkspaces/ideaworkspace/yotoo/target/yotoo.jar";
     private static URLClassLoader urlClassLoader;
 
     /**
      * @param args
      * @throws Exception 
      */
     public static void main(String[] args) throws Exception {
 
          try {
 
               // Create a URL that refers to a jar file in the file system
               URL FileSysUrl = new URL(JAR_URL);
               // Get the jar URL which contains target class
               URL[] classLoaderUrls = new URL[]{FileSysUrl};
               // Create a classloader and load the entry point class
               
               JarURLConnection jarURLConnection = new sun.net.www.protocol.jar.JarURLConnection(FileSysUrl, new Handler());
  
            
               System.out.println(jarURLConnection.getJarFileURL());
 
               // Load the target class
               Class beanClass = urlClassLoader.loadClass("oracle.jdbc.driver.OracleDriver"); 
               
               System.out.println(jarURLConnection.getJarFile());
 
          } catch (MalformedURLException e) {
               e.printStackTrace();
          } catch (IOException e) {
               e.printStackTrace();
          }
 
     }
     public static URL getJarUrl(final File file) throws IOException {
         return new URL("jar:" + file.toURI().toURL().toExternalForm() + "!/");
     }
}
 
