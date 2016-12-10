package controllers;

import javax.inject.Inject;

import error.HelperException;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.signup.request.SignupRequest;
import test.db.FooDAO;


public class TestController extends Controller {

  
  @Inject
  FooDAO fooDao ;
  
  
  public Result sessionManagerACID(){
    SignupRequest request = new SignupRequest();
    request.setEmail("111@star.com.tw");
    request.setUsername("111");
    request.setPassword("111");
    String tewsm = fooDao.testErrorWithSessionManager(request);
    return ok("ACID rollback Testing " + 
              "\n test Error With SessionManager errorMessage = " + tewsm );
  }
  

  public Result annotationACID(){
    String tewat = "";
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("222@star.com.tw");
      request.setUsername("222");
      request.setPassword("222");
      fooDao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      tewat = HelperException.un.genException(e);
    } finally {
    }
    return ok("ACID rollback Testing " +
              "\n test Error With Annotation Transation errorMessage = " + tewat);
  }

  
  
}
