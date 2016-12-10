package error;

import java.util.HashMap;
import java.util.Map;

import utils.tool.Utils_FastScan;

public enum HelperException {

  un;

  public String genException(Exception e) {

    e.printStackTrace();
    System.out.println("---------------------");
    StringBuffer errorMessage = new StringBuffer("");
    errorMessage.append("Casue : " + e.getCause() + 
        " , Message : " + e.getMessage() + 
        " , LocationMessage : " + e.getLocalizedMessage() +
        " , Error related class => { ");
    
    Map<String , HashMap<String , String>> classMaps = Utils_FastScan.un.getProjectClassMethodMapping();
    for (StackTraceElement ele : e.getStackTrace()) {
      if(classMaps != null && 
         classMaps.get(ele.getClassName()) != null && 
         classMaps.get(ele.getClassName()).get(ele.getMethodName()) != null ){
        errorMessage.append("[class : " + ele.getClassName() + 
                            " , method : " + ele.getMethodName() + 
                            " , lineNumber : " + ele.getLineNumber() + "] , ");
      }
    }
    errorMessage.replace(errorMessage.length()-2 , errorMessage.length(), "").append(" } ");
    play.Logger.error(errorMessage.toString());
    return errorMessage.toString();
  }



}
