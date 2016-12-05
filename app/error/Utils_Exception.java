package error;

public enum Utils_Exception {
  
  un;
  
  public String genException(Class<?> thisClass , Exception e){
    StringBuffer errorMessage = new StringBuffer("");
    for(StackTraceElement ele : e.getStackTrace()){
      if(ele.getMethodName().equals(thisClass.getEnclosingMethod().getName())){
        errorMessage.append("class : " + thisClass.getEnclosingClass()  +
                       " , method : " + thisClass.getEnclosingMethod().getName() + 
                       " , lineNumber : " + ele.getLineNumber());
        break;
      }
    }
    errorMessage.append(" , casue : " + e.getCause() + 
                        " , message : " + e.getMessage() + 
                        " , locationMessage : " + e.getLocalizedMessage());
    play.Logger.error(errorMessage.toString());
    return errorMessage.toString();
  }

}
