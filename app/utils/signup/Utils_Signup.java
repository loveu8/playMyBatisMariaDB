package utils.signup;

import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import play.libs.Json;
import pojo.web.Member;
import pojo.web.email.MemberSendChangeEmail;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.status.CellphoneStatus;
import pojo.web.signup.status.EmailStatus;
import pojo.web.signup.status.HeaderPicLinkStatus;
import pojo.web.signup.status.NicknameStatus;
import pojo.web.signup.status.PasswordStatus;
import pojo.web.signup.status.UsernameStatus;
import pojo.web.signup.verific.VerificFormMessage;
import utils.enc.AESEncrypter;


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
	

	public VerificFormMessage checkUsername(String username , boolean isUsedUsername) {
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
	
	
	public VerificFormMessage checkPassword(String password, String retypepassword) {
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
	
	
	public VerificFormMessage checkHeaderPicLink(String headerPicLink , boolean isImg){
	  
      VerificFormMessage message = new VerificFormMessage();
      
      message.setInputName("headerPicLink");
      
      String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
      
      if(headerPicLink == null || "".equals(headerPicLink)){
        message.setStatus(HeaderPicLinkStatus.S201.status);
        message.setStatusDesc(HeaderPicLinkStatus.S201.statusDesc);
      } else if (!headerPicLink.matches(urlRegex)){
        message.setStatus(HeaderPicLinkStatus.S1.status);
        message.setStatusDesc(HeaderPicLinkStatus.S1.statusDesc);
      } else if(!isImg){
        message.setStatus(HeaderPicLinkStatus.S2.status);
        message.setStatusDesc(HeaderPicLinkStatus.S2.statusDesc);
      } else {
        message.setStatus(HeaderPicLinkStatus.S200.status);
        message.setStatusDesc(HeaderPicLinkStatus.S200.statusDesc);
      }
      
      play.Logger.info("checkHeaderPicLink = " + Json.toJson(message));
      
      return message;
	}
	
	
    public VerificFormMessage checkNickname(String nickname) {
      VerificFormMessage message = new VerificFormMessage();
      
      message.setInputName("nickname");
      
      String nicknameRegex = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】『；：」「』。，、？\\\\]+.*";
      
      if(nickname == null || "".equals(nickname)) {
          message.setStatus(NicknameStatus.S201.status);
          message.setStatusDesc(NicknameStatus.S201.statusDesc);
      } else if(nickname.length() < 1 || nickname.length() > 15){
          message.setStatus(NicknameStatus.S1.status);
          message.setStatusDesc(NicknameStatus.S1.statusDesc);
      } else if(nickname.matches(nicknameRegex)){
          message.setStatus(NicknameStatus.S2.status);
          message.setStatusDesc(NicknameStatus.S2.statusDesc);
      } else {
          message.setStatus(NicknameStatus.S200.status);
          message.setStatusDesc(NicknameStatus.S200.statusDesc);
      }
      
      play.Logger.info("checkNickname = " + Json.toJson(message));
      
      return message;
    }
	
    
    public VerificFormMessage checkCellphone(String cellphone, boolean isUsedCellphone) {
      
      VerificFormMessage message = new VerificFormMessage();
      
      message.setInputName("cellphone");
      
      String cellphoneRegex = "[0-9]{4}[0-9]{6}";
      
      if(cellphone == null || "".equals(cellphone)) {
          message.setStatus(CellphoneStatus.S201.status);
          message.setStatusDesc(CellphoneStatus.S201.statusDesc);
      } else if(!cellphone.matches(cellphoneRegex)){
          message.setStatus(CellphoneStatus.S1.status);
          message.setStatusDesc(CellphoneStatus.S1.statusDesc);
      } else if (isUsedCellphone){
          message.setStatus(CellphoneStatus.S2.status);
          message.setStatusDesc(CellphoneStatus.S2.statusDesc);
      } else {
          message.setStatus(CellphoneStatus.S200.status);
          message.setStatusDesc(CellphoneStatus.S200.statusDesc);
      }
      
      play.Logger.info("checkNickname = " + Json.toJson(message));
      
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
	
	
	/**
	 * 產生重新設定Email資料
	 * @param memberNo
	 * @param oldEmail
	 * @param newEmail
	 */
	public MemberSendChangeEmail genMemberSendChangeEmail(String memberNo , String oldEmail , String newEmail){
	  
	  MemberSendChangeEmail data = new MemberSendChangeEmail();
	  long limitTime =  (60 * 60 * 24) * 1 * 1000;
	  
	  try{

        String token = java.util.UUID.randomUUID().toString();
        Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String expiryDate = formatter.format(new Date(new Date().getTime() + limitTime ));
        String createDate = formatter.format(new Date());
        
        data.setMemberNo(memberNo);
        data.setOldEmail(oldEmail);
        data.setNewEmail(newEmail);
        data.setToken(token);
        data.setUse(false);
        data.setCheckCode(new AESEncrypter().randomString(6));
        data.setCreateDate(createDate);
        data.setExpiryDate(expiryDate);
        
	  } catch (Exception e){
	    e.printStackTrace();
	    data = null;
	  }
	  return data;
	}

	
}
