package test.db;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionManager;
import org.apache.ibatis.session.TransactionIsolationLevel;

import com.google.inject.Inject;

import error.MyDaoException;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

public abstract class AbstractFooDAO {
  
  @Inject
  public SqlSessionManager sqlSessionManager;
  
  @Inject
  public WebService webService;
  
  @Inject
  public FooServiceImpl fooServiceImpl;
  
  public String testErrorWithSessionManager(SignupRequest request) throws ArithmeticException{
    sqlSessionManager.startManagedSession(
      ExecutorType.BATCH,
      TransactionIsolationLevel.READ_UNCOMMITTED);
    String errorMessage = "";
    try {
      webService.signupNewMember(request);
      // error test 
      int i = 1 / 0 ;
      sqlSessionManager.commit();
    } catch (Exception e) {
      e.printStackTrace();
      errorMessage = "cause:" + e.getCause() + ",message:" + e.getMessage();
      sqlSessionManager.rollback();
    }finally {
      sqlSessionManager.close();
    }
    return errorMessage;
  }
   
  
  public void testErrorWithAnnotationTransation(SignupRequest request) throws MyDaoException {
    fooServiceImpl.signupNewMember(request);
  }
  
}
