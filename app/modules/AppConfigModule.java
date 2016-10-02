package modules;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import config.AppConfig;
import controllers.WebController;
import play.cache.DefaultCacheApi;
import play.data.FormFactory;
import play.inject.ApplicationLifecycle;
import services.WebService;

public class AppConfigModule extends AbstractModule {

  @Override
  protected void configure() {
    bindWebControllerProviderType(ControllerListProvider.class);
  }
  
  protected final void bindWebControllerProviderType(Class<? extends Provider<WebController>> webControllerProviderType) {
    bind(WebController.class).toProvider(webControllerProviderType).in(Scopes.SINGLETON);
  }
    
  @Singleton
  public static class ControllerListProvider implements Provider<WebController> {
    
    public static AnnotationConfigApplicationContext ctx;
    
   
    @Inject
    public ControllerListProvider(WebService webService , 
                  FormFactory formFactory , 
                  DefaultCacheApi cacheApi , 
                  ApplicationLifecycle lifecycle){
      ctx = new AnnotationConfigApplicationContext();  
      // 自定義的AppConfig
      ctx.register(AppConfig.class);
      ctx.getBeanFactory().registerSingleton("webService" , webService);
      ctx.getBeanFactory().registerSingleton("formFactory",formFactory);
      ctx.getBeanFactory().registerSingleton("cacheApi" , cacheApi);
      ctx.refresh(); 
      ctx.registerShutdownHook();
      ctx.start();

      lifecycle.addStopHook(() -> {    
        ctx.close();
        return CompletableFuture.completedFuture(null);
      });
      
      System.out.println("ControllerListProvider finish");
    }
    
    @Override 
    public WebController get() {
      return ctx.getBean(WebController.class);
    }
  }
}
