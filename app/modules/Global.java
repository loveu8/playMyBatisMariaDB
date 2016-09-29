package modules;

import javax.inject.Inject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.inject.Singleton;

import config.AppConfig;
import play.data.FormFactory;


@Singleton
public class Global {

  private AnnotationConfigApplicationContext ctx;
  
  @Inject
  public Global(FormFactory formFactory){
    ctx = new AnnotationConfigApplicationContext();  
    ctx.getBeanFactory().registerSingleton("formFactory",formFactory);
    ctx.register(AppConfig.class);
    ctx.refresh(); 
    ctx.registerShutdownHook();
  }
  

  public <A> A getBean(Class<A> clazz) {
    return ctx.getBean(clazz);
  }

}
