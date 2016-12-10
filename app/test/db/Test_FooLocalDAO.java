package test.db;

import org.apache.ibatis.session.SqlSessionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import error.HelperException;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

public class Test_FooLocalDAO extends AbstractFooDAO{

  static Test_FooLocalDAO dao;
  
  @BeforeClass
  public static void before(){
    play.Logger.info("Test_FooLocalDAO , test case start");
    Injector injector = Guice.createInjector(new modules.MyBatisModule());
    sqlSessionManager = injector.getInstance(SqlSessionManager.class);
    webService = injector.getInstance(WebService.class);
    fooServiceImpl = injector.getInstance(FooServiceImpl.class);
    dao = new Test_FooLocalDAO();
  }

  @Test
  public void case1(){
    play.Logger.info("----------------------------------------------");
    play.Logger.info("Case 1 Test SessionManager rollback - start");
    SignupRequest request = new SignupRequest();
    request.setEmail("111@star.com.tw");
    request.setUsername("111");
    request.setPassword("111");
    dao.testErrorWithSessionManager(request);
    play.Logger.info("Test SessionManager rollback - end");
    play.Logger.info("----------------------------------------------");
  }
  
  
  @Test
  public void case2(){
    play.Logger.info("----------------------------------------------");
    play.Logger.info("Case 2 Test Annation Transactional rollback - start");
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("222@star.com.tw");
      request.setUsername("222");
      request.setPassword("222");
      dao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      HelperException.un.genException(e);
    }
    play.Logger.info("Test Annation Transactional rollback - end");
    play.Logger.info("----------------------------------------------");
  }
  
  
  @AfterClass
  public static void after(){
    play.Logger.info("Test_FooLocalDAO , test case finish");
  }
    
}