-- 文章主要資料表
-- article_main
-- articleNo 	文章編號( 索引鍵)
-- memberNo     會員編號 (index)
-- content      文章內容
-- privacy      文章隱私 , public 公開 , protected 朋友才可以看 , private 自己可看
-- hidden		是否刪除(隱藏)
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_main` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE  'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE  'utf8_unicode_ci',
   `privacy`	 VARCHAR(15)   NOT NULL DEFAULT 'public' COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL NULL COLLATE  'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE  'utf8_unicode_ci',
   `modifyDate`  VARCHAR(50)   NOT NULL NULL COLLATE  'utf8_unicode_ci',
	CONSTRAINT pk_articleNo PRIMARY KEY (articleNo),
	INDEX (member_no)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 建立article流水號表單，用於取得最新的ㄧ筆auto key
CREATE TABLE article_main_seq
(
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
);


-- 建立觸發器，在insert article_main之前
-- 會自動取得article_main_seq 一組最新的 articleNo 
DELIMITER $$
CREATE TRIGGER tg_article_main_insert
BEFORE INSERT ON article_main
FOR EACH ROW
BEGIN
  INSERT INTO article_main_seq VALUES (NULL);
  SET NEW.articleNo = CONCAT('art', LPAD(LAST_INSERT_ID(), 17, '0'));
END$$
DELIMITER ;


-- 文章主要資料表紀錄表單
-- articleNo 	文章編號( 索引鍵)
-- memberNo     會員編號 (index)
-- content      文章內容
-- privacy      文章隱私 , public 公開 , protected 朋友才可以看 , private 自己可看
-- hidden		是否刪除(隱藏)
-- createDate   創立日期
CREATE TABLE `article_main_log` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `content`	 VARCHAR(1000) NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `privacy`	 VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
	index (articleNo),
	index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章內容表單
-- articleNo 	文章編號( 索引鍵)
-- content      文章內容
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_content` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `content`	 VARCHAR(1000) NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `modifyDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
	CONSTRAINT pk_articleNo PRIMARY KEY (articleNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章內容表單記錄表
-- articleNo 	文章編號( 索引鍵)
-- content      文章內容
-- createDate   創立日期
-- createDate   創立日期
CREATE TABLE `article_content_log` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `content`	 VARCHAR(1000) NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
	INDEX (articleNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章回覆資料表
-- article_reply
-- articleNo 	文章編號	   (索引鍵)
-- memberNo    回覆會員編號 (index)
-- seqno        回覆序號
-- hidden       隱藏文章
-- content      回覆內容
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_reply` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',   
   `seqno`       VARCHAR(100)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL DEFAULT false COLLATE 'utf8_unicode_ci',
   `content`	 VARCHAR(1000) NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `modifyDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   CONSTRAINT pk_articleNo PRIMARY KEY (articleNo , seqno),
	index (articleNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章回覆內容資料紀錄表
-- article_reply_log
-- articleNo 	文章編號	   (索引鍵)
-- memberNo     回覆會員編號 (index)
-- seqno        回覆序號
-- hidden       隱藏文章
-- content      回覆內容
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_reply_log` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',   
   `seqno`       VARCHAR(100)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL DEFAULT false COLLATE 'utf8_unicode_ci',
   `content`	 VARCHAR(1000) NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
	index (articleNo , seqno)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章其他詳細資料表
-- article_detail
-- articleNo 	文章編號	   (索引鍵)
-- memberNo     回覆會員編號 (index)
-- like         是否喜歡
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_detail` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',   
   `like`		 BOOL		   NOT NULL DEFAULT false COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `modifyDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   CONSTRAINT pk_articleNo PRIMARY KEY (articleNo , memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


-- 文章分享資料表
-- article_share
-- articleNo 	分享的文章編號 (索引鍵)
-- memberNo     會員編號      (index)
-- seqno        分享序號
-- hidden       隱藏文章
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_share` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',  
   `seqno`       VARCHAR(100)  NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `modifyDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   CONSTRAINT pk_share PRIMARY KEY (articleNo , memberNo , seqno),
	index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;