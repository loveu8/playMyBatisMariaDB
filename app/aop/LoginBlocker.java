package aop;

import javax.inject.Inject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import play.mvc.Controller;
import services.WebService;

public class LoginBlocker extends Controller implements MethodInterceptor{
  

  private WebService webService;
  
  @Inject
  public LoginBlocker(WebService webService){
    this.webService = webService;
  }
  
  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("Chech login now");
    return invocation.proceed();
  }
}
