package pojo.login;

public class User {
	
	private String account;
	
	private String password;
	
    public String validate() {
        if (authenticate(account, password)) {
            return "錯誤的帳號或密碼";
        }
        return null;
    }
	
	private boolean authenticate(String account2, String password2) {
		return account2 == null || password2 == null || "".equals(account2) || "".equals(password2);
	}

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
	
}
