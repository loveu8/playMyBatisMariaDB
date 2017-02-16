package test.db;

import org.apache.ibatis.session.*;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Inject;

import error.ErrorMessage;
import error.MyDaoException;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

/**
 * Test Guice and MyBatis Transactional example 
 */
public class FooServiceImpl{
 
  @Inject
  private WebService webService;

  @Transactional(
      executorType = ExecutorType.BATCH,
      isolation = Isolation.READ_UNCOMMITTED,
      rethrowExceptionsAs = MyDaoException.class,
      exceptionMessage = ErrorMessage.DAO_ERROR1
  )
  public int signupNewMember(SignupRequest signupRequest) throws MyDaoException {
    int num = this.webService.signupNewMember(signupRequest);
    // error test
    num = num / 0;
    return num;
  }

    
}