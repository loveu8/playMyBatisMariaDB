package modules;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

import annotation.AuthCheck;
import aop.AuthBlocker;

public class AopModule extends AbstractModule {
  
  @Override
  protected void configure() {
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(AuthCheck.class),new AuthBlocker());
  }
  
}

