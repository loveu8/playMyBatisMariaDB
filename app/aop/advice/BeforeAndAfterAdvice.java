package aop.advice;

import java.lang.reflect.Method;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAndAfterAdvice implements MethodBeforeAdvice , AfterReturningAdvice {

  long startTime;
  long endTime;
  Format format = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
  
  @Override
  public void before(Method method, Object[] args, Object target) throws Throwable {
    startTime = System.currentTimeMillis();
    String name = method.getName();
    play.Logger.info("before target = "+ target +",method = "+ name 
                     +" ,start time = " + formatTime(startTime));
  }

  @Override
  public void afterReturning(Object returnValue, Method method, Object[] args, Object target)
      throws Throwable {
    endTime = System.currentTimeMillis();
    String name = method.getName();
    play.Logger.info("after target = "+ target +",method = "+ name 
                      + ", end time = " + formatTime(endTime)
                      +" , cost time = " + (endTime - startTime) + "ms" );
  }
  
  public String formatTime(long time){
    return format.format(new Date(time));
  }
  
}
