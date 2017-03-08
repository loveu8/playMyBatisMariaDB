package utils.enc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * <pre>
 *  加解密工具
 * </pre>
 */
public class EncAndDeCodeTool {
  
  public String urlAndBase64Encode(String unencodeStr){
    return this.base64Encode(this.urlEncode(unencodeStr));
  }
  
  public String urlAndBase64Decode(String encodeStr){
    return this.urlDecode(this.base64Decode(encodeStr));
  }
  
  public String urlEncode(String encodeStr){
    try {
      return URLEncoder.encode(encodeStr , "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  public String base64Encode(String encodeStr){
    return new String (Base64.getEncoder().encode(encodeStr.getBytes()));
  }
  
  public String urlDecode(String decodeStr){
    try {
      return URLDecoder.decode(decodeStr , "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }
  
  public String base64Decode(String decodeStr){
    return new String (Base64.getDecoder().decode(decodeStr.getBytes()));
  }
  
  
  public static void main(String[] args) {
    String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
  }
  
  
}
