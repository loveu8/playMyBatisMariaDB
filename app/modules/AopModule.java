package modules;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import annotation.AuthCheck;
import aop.AfterBlocker;
import aop.AuthBlocker;
import aop.advice.BeforeAndAfterAdvice;
import aop.BeforeBlocker;

public class AopModule extends AbstractModule {
  
  @Override
  protected void configure() {
    BeforeAndAfterAdvice advice = new BeforeAndAfterAdvice();
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(AuthCheck.class),new BeforeBlocker(advice),new AuthBlocker() , new AfterBlocker(advice));  
  }
  
}

