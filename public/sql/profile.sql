-- 會員修改信箱
-- member_changeMail
-- member_no  會員編號 (索引鍵)
-- oldEmail	  舊信箱
-- newEmail	  新信箱
-- token	  驗證字串
-- checkCode  驗證碼
-- isUse      是否使用過
-- createDate 創立日期
-- expiryTime 逾期時間
CREATE TABLE `member_changeEmail` (
   `memberNo`   VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`oldEmail`   VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`newEmail` 	 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`token` 	 	 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`checkCode`  VARCHAR(6)	  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`isUse`      BOOL		  	  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`expiryDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	CONSTRAINT pk_memberNo PRIMARY KEY (memberNo),
	INDEX (token)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;
-- DROP tables member_changeEmail;

-- 找出修改密碼相關資訊
SELECT DATE_FORMAT(NOW() , '%Y%m%d%H%i%s') AS formatTime FROM DUAL;

SELECT
	(SELECT email FROM member_main WHERE memberNo = 'mem000000000003' )	AS newEmail ,
	(SELECT newEmail FROM member_changeEmail 
	 WHERE memberNo = 'mem000000000003' AND expiryTime > (DATE_FORMAT(NOW() , '%Y%m%d%H%i%s'))) AS unAuthEmail
FROM DUAL;



-- 會員明細
-- member_detail
-- memberNo	會員編號
-- headerPicLink 頭像網址
-- birthday 生日
-- nickname 暱稱
-- cellphone 手機號碼
-- createDate 建立日期
-- modifyDate 修改日期

CREATE TABLE `member_detail` (
    `memberNo`   	VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`nickname` 	 	VARCHAR(50)  NULL NULL COLLATE  'utf8_unicode_ci',
	`birthday` 	 	VARCHAR(50)  NULL NULL COLLATE 'utf8_unicode_ci',
	`cellphone` 	VARCHAR(50)  NULL NULL COLLATE 'utf8_unicode_ci',
	`headerPicLink` VARCHAR(500) NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`modifyDate` VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	 CONSTRAINT pk_memberNo PRIMARY KEY (memberNo),
	 INDEX (cellphone) 
)COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

-- DROP tables member_detail;

-- 會員明細修改紀錄表單
-- member_detail
-- memberNo	會員編號
-- headerPicLink 頭像網址
-- birthday 生日
-- nickname 暱稱
-- cellphone 手機號碼
-- createDate 建立日期

CREATE TABLE `member_detail_log` (
    `memberNo`   	VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`nickname` 	 	VARCHAR(50)   NULL NULL COLLATE  'utf8_unicode_ci',
	`birthday` 	 	VARCHAR(50)   NULL NULL COLLATE 'utf8_unicode_ci',
	`cellphone` 	VARCHAR(50)   NULL NULL COLLATE 'utf8_unicode_ci',
	`headerPicLink` VARCHAR(500)  NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- DROP tables member_detail_log;