package error;

import java.lang.reflect.Method;
import java.util.List;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public enum HelperException {

  un;

  public String genException(Class<?> thisClass, Exception e) {

    StringBuffer errorMessage = new StringBuffer("");

    List<String> classNames = new FastClasspathScanner("controllers").scan().getNamesOfAllClasses();
    classNames.addAll(new FastClasspathScanner("test").scan().getNamesOfAllClasses());

    for (StackTraceElement ele : e.getStackTrace()) {
      for (String className : classNames) {
        if (ele.getClassName().equals(className)) {
          try {
            Method[] methods = Class.forName(className).getMethods();
            for (int i = 0; i < methods.length; i++) {
              if (methods[i].getName().equals(ele.getMethodName())) {
                errorMessage.append("class : " + className + " , method : " + methods[i].getName()
                    + " , lineNumber : " + ele.getLineNumber() + "\n");
                break;
              }
            }
          } catch (SecurityException e1) {
          } catch (ClassNotFoundException e1) {
          }
          break;
        }
      }
    }
    errorMessage.append(" , casue : " + e.getCause() + " , message : " + e.getMessage()
        + " , locationMessage : " + e.getLocalizedMessage());
    play.Logger.error(errorMessage.toString());

    return errorMessage.toString();
  }


}
