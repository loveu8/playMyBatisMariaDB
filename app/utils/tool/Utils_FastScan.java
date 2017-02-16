package utils.tool;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public enum Utils_FastScan {

  un;
  
  /**
   * Add your project top level package 
   */
  final static String[] classPackages 
                        = {"annotation" , "aop" , "common" , "error" , "filters" , "modules" ,
                           "pojo" , "services" , "utils" , "views" , "test"};

  
  /**
   * According classPackages to find our classes
   */
  public List<String> getProjectClasses() {
    List<String> classNames = new ArrayList<String>();
    FastClasspathScanner fastScaner = new FastClasspathScanner(classPackages);
    classNames.addAll(fastScaner.scan().getNamesOfAllClasses());
    return classNames;
  }
 
  
  /**
   * According class to Map our class the Method
   */
  public Map<String , HashMap<String , String>> getProjectClassMethodMapping(){
    Map<String , HashMap<String , String>> classMaps = new HashMap<String , HashMap<String , String>>();
    List<String> classNames = getProjectClasses();
      
    for(String className : classNames){
      Method[] classMethods;
      try {
        classMethods = Class.forName(className).getMethods();
        HashMap<String , String> methodMaps = new HashMap<String , String>();
        for(int index = 0 ; index < classMethods.length ; index++){
          methodMaps.put(classMethods[index].getName(), classMethods[index].getName());
        }
        classMaps.put(className, methodMaps);
      } catch (SecurityException e) {
      } catch (ClassNotFoundException e) {
      }
    }
    return classMaps;
  }

  
}
