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
  public static SqlSessionManager sqlSessionManager;
  
  @Inject
  public static WebService webService;
  
  @Inject
  public static FooServiceImpl fooServiceImpl;
  
  public static String testErrorWithSessionManager(SignupRequest request){
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
      errorMessage = e.getMessage();
      for(StackTraceElement ste :e.getStackTrace() ){
        System.out.println("class:" + ste.getClassName() +
                           " , method:" + ste.getMethodName() + 
                           " , fieldName: " + ste.getFileName() +
                           " , lineNumber:" + ste.getLineNumber());
      }
      sqlSessionManager.rollback();
    } finally {
      sqlSessionManager.close();
    }
    return errorMessage;
  }
   
  
  public void testErrorWithAnnotationTransation(SignupRequest request) throws MyDaoException {
    fooServiceImpl.signupNewMember(request);
  }
  
}
