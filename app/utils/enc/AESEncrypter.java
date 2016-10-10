package utils.enc;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import play.libs.Json;
import pojo.web.auth.ServerCacheData;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 
 * 利用隨機產生Key和IV ，進行AES加解密動作
 * 
 * Reference 
 *  1.http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
 *  2.http://stackoverflow.com/questions/15554296/simple-java-aes-encrypt-decrypt-example/22445878#22445878
 * </pre>
 */
public class AESEncrypter {
  
  public static String encrypt(String key, String initVector, String value) {
    try {
      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

      byte[] encrypted = cipher.doFinal(value.getBytes());
      String encResult = Base64.getEncoder().encodeToString(encrypted);
      System.out.println("encrypted string : " + encResult);

      return encResult;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String decrypt(String key, String initVector, String encrypted) {
    try {
      IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
      SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      
      byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
      String decResult = new String(original);
      
      System.out.println("decrypt result string : " + decResult);
      
      return decResult;
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public static String randomString(int len) {
    final String charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(charSet.charAt(rnd.nextInt(charSet.length())));
    }
    return sb.toString();
  }
  
  public static void main(String[] args) {
    
    String clientSessionId = java.util.UUID.randomUUID().toString();
    String key = randomString(16);  // 128 bit key
    String iv = randomString(16);   // 16 bytes IV
    String clientSessionUnsign = "{\"memberNo\":\"mem000000000001\",\"expiryDate\":\"20161020120000\"}";
    
    String clientSessionSign = encrypt(key, iv, clientSessionUnsign);
    String decodeString  = decrypt(key, iv, clientSessionSign);  
    
    System.out.println("------------------------------------------------");
    System.out.println("Target          => " + clientSessionUnsign);
    System.out.println("key             => " + key);
    System.out.println("iv              => " + iv);
    System.out.println("encryptString   => " + clientSessionSign);
    System.out.println("decodeString    => " + decodeString);
    System.out.println("------------------------------------------------");
    
    Map<String , String> sessionId = new HashMap<String, String>();
    sessionId.put("sessionId", clientSessionId);
    

    Map<String , String> sessionSign = new HashMap<String, String>();
    sessionSign.put("sessionSign", clientSessionSign);
    
    
    System.out.println("client SessionId      => " + Json.toJson(sessionId));
    System.out.println("client SessionSign    => " + Json.toJson(sessionSign));
    System.out.println("------------------------------------------------");
    
    ServerCacheData data = new ServerCacheData();
    data.setExpiryDate("20161020120000");
    data.setKey(key);
    data.setIv(iv);
    data.setSessionSign(clientSessionSign);
    Map<String , ServerCacheData> serverCacheData = new HashMap<String , ServerCacheData>();
    serverCacheData.put(clientSessionId, data);
    System.out.println("server Cache = " + Json.toJson(serverCacheData));
  }
  
    
}