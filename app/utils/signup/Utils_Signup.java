package utils.signup;

import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import play.libs.Json;
import pojo.web.Member;
import pojo.web.MemberTokenType;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.status.EmailStatus;
import pojo.web.signup.status.PasswordStatus;
import pojo.web.signup.status.UsernameStatus;
import pojo.web.signup.verific.VerificFormMessage;


public class Utils_Signup {
	

	public Map<String , VerificFormMessage>checkSingupRequest(SignupRequest reqeuest , boolean isRegEmail , boolean isUsedUsername){
		
		Map<String , VerificFormMessage> info = new HashMap<String , VerificFormMessage>();
		
		String email 			= reqeuest.getEmail();
		String username			= reqeuest.getUsername();
		String password			= reqeuest.getPassword();
		String retypepassword   = reqeuest.getRetypePassword();

		info.put("email", checkEmail(email , isRegEmail));
		info.put("username", checkUsername(username , isUsedUsername));
		info.put("password", checkPassword(password , retypepassword));
		
		return info;
	}
	

	public VerificFormMessage checkEmail(String email , boolean isRegEmail){
		VerificFormMessage message = new VerificFormMessage();
		
		message.setInputName("email");
		
	    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	    
		if("".equals(email) || email == null){
			message.setStatus(EmailStatus.S1.status);
			message.setStatusDesc(EmailStatus.S1.statusDesc);
		} else if(! email.matches(emailRegex)){
			message.setStatus(EmailStatus.S2.status);
			message.setStatusDesc(EmailStatus.S2.statusDesc);
		} else if(isRegEmail){ 
			message.setStatus(EmailStatus.S3.status);
			message.setStatusDesc(EmailStatus.S3.statusDesc);
		} else {
			message.setStatus(EmailStatus.S200.status);
			message.setStatusDesc(EmailStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkEmail = " + Json.toJson(message));
		
		return message;
	}
	

	private VerificFormMessage checkUsername(String username , boolean isUsedUsername) {
		VerificFormMessage message = new VerificFormMessage();
		
		message.setInputName("username");
		
		String usernameRegex = "^[a-zA-Z]{4,15}$";
		
		if("".equals(username) || username == null){
			message.setStatus(UsernameStatus.S1.status);
			message.setStatusDesc(UsernameStatus.S1.statusDesc);
		} else if(!username.matches(usernameRegex)){
			message.setStatus(UsernameStatus.S2.status);
			message.setStatusDesc(UsernameStatus.S2.statusDesc);
		} else if(isUsedUsername){
          message.setStatus(UsernameStatus.S3.status);
          message.setStatusDesc(UsernameStatus.S3.statusDesc);
		} else {
			message.setStatus(UsernameStatus.S200.status);
			message.setStatusDesc(UsernameStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkUsername = " + Json.toJson(message));
		
		return message;
	}
	
	
	private VerificFormMessage checkPassword(String password, String retypepassword) {
		VerificFormMessage message = new VerificFormMessage();
		
		message.setInputName("password");
		
		String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,15}$";
		
		if("".equals(password) || password == null){
			message.setStatus(PasswordStatus.S1.status);
			message.setStatusDesc(PasswordStatus.S1.statusDesc);
		} else if("".equals(retypepassword) || retypepassword == null){
			message.setStatus(PasswordStatus.S2.status);
			message.setStatusDesc(PasswordStatus.S2.statusDesc);
		} else if(!retypepassword.equals(password)){
			message.setStatus(PasswordStatus.S3.status);
			message.setStatusDesc(PasswordStatus.S3.statusDesc);
		} else if(!password.matches(passwordRegex)){
			message.setStatus(PasswordStatus.S4.status);
			message.setStatusDesc(PasswordStatus.S4.statusDesc);
		} else {
			message.setStatus(PasswordStatus.S200.status);
			message.setStatusDesc(PasswordStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkPassword = " + Json.toJson(message));
		
		return message;
	}
	
	
	/**
	 * 產生註冊sha-256認證字串
	 * @param email 使用者信箱
	 * @return SHA256 String
	 */
	public String genSignupAuthString(String email){

      Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
      String time = formatter.format(new Date());
      String text = email + time;

      return this.genSHA256String(text);
	}
	
	/**
     * 產生忘記密碼sha-256認證字串
     * @param Member member 使用者資料
     * @return SHA256 String
     */
    public String genForgotPasswordTokenString(Member member){

      Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
      String time = formatter.format(new Date());
      String text = time + member.getMemberNo() + member.getEmail();

      return this.genSHA256String(text);
    }
	
	
	/**
	 * 傳入主要的需要認證字串，進行Sha256加密
	 * @param text
	 * @return token
	 */
	private String genSHA256String(String text){
	  String token = "";
	  try{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes("UTF-8")); 
        byte[] digest = md.digest();
        // %064x 意思是，產生64個字串長的字串
        token = String.format("%064x", new java.math.BigInteger(1, digest));
	  } catch (Exception e) {
        e.printStackTrace();
      }
	  return token;
	}
	
	
	/**
     * 產生會員記錄檔
     * @param memberNo 
     * @param device 
     * @param ipAddress 
     * @param status 
     * @return Map<String , String>
     */
	public Map<String , String> genMemberLoginData(String memberNo , String device , String ipAddress , String status){
	  Map<String , String> memberLoginLog = new HashMap<String , String>();
	  memberLoginLog.put("memberNo", memberNo);
	  memberLoginLog.put("device",device);
	  memberLoginLog.put("ipAddress",ipAddress);
	  memberLoginLog.put("status",status);
	  return memberLoginLog;
	}
	
}
