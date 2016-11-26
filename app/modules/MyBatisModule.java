package modules;

import java.util.Properties;

import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.common.collect.ImmutableMap;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.Configuration;
import play.db.Database;
import play.db.Databases;
import services.UserService;
import services.WebService;

public class MyBatisModule extends org.mybatis.guice.MyBatisModule {

  @Override
  protected void initialize() {
    // 使用Play內建Databases物件，使用createFrom連線到DB
    usePlayStyleConnectDatabase();
    
    // 使用MyBaits官方說明，連線到DB
//    useMyBaitsStyleConnectDatabase();
  }
  
  protected void usePlayStyleConnectDatabase(){
    environmentId("dev");
    bindConstant().annotatedWith(Names.named("mybatis.configuration.failFast")).to(true);
    bindDataSourceProviderType(PlayDataSourceProvider.class);
    bindTransactionFactoryType(JdbcTransactionFactory.class);
    //把我們要呼叫DB的類別，增加到MapperClass
    addMapperClass(UserService.class);
    addMapperClass(WebService.class);
  }
  

  // 產出一個單例模式的連線
  @Singleton
  public static class PlayDataSourceProvider implements Provider<DataSource> {

    final Database db;

    private Configuration configuration;

    // 利用 ConfigFactory，自動取得application.conf設定檔
    public PlayDataSourceProvider() {
      ClassLoader classLoader = modules.MyBatisModule.class.getClassLoader();
      Config config = ConfigFactory.load(classLoader);
      this.configuration = new Configuration(config);
      String schema = configuration.getString("db.play.schema");
      String driver = configuration.getString("db.play.driver");
      String url = configuration.getString("db.play.url");
      String user = configuration.getString("db.play.user");
      String password = configuration.getString("db.play.password");

      // 建立Database物件
      Database database = Databases.createFrom(schema, driver, url,
          ImmutableMap.of("user", user, "password", password));
      this.db = database;
    }
    
    @Override
    public DataSource get() {
      return db.getDataSource();
    }

    /*
     * // 可參考，若只希望給Contorller自動Inject，不期望被其它方法呼叫時 
     * // 可以利用Inject，自動取得Configuration設定檔，取得DB連線資訊
     * 
     * @Inject public PlayDataSourceProvider(Configuration config) {
     * 
     * this.configuration = config;
     * 
     * // 取得conf檔案 資料庫設定 
     * String schema = configuration.getString("db.play.schema");
     * String driver = configuration.getString("db.play.driver"); 
     * String url = configuration.getString("db.play.url"); 
     * String user = configuration.getString("db.play.user"); 
     * String password = configuration.getString("db.play.password");
     * 
     * // 建立Database物件
     * Database database 
     * = Databases.createFrom( schema, driver, url,
     *          ImmutableMap.of( "user", user, 
     *                           "password", password ) 
     *                         ); 
     * this.db = database; 
     * }
     */

  }

  
  protected void useMyBaitsStyleConnectDatabase(){
    install(JdbcHelper.MariaDB);
    bindDataSourceProviderType(PooledDataSourceProvider.class);
    bindTransactionFactoryType(JdbcTransactionFactory.class);
    addMapperClass(UserService.class);
    addMapperClass(WebService.class);
    Names.bindProperties(binder(), createProperties());
  }
  
  
  private Properties createProperties(){
    ClassLoader classLoader = modules.MyBatisModule.class.getClassLoader();
    Config config = ConfigFactory.load(classLoader);
    Configuration configuration = new Configuration(config);
    String schema = configuration.getString("db.play.schema");
    String user = configuration.getString("db.play.user");
    String password = configuration.getString("db.play.password");
    
    Properties myBatisProperties = new Properties();
    myBatisProperties.setProperty("mybatis.environment.id", "dev");
    myBatisProperties.setProperty("JDBC.schema", schema);
    myBatisProperties.setProperty("JDBC.username", user);
    myBatisProperties.setProperty("JDBC.password", password);
    return myBatisProperties;
  }

}
