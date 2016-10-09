package aop;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import play.cache.DefaultCacheApi;
import play.data.FormFactory;
import play.data.format.Formatters;
import play.i18n.MessagesApi;
import services.WebService;


/**
 * <pre>
 *  Super Class
 *  該類別用途是當方法進來時
 *  去取得目前Play current injector的相關服務
 *  WebService       (modules.MyBatisModule Inject WebService)
 *  FormFactory      (Play Inject FormFactory)
 *  DefaultCacheApi  (Play Inject DefaultCacheApi)
 * </pre>
 */
public class CommonBeforeBlocker implements MethodInterceptor{
  
  protected WebService webService;
  protected FormFactory formFactory;
  protected DefaultCacheApi cache;
  
  private static play.api.inject.Injector injector() {
    return play.api.Play.current().injector();
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    play.Logger.info("CommonBeforeBlocker get Play current Inject Class");
    this.webService = injector().instanceOf(WebService.class);
    this.formFactory = new FormFactory(injector().instanceOf(MessagesApi.class), injector().instanceOf(Formatters.class), injector().instanceOf(javax.validation.Validator.class));
    this.cache = new DefaultCacheApi(injector().instanceOf(play.api.cache.CacheApi.class));
    play.Logger.info("Joinpoint   = " + invocation.getThis().getClass());
    play.Logger.info("getClass    = " + invocation.getClass());
    play.Logger.info("proceed     = " + invocation.proceed());
    play.Logger.info("method      = " + invocation.getMethod());
    play.Logger.info("arguments   = " + invocation.getArguments());
    play.Logger.info("webService  = " + webService);
    play.Logger.info("formFactory = " + formFactory);
    play.Logger.info("formFactory = " + cache);
    return invocation.proceed();
  }
}

