-- 會員表單
-- memberNo 會員編號  (不可以重複，主鍵，當每個使用者註冊時，都會有唯得一個編號，可以查尋到會員相關資料)
-- email 使用者信箱   (不可以重複，不重覆鍵)
-- status 認證狀態    (初次註冊者，會給預設值1尚未認證)
-- password 密碼      
-- username 使用者名稱 (索引鍵。之後每個使用者註冊時，會檢查使用者名稱不可以重複的功能。)
-- createDate 創立日期 (ex:20160921080102)
-- modifyDate 修改日期 (ex:20160921080102)
CREATE TABLE `member_main` (
    `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`email`  	 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status` 	 VARCHAR(1)  	NOT NULL DEFAULT '1' NULL COLLATE 'utf8_unicode_ci',
	`password`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`username`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`modifyDate` VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_memberNo PRIMARY KEY (memberNo),
	UNIQUE KEY (email),
	INDEX (username)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

-- Drop tables member_main;
SELECT * FROM member_main;

-- 建立member流水號表單，用於取得最新的ㄧ筆auto key
CREATE TABLE member_main_seq
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
);
 
-- DROP tables member_main_seq;
 
-- 建立觸發器，在insert member_main之前
-- 會自動取得member_main_seq一組最新的 memberNo 
DELIMITER $$
CREATE TRIGGER tg_member_main_insert
BEFORE INSERT ON member_main
FOR EACH ROW
BEGIN
  INSERT INTO member_main_seq VALUES (NULL);
  SET NEW.memberNo = CONCAT('mem', LPAD(LAST_INSERT_ID(), 12, '0'));
END$$
DELIMITER ;

-- 會員修改紀錄表單
-- memberNo 會員編號  (索引鍵)
-- email 使用者信箱  
-- status 認證狀態   
-- password 密碼      
-- username 使用者名稱 
-- createDate 創立日期 (ex:20160921080102)
CREATE TABLE `member_main_log` (
  `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`email`  		 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status` 		 VARCHAR(1)  	NOT NULL DEFAULT '1' NULL COLLATE 'utf8_unicode_ci',
	`password`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`username`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

-- DROP tables member_main_log;
SELECT * FROM member_main_log;


-- 會員登入紀錄
-- memberNo 會員編號  (索引鍵)
-- email 使用者信箱  
-- status 登入狀態
-- device 登入的裝置
-- loginDate 登入日期
CREATE TABLE `member_login_log` (
  `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status` 		 VARCHAR(2)  	NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`ipAddress`  VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`device`  	 VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`loginDate`  VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	 index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;
-- DROP tables member_login_log;
SELECT * FROM member_login_log;

-- 會員Token表單
CREATE TABLE `member_token` (
	`tokenString`  VARCHAR(100) NOT NULL NULL COLLATE  'utf8_unicode_ci',
    `memberNo`     VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
    `type`         VARCHAR(20)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
    `sendDate`	   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`isUse`        BOOL			NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`createDate`   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`modifyDate`   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`expiryDate`   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	 CONSTRAINT pk_authString PRIMARY KEY (tokenString),
	 index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 撈出目前DB時間 +1 天
-- SELECT DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 DAY),'%Y%m%d%H%i%s');
-- 增加撈出目前DB時間
-- SELECT * , DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') as dbTime FROM member_main;
-- DROP tables member_token;
SELECT * FROM member_token;


-- 表單狀態對照表
CREATE TABLE `table_status_desc` (
  `tableName`  VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status`  	 VARCHAR(10) 	NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`statusDesc` VARCHAR(50)  DEFAULT NULL NULL COLLATE 'utf8_unicode_ci',
	 index (tableName)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

SELECT * FROM table_status_desc;

-- member_main 狀態對照

INSERT table_status_desc VALUES ('member_main','1','尚未認證');

INSERT table_status_desc VALUES ('member_main','2','已認證');

INSERT table_status_desc VALUES ('member_main','3','帳號停用');

-- member_login_log 狀態對照
INSERT table_status_desc VALUES ('member_login_log','1','加入會員成功');

INSERT table_status_desc VALUES ('member_login_log','2','登入成功');

INSERT table_status_desc VALUES ('member_login_log','3','登入失敗');


