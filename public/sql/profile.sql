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
	`token` 	 VARCHAR(150) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`checkCode`  VARCHAR(6)	  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`isUse`      BOOL		  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`createDate` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`expiryTime` VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	CONSTRAINT pk_memberNo PRIMARY KEY (memberNo)
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
	 WHERE memberNo = '' AND expiryTime > (DATE_FORMAT(NOW() , '%Y%m%d%H%i%s'))) AS unAuthEmail
FROM DUAL;

-- 會員明細
-- member_detail
-- memberNo	會員編號
-- headerPicLink 大頭像網址
-- birthday 生日
-- nickName 綽號
-- telphone 手機號碼

CREATE TABLE `member_detail` (
    `memberNo`   	VARCHAR(15)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`nickName` 	 	VARCHAR(50) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`birthday` 	 	VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`telphone` 	 	VARCHAR(50)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
	`headerPicLink` VARCHAR(500) NOT NULL NULL COLLATE 'utf8_unicode_ci',
	 CONSTRAINT pk_memberNo PRIMARY KEY (memberNo)
)COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

-- DROP tables member_detail;