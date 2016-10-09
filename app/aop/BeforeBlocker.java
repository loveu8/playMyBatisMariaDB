package aop;


import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;

public class BeforeBlocker extends MethodBeforeAdviceInterceptor{

  private static final long serialVersionUID = 1L;

  public BeforeBlocker(MethodBeforeAdvice advice) {
    super(advice);
  }  
}


