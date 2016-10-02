package controllers.aop;

import javax.inject.Singleton;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import play.mvc.Result;
import play.mvc.Controller;
import annotation.Check;

@Aspect
@Component
public class AopController extends Controller {
  

  @Around(value="@annotation(check)")
  public Result logger(ProceedingJoinPoint pjp , Check check) throws Throwable {
    play.Logger.info("Around , is calling");
    return (Result) pjp.proceed(pjp.getArgs());
  }

}

