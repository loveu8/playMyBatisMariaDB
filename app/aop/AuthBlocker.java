package aop;

import org.aopalliance.intercept.MethodInvocation;

public class AuthBlocker extends CommonBeforeBlocker{

  
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    super.invoke(invocation);
    play.Logger.info("Check Auth Now");
    return invocation.proceed();
  }
  
}

