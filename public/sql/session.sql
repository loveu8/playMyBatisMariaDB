--- Session 表單
-- sessionId   SessionId   (UUID)
-- sessionSign sessionSign (儲存相關Session資料)
-- key         AES Key     (AES 隨機16bit加密Key)
-- iv          AES IV      (AES 隨機16bit初始向量)
-- memberNo    會員編號  	 (不可以重複，可以查尋到會員相關資料)
-- expiryDate  Cookie and Session 逾期期限      
-- createDate  創立日期 	 (ex:20160921080102)
-- modifyDate  修改日期 	 (ex:20160921080102)
CREATE TABLE `member_session` (
	`sessionId`  	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `sessionSign` VARCHAR(250) NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `aseKey`      VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `aseIv`       VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `memberNo`   	VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `expiryDate`  VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`createDate` 	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`modifyDate`	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_sessionId PRIMARY KEY (sessionId),
	UNIQUE KEY (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

