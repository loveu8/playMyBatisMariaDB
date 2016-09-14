-- 會員表單

CREATE TABLE `member_main` (
  `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`email`  		 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status` 		 VARCHAR(1)  	NOT NULL DEFAULT '1' NULL COLLATE 'utf8_unicode_ci',
	`password`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`username`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`modifyDate` VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_memberNo PRIMARY KEY (memberNo),
	UNIQUE KEY (email)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


SELECT * FROM member_main;

-- 建立member流水號表單，用於取得最新的ㄧ筆auto key

CREATE TABLE member_main_seq
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
);
 
-- DROP tables member_main_seq;
 
-- 建立觸發器，在insert member_main之前
-- 會自動取得member_main_seq一組最新的 memberNo 
-- 在insert到表單member_login_log
DELIMITER $$
CREATE TRIGGER tg_member_main_insert
BEFORE INSERT ON member_main
FOR EACH ROW
BEGIN
  INSERT INTO member_main_seq VALUES (NULL);
  SET NEW.memberNo = CONCAT('mem', LPAD(LAST_INSERT_ID(), 12, '0'));
END$$
DELIMITER ;

insert member_main values(null,'playStar@gmail.com','2','Abc123','playStar','20160906083500','20160906083500');

-- 刪除預設會員資料
-- DELETE FROM member_main WHERE username = 'Star';

-- 會員修改紀錄表單

CREATE TABLE `member_main_log` (
  `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`email`  		 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`status` 		 VARCHAR(1)  	NOT NULL DEFAULT '1' NULL COLLATE 'utf8_unicode_ci',
	`password`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`username`   VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	CONSTRAINT pk_memberNo PRIMARY KEY (memberNo),
	UNIQUE KEY (email)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

SELECT * FROM member_main_log;

-- 會員登入紀錄

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

SELECT * FROM member_login_log;

-- 認證表單

CREATE TABLE `member_auth` (
	`authString`   VARCHAR(100) NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `memberNo`     VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `sendDate`		 VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`isUse`        BOOL				  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`lastAuthDate` VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`createDate`   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`modifyDate`   VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	 CONSTRAINT pk_authString PRIMARY KEY (authString),
	 index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

SELECT * FROM member_auth;

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


