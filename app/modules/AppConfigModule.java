package modules;

import java.util.ArrayList;
import java.util.List;
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

  static AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
  static List<Class<?>> clazz  = new ArrayList<Class<?>>();
  static List<Class<?>> providers = new ArrayList<Class<?>>();
  
  @Override
  protected void configure() {
        
    System.out.println("AppConfigModule Start bind calss...");
    bindWebControllerProviderType(ControllerListProvider.class);
//    bind(ListProvider.class).asEagerSingleton();
//    for(int index = 0 ; index < ctx.getBeanDefinitionCount() ; index ++){
//      System.out.println("QQ = " + ctx.getBean(ctx.getBeanDefinitionNames()[index]).getClass());
//    }
    System.out.println("AppConfigModule Finish bind...");
    
  }
  
  
  public static class ListProvider {
   
    @Inject
    public ListProvider(WebService webService ,
                  FormFactory formFactory ,
                  DefaultCacheApi cacheApi ,
                  ApplicationLifecycle lifecycle ){
      

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

      genInnerListClazz(ctx , clazz , providers);
      
      System.out.println("-----------------------");
      for(int index = 0 ; index < ctx.getBeanDefinitionCount() ; index ++){        
        System.out.println("class                = " + clazz.get(index));
        System.out.println("bean                 = " + ctx.getBean(clazz.get(index)).getClass());
        System.out.println("providers class      = " + providers.get(index));
        System.out.println("-------------");
      }
      System.out.println("ListProviderType finish");
            
    }
    
    
    private void genInnerListClazz(AnnotationConfigApplicationContext ctx , List<Class<?>> clazz , List<Class<?>> providers){
      for(int index = 0 ; index < ctx.getBeanDefinitionCount() ; index ++){
        
        clazz.add(ctx.getBean(ctx.getBeanDefinitionNames()[index]).getClass());
        Class innerClass  = ctx.getBean(clazz.get(index)).getClass();
        
        @Singleton
        class InnerProvider implements Provider{
          @Override
          public Object get() {
            System.out.println("innerClass = " + innerClass);
            return innerClass;
          }
        }
        providers.add(InnerProvider.class);
      } // end for
    
    } // end genInnerListClazz
 
  } //end ListProvider
 
  
  protected final void bindWebControllerProviderType(Class<? extends Provider<WebController>> webControllerProviderType) {
    System.out.println("WebController = " + WebController.class);
    System.out.println("webControllerProviderType = " + webControllerProviderType);
    bind(WebController.class).toProvider(webControllerProviderType).in(Scopes.SINGLETON);
    System.out.println("bindWebControllerProviderType finish");
  }

  @Singleton
  public static class ControllerListProvider implements Provider<WebController> {
      
    private AnnotationConfigApplicationContext ctx;
    
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
      System.out.println("Spring Bean from AppConfigModule Inject , bean = " + ctx.getBean(WebController.class));
      return ctx.getBean(WebController.class);
    }
  }
 
}