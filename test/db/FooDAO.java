package db;

import org.apache.ibatis.session.*;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pojo.web.signup.request.SignupRequest;
import services.WebService;

public final class FooDAO {

    private final SqlSessionManager sessionManager;
    
    private final WebService webService;

    public FooDAO() {
      Injector injector = Guice.createInjector(new modules.MyBatisModule());
      this.sessionManager = injector.getInstance(SqlSessionManager.class);
      this.webService = injector.getInstance(WebService.class);
    }

    public void doFooBarWithSessionManager(){
      // Starts a new SqlSession
      this.sessionManager.startManagedSession(ExecutorType.BATCH,TransactionIsolationLevel.READ_UNCOMMITTED);
      
      try {
          SignupRequest request = new SignupRequest();
          request.setEmail("123@yahoo.com.tw");
          request.setUsername("555");
          request.setPassword("1234");
          request.setRetypePassword("1234");
          webService.signupNewMember(request);     
          int i = 1 / 0 ;
          this.sessionManager.commit();
      } catch (Exception e) {
          e.printStackTrace();
          this.sessionManager.rollback();
          System.out.println("catch error , not commit reset");
      } finally {
          this.sessionManager.close();
      }
    }
    
    @Transactional(
        executorType = ExecutorType.BATCH,
        isolation = Isolation.READ_UNCOMMITTED,
        rethrowExceptionsAs = java.lang.Exception.class,
        exceptionMessage = "error"
    )
    public void doFooBarWithAnnTransation(){
      try {
        SignupRequest request = new SignupRequest();
        request.setEmail("456@yahoo.com.tw");
        request.setUsername("666");
        request.setPassword("1234");
        request.setRetypePassword("1234");
        webService.signupNewMember(request);
        int i = 1 / 0 ;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    
    public static void main(String[] args) {
      FooDAO dao = new FooDAO();
      dao.doFooBarWithSessionManager();
      dao.doFooBarWithAnnTransation();
    }
    
    
}