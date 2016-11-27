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
  public FooService fooService;
  
  public String testErrorWithSessionManager(){
    this.sqlSessionManager.startManagedSession(
          ExecutorType.BATCH,
          TransactionIsolationLevel.READ_UNCOMMITTED);
    String errorMessage = "";
    try {
        SignupRequest request = new SignupRequest();
        request.setEmail("111@star.com.tw");
        request.setUsername("111");
        request.setPassword("111");
        request.setRetypePassword("111");
        webService.signupNewMember(request);
        // error test 
        int i = 1 / 0 ;
        this.sqlSessionManager.commit();
    } catch (Exception e) {
        e.printStackTrace();
        errorMessage = e.getMessage();
        this.sqlSessionManager.rollback();
    } finally {
        this.sqlSessionManager.close();
    }
    return errorMessage;
  }
    
  public void testErrorWithAnnotationTransation() throws MyDaoException {
    SignupRequest request = new SignupRequest();
    request.setEmail("222@star.com.tw");
    request.setUsername("222");
    request.setPassword("222");
    request.setRetypePassword("222");
    fooService.signupNewMember(request);
  }
  
}
