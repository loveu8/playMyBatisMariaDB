--- Session 表單
-- sessionId   SessionId   (UUID)
-- sessionSign sessionSign (儲存相關Session資料)
-- key         AES Key     (AES 隨機16bit加密Key)
-- iv          AES IV      (AES 隨機16bit初始向量)
-- no             編號  	 (不可以重複，可以查尋到會員相關資料)
-- role           角色
-- expiryDate  Cookie and Session 逾期期限      
-- createDate  創立日期 	 (ex:20160921080102)
-- modifyDate  修改日期 	 (ex:20160921080102)
CREATE TABLE `user_session` (
	`sessionId`  	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `sessionSign` VARCHAR(250) NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `aseKey`      VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `aseIv`       VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `no`   			  VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `role`   			VARCHAR(15)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
  `expiryDate`  VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`createDate` 	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	`modifyDate`	VARCHAR(50)  NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_sessionId PRIMARY KEY (sessionId),
	UNIQUE KEY (no)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;

