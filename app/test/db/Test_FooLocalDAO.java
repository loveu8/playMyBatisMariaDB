package test.db;

import org.apache.ibatis.session.SqlSessionManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pojo.web.signup.request.SignupRequest;
import services.WebService;

public class Test_FooLocalDAO extends AbstractFooDAO{

  static Test_FooLocalDAO dao;
  
  @BeforeClass
  public static void before(){
    System.out.println("Test_FooLocalDAO , test case start");
    Injector injector = Guice.createInjector(new modules.MyBatisModule());
    sqlSessionManager = injector.getInstance(SqlSessionManager.class);
    webService = injector.getInstance(WebService.class);
    fooServiceImpl = injector.getInstance(FooServiceImpl.class);
    dao = new Test_FooLocalDAO();
  }

  @Test
  public void case1(){
    System.out.println("----------------------------------------------");
    System.out.println("Test SessionManager rollback - start");
    SignupRequest request = new SignupRequest();
    request.setEmail("111@star.com.tw");
    request.setUsername("111");
    request.setPassword("111");
    System.out.println("error message = " + dao.testErrorWithSessionManager(request));
    System.out.println("Test SessionManager rollback - end");
    System.out.println("----------------------------------------------");
  }
  
  
  @Test
  public void case2(){
    System.out.println("----------------------------------------------");
    System.out.println("Case 2 Test Annation Transactional rollback one - start");
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("222@star.com.tw");
      request.setUsername("222");
      request.setPassword("222");
      dao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      System.out.println("error message = " + e.getMessage());
    }
    System.out.println("Test Annation Transactional rollback one - end");
    System.out.println("----------------------------------------------");
  }
  
  
  @Test
  public void case3(){
    System.out.println("----------------------------------------------");
    System.out.println("Case 3 : Test Annation Transactional rollback with print Exception  - start");
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("333@star.com.tw");
      request.setUsername("333");
      request.setPassword("333");
      dao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      System.out.println("casue : " + e.getCause() + " , locationMessage : " + e.getLocalizedMessage());
      for(StackTraceElement ste :e.getStackTrace() ){
        System.out.println("class:" + ste.getClassName() +
                           " , method:" + ste.getMethodName() + 
                           " , fieldName: " + ste.getFileName() +
                           " , lineNumber:" + ste.getLineNumber());
      }
    }
    System.out.println("Case 3 : Test Annation Transactional rollback with print Exception - end");
    System.out.println("----------------------------------------------");
  }
  
  @AfterClass
  public static void after(){
    System.out.println("Test_FooLocalDAO , test case finish");
  }
    
}