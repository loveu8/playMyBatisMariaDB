package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;

import annotation.CheckLogin;
import aop.LoginBlocker;
import services.WebService;

public class AopModule extends AbstractModule {
  
  @Override
  protected void configure() {
    Injector injector = Guice.createInjector(new modules.MyBatisModule());
    WebService webService = injector.getInstance(WebService.class);
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(CheckLogin.class), new LoginBlocker(webService));
  }
  
 
}