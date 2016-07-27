package pojo.login;

public class User {
	
	private String account;
	
	private String password;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	// 簡單驗證帳號密碼
    public String validate(String dbAccount , String dbPassword) {
        if (nullOrEmptyCheck(account, password)) {
            return "請輸入帳號與密碼";
        }
        if(!accountCheck(dbAccount ,dbPassword , account , password)){
        	return "請確認帳號與密碼是否輸入正確";
        }
        // 通過檢查，回傳pass
        return "pass";
    }
	
    
	private boolean nullOrEmptyCheck(String account2, String password2) {
		return account2 == null || password2 == null || "".equals(account2) || "".equals(password2);
	}
	
	private boolean accountCheck(String dbAccount , String dbPassword , String userAccount , String userPassword){
		return dbAccount != null && dbPassword != null && "".equals(dbAccount) && "".equals(dbPassword)
			   && dbAccount.equals(userAccount) && dbPassword.equals(userPassword);
	}
	
}
