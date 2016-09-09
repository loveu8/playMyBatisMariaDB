package utils.signup;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import play.libs.Json;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.status.EmailStatus;
import pojo.web.signup.status.PasswordStatus;
import pojo.web.signup.status.UsernameStatus;
import pojo.web.signup.error.DefaultFormErrorMessage;
import services.WebService;

public class Utils_Signup {
	
	
	private WebService webService;
	
	
	@Inject
	public Utils_Signup( WebService webService){
		this.webService = webService;
	}
	
	
	public Map<String , DefaultFormErrorMessage>checkSingupRequest(SignupRequest reqeuest ){
		
		Map<String , DefaultFormErrorMessage> info = new HashMap<String , DefaultFormErrorMessage>();
		
		String email 			= reqeuest.getEmail();
		String username			= reqeuest.getUsername();
		String password			= reqeuest.getPassword();
		String retypepassword   = reqeuest.getRetypePassword();

		info.put("email", checkEmail(email));
		info.put("username", checkUsername(username));
		info.put("password", checkPassword(password , retypepassword));
		
		return info;
	}
	

	public DefaultFormErrorMessage checkEmail(String email){
		DefaultFormErrorMessage message = new DefaultFormErrorMessage();
		
		message.setInputName("email");
		
	    String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
		
		if("".equals(email) || email == null){
			message.setStatus(EmailStatus.S1.status);
			message.setStatusDesc(EmailStatus.S1.statusDesc);
		} else if(! email.matches(emailRegex)){
			message.setStatus(EmailStatus.S2.status);
			message.setStatusDesc(EmailStatus.S2.statusDesc);
		} else if(webService.checkMemberByEmail(email)){ 
			message.setStatus(EmailStatus.S3.status);
			message.setStatusDesc(EmailStatus.S3.statusDesc);
		} else {
			message.setStatus(EmailStatus.S200.status);
			message.setStatusDesc(EmailStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkEmail = " + Json.toJson(message));
		
		return message;
	}
	

	private DefaultFormErrorMessage checkUsername(String username) {
		DefaultFormErrorMessage message = new DefaultFormErrorMessage();
		
		message.setInputName("username");
		
		if("".equals(username) || username == null){
			message.setStatus(UsernameStatus.S1.status);
			message.setStatusDesc(UsernameStatus.S1.statusDesc);
		} else if(username.length() > 16 || username.length() < 4 ){
			message.setStatus(UsernameStatus.S2.status);
			message.setStatusDesc(UsernameStatus.S2.statusDesc);
		} else {
			message.setStatus(UsernameStatus.S200.status);
			message.setStatusDesc(UsernameStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkUsername = " + Json.toJson(message));
		
		return message;
	}
	
	
	private DefaultFormErrorMessage checkPassword(String password, String retypepassword) {
		DefaultFormErrorMessage message = new DefaultFormErrorMessage();
		
		message.setInputName("password");
		
		String passwordRegex = "[0-9a-zA-Z]{4,15}";
		
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
		} else if(password.length() < 4 || password.length() > 15){
			message.setStatus(PasswordStatus.S5.status);
			message.setStatusDesc(PasswordStatus.S5.statusDesc);
		} else {
			message.setStatus(PasswordStatus.S200.status);
			message.setStatusDesc(PasswordStatus.S200.statusDesc);
		}
		
		play.Logger.info("checkPassword = " + Json.toJson(message));
		
		return message;
	}
	
}
