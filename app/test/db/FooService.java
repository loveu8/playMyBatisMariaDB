package test.db;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.*;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

import error.MyDaoException;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

/**
 * Test Guice and MyBatis Transactional example 
 */
public class FooService{
 
  @Inject
  private WebService webService;

  @Transactional(
      executorType = ExecutorType.BATCH,
      isolation = Isolation.READ_UNCOMMITTED,
      rethrowExceptionsAs = MyDaoException.class,
      exceptionMessage = "error test" 
  )
  public int signupNewMember(@Param("signupRequest") SignupRequest signupRequest) throws MyDaoException {
    int num = this.webService.signupNewMember(signupRequest);
    // error test
    int i = 1 /0;
    return num;
  }

    
}