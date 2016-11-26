package services;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pojo.web.Member;
import pojo.web.MemberToken;
import pojo.web.auth.UserSession;
import pojo.web.signup.request.SignupRequest;

public interface WebService {

  /** 註冊新會員 */
  public int signupNewMember(@Param("signupRequest") SignupRequest signupRequest);

  /** 檢查是否有該會員存在 */
  public boolean checkMemberByEmail(String email);
  
  /** 檢查是否有該會員使用者名稱是否存在 */
  public boolean checkMemberByUsername(String username);

  /** 用Email 尋找會員資料 */
  public Member findMemberByEmail(String email);
  
  /** 用會員編號 尋找會員資料 */
  public Member findMemberByMemberNo(String memberNo);
  
  /** 用Email與使用者名稱 尋找會員資料 */
  public Member findMemberByEmailAndUserName(@Param("email")String email , @Param("username")String username);
  
  /** 產生會員認證資料  */
  public int genTokenData(@Param("memberToken") Map<String , String> memberToken);
  
  /** 會員記錄檔  */
  public int genMemberLoginLog(@Param("memberLoginData") Map<String , String> memberLoginData);
  
  /** 驗證會員連結 */
  public MemberToken getMemberTokenData(@Param("token") String token , @Param("type") String type);
  
  /** 更新認證連結*/
  public int updateMemberToken(@Param("memberNo") String memberNo , @Param("type") String type);
  
  /** 更新會員資料*/
  public int updateMemberToAuthOk(String memberNo);
  
  /** 新增會員紀錄檔資料 */
  public int genMemberChangeLog(@Param("member") Member member);
  
  /** 新增會員Session資料 */
  public int genUserSession(@Param("userSession") UserSession userSession);

  /** 取得會員Session資料*/
  public UserSession getUserSession(String sessionId);

  /** 更新會員密碼 */
  public int updateMemberPassword(@Param("memberNo")String memberNo, @Param("password")String password);

  /** 根據會員編號 , 與密碼 , 確認該會員存在*/
  public boolean checkMemberByMemberNoAndPassword(@Param("no")String no, @Param("password")String oldPassword);
  
}
