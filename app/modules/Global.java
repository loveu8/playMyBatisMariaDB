package modules;


import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import play.cache.DefaultCacheApi;
import play.data.FormFactory;
import play.inject.ApplicationLifecycle;
import services.WebService;

import com.google.inject.Singleton;

import config.AppConfig;
import controllers.WebController;
import controllers.aop.AopController;



@Singleton
public class Global {

  private AnnotationConfigApplicationContext ctx;
  
  @Inject
  public Global(WebService webService , FormFactory formFactory , DefaultCacheApi cacheApi , ApplicationLifecycle lifecycle){
    ctx = new AnnotationConfigApplicationContext();  
    ctx.getBeanFactory().registerSingleton("webService" , webService);
    ctx.getBeanFactory().registerSingleton("formFactory",formFactory);
    ctx.getBeanFactory().registerSingleton("cacheApi" , cacheApi);
    
    // 自定義的AppConfig
    ctx.register(AppConfig.class);
    ctx.refresh(); 
    ctx.registerShutdownHook();
    
    System.out.println("AopController: " + ctx.getBean(AopController.class));
    System.out.println("WebController: " + ctx.getBean(WebController.class));
    System.out.println("webService: " + ctx.getBean(WebService.class));
    System.out.println("cacheApi: " + ctx.getBean(DefaultCacheApi.class));
    
    lifecycle.addStopHook(() -> {       
      ctx.close();
      return CompletableFuture.completedFuture(null);
    });
  }
  

  public <A> A getBean(Class<A> clazz) {
    return ctx.getBean(clazz);
  }

}
