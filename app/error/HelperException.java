package error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.libs.Json;
import pojo.error.ErrorLogInfo;
import pojo.error.RelatedClass;
import utils.tool.Utils_FastScan;

public enum HelperException {

  un;

  /**
   * <pre>
   * Check 1 : 確認是否有 Exception
   * Check 2 : 印出相關errorMessage
   * Check 3 : 若有input，印出相關input的參數
   * Check 4 : 印出相關Class method , line number error
   * </pre> 
   */
  public String genException(Exception e , Object... inputs) {
    if(e == null){
      return "";
    }

    ErrorLogInfo errorLogInfo = new ErrorLogInfo();
    errorLogInfo.setCasue(e.getCause()!=null ? e.getCause().toString() : "null");
    errorLogInfo.setMessage(e.getMessage());
    errorLogInfo.setLocalMessage(e.getLocalizedMessage());
    errorLogInfo.setInputs(Arrays.asList(inputs));

    Map<String , HashMap<String , String>> classMaps = Utils_FastScan.un.getProjectClassMethodMapping();
    List<RelatedClass> relatedClasses = new ArrayList<RelatedClass>();
    for (StackTraceElement ele : e.getStackTrace()) {
      if(classMaps != null && 
         classMaps.get(ele.getClassName()) != null && 
         classMaps.get(ele.getClassName()).get(ele.getMethodName()) != null ){
        RelatedClass realtedClass = new RelatedClass();
        realtedClass.setClassName(ele.getClassName());
        realtedClass.setMethod(ele.getMethodName());
        realtedClass.setLineNumber(Integer.toString(ele.getLineNumber()));
        relatedClasses.add(realtedClass);
      }
    }
    errorLogInfo.setRelatedClasses(relatedClasses);
    
    play.Logger.error(Json.toJson(errorLogInfo).toString());
    return Json.toJson(errorLogInfo).toString();
  }



}
