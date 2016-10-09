package aop;


import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;

public class AfterBlocker extends AfterReturningAdviceInterceptor{

  private static final long serialVersionUID = 1L;
  
  public AfterBlocker(AfterReturningAdvice advice) {
    super(advice);
  }
  
}


