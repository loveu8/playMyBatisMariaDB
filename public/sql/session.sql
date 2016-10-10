--- Session 表單
-- memberNo    會員編號  (不可以重複，可以查尋到會員相關資料)
-- sessionId   SessionId   (memberNo加密資料)
-- sessionSign sessionSign (儲存相關Session資料)
-- expiryDate  Cookie and Session 逾期期限      
-- createDate  創立日期 (ex:20160921080102)
-- modifyDate  修改日期 (ex:20160921080102)
CREATE TABLE `member_session` (
  `memberNo`   	VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`sessionId`  	VARCHAR(150) NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `sessionSign` VARCHAR(150) NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `expiryDate`  VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`createDate` 	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`modifyDate`	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_sessionId PRIMARY KEY (sessionId),
	UNIQUE KEY (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;