package controllers.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import play.mvc.Controller;
import annotation.Check;

@Aspect
@Component
public class AopController extends Controller {

  @Around(value="@annotation(check)")
//  @Around(value="controllers.WebController()")
  public Object logger(ProceedingJoinPoint pjp , Check check) throws Throwable {
    play.Logger.info("Around , before is calling");
    pjp.proceed();
    play.Logger.info("Around , after is finish");
    return pjp.getTarget();
  }

}

