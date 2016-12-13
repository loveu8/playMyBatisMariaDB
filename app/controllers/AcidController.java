package controllers;

import javax.inject.Inject;

import error.HelperException;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.signup.request.SignupRequest;
import test.db.FooDAO;


/**
 * ACID testing
 * 
 * TEST CASE : @link {Test_FooLocalDAO}
 */
public class AcidController extends Controller {

  @Inject
  FooDAO fooDao ;

  public Result sessionManagerACID(){
    SignupRequest request = new SignupRequest();
    request.setEmail("111@star.com.tw");
    request.setUsername("111");
    request.setPassword("111");
    String tewsm = fooDao.testErrorWithSessionManager(request);
    return ok("sessionManagerACID rollback Testing " + 
              "\n Error info = " + tewsm );
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
      tewat = "cause:" + e.getCause() + ",message:" + e.getMessage();
    } finally {
    }
    return ok("annotationACID rollback Testing " +
              "\n Error info = " + tewat);
  }

  public Result annotationACIDWithHelperException(){
    String tewat = "";
    SignupRequest request = new SignupRequest();
    try{
      request.setEmail("333@star.com.tw");
      request.setUsername("333");
      request.setPassword("333");
      fooDao.testErrorWithAnnotationTransation(request);
    } catch (Exception e) {
      e.printStackTrace();
      tewat = HelperException.un.genException(e,request);
    }
    return ok("annotationACID With HelperException rollback Testing " +
              "\n Error info = " + tewat);
  }
  
}
