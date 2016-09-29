package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;

import aop.AOP;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({"aop.AOP" , "controllers" , "utils" , "services"})
public class AppConfig {
  
  @Bean
  public AOP aop() {
    return new AOP();
  }
  
      
  @Bean
  @Scope("prototype")
  public play.mvc.Security.AuthenticatedAction security$AuthenticatedAction(play.DefaultApplication playApp) {
          return playApp.injector().instanceOf(play.mvc.Security.AuthenticatedAction.class);      
  }       
  
  
}