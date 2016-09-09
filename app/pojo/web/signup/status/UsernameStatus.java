package pojo.web.signup.status;

public enum UsernameStatus {
	
	
	S1("1","請輸入使用者名稱。") , 
	S2("2","使用者名稱，需要介於4個字~15個之間。") , 
	S200("200","使用者名稱可以使用。")
	;

	
	UsernameStatus(String status , String statusDesc){
		this.status = status;
		this.statusDesc = statusDesc;
	}
	
	
	public final String status;		// 狀態代碼
	
	public final String statusDesc;	// 狀態說明
	
}
