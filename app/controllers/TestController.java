package controllers;

import javax.inject.Inject;

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
      e.printStackTrace();
      tewat = e.getMessage();
    } finally {
    }
    return ok("ACID rollback Testing " +
              "\n test Error With Annotation Transation errorMessage = " + tewat);
  }
  
  public Result annotationACIDwithExceptionLog(){
    String tewat = "";
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("222@star.com.tw");
      request.setUsername("222");
      request.setPassword("222");
      fooDao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      for(StackTraceElement ste :e.getStackTrace() ){
        System.out.println("class:" + ste.getClassName() +
                           " , method:" + ste.getMethodName() + 
                           " , fieldName: " + ste.getFileName() +
                           " , lineNumber:" + ste.getLineNumber());
      }
      tewat = "casue : " + e.getCause() + " , locationMessage : " + e.getLocalizedMessage();
    } finally {
    }
    return ok("ACID rollback Testing " +
              "\n test Error With Annotation Transation errorMessage = " + tewat);
  }
  
  
}
