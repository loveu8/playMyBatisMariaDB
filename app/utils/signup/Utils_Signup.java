package utils.signup;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;

import play.libs.Json;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.status.EmailStatus;
import pojo.web.signup.status.PasswordStatus;
import pojo.web.signup.status.UsernameStatus;
import pojo.web.signup.verific.VerificFormMessage;
import services.WebService;


public class Utils_Signup {
	

	public Map<String , VerificFormMessage>checkSingupRequest(SignupRequest reqeuest , boolean isRegEmail){
		
		Map<String , VerificFormMessage> info = new HashMap<String , VerificFormMessage>();
		
		String email 			= reqeuest.getEmail();
		String username			= reqeuest.getUsername();
		String password			= reqeuest.getPassword();
		String retypepassword   = reqeuest.getRetypePassword();

		info.put("email", checkEmail(email , isRegEmail));
		info.put("username", checkUsername(username));
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
	

	private VerificFormMessage checkUsername(String username) {
		VerificFormMessage message = new VerificFormMessage();
		
		message.setInputName("username");
		
		String usernameRegex = "^[a-zA-Z]{4,15}$";
		
		if("".equals(username) || username == null){
			message.setStatus(UsernameStatus.S1.status);
			message.setStatusDesc(UsernameStatus.S1.statusDesc);
		} else if(!username.matches(usernameRegex)){
			message.setStatus(UsernameStatus.S2.status);
			message.setStatusDesc(UsernameStatus.S2.statusDesc);
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
	
}
