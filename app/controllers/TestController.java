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
    String errorCasue = "";
    String errorMessage = "";
    String errorLocationMessage = "";
    StringBuffer errorStackTrace = new StringBuffer("");
    try{
      SignupRequest request = new SignupRequest();
      request.setEmail("222@star.com.tw");
      request.setUsername("222");
      request.setPassword("222");
      fooDao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      errorCasue = e.getCause().toString();
      errorMessage = e.getMessage();
      errorLocationMessage = e.getLocalizedMessage();

      for(StackTraceElement ste :e.getStackTrace() ){
        errorStackTrace.append(ste.getClassName() +
                               "." + ste.getMethodName() + 
                               "(" + ste.getFileName() +
                               ":" + ste.getLineNumber()+")\n");
      }
    } finally {
      System.out.println("casue : " + errorCasue + 
                         " , message : " + errorMessage + 
                         " , locationMessage : " + errorLocationMessage );
      System.out.println("errorStackTrace : " + errorStackTrace.toString());
    }
    return ok("ACID rollback Testing " +
              "\n test Error With Annotation Transation errorMessage = " + tewat);
  }
  
  
}
