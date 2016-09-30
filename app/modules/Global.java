package modules;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.inject.Singleton;

import config.AppConfig;



@Singleton
public class Global {

  private AnnotationConfigApplicationContext ctx;
  
  public Global(){
    ctx = new AnnotationConfigApplicationContext();  
    ctx.register(AppConfig.class);
    ctx.refresh(); 
    ctx.registerShutdownHook();
  }
  

  public <A> A getBean(Class<A> clazz) {
    return ctx.getBean(clazz);
  }

}
