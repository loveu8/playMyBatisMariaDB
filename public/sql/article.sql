-- 文章主要資料表
-- article_main
-- articleNo 	文章編號( 索引鍵)
-- member_no    會員編號 (index)
-- privacy      文章隱私 , public 公開 , protected 朋友才可以看 , private 自己可看
-- hidden		是否刪除(隱藏)
-- createDate   創立日期
-- modifyDate   修改時間
CREATE TABLE `article_main` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `privacy`	 VARCHAR(15)   NOT NULL DEFAULT 'public' COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
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
-- member_no    會員編號 (index)
-- privacy      文章隱私 , public 公開 , protected 朋友才可以看 , private 自己可看
-- hidden		是否刪除(隱藏)
-- createDate   創立日期
CREATE TABLE `article_main_log` (
   `articleNo`   VARCHAR(20)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `memberNo`    VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `privacy`	 VARCHAR(15)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `hidden`		 BOOL		   NOT NULL NULL COLLATE 'utf8_unicode_ci',
   `createDate`  VARCHAR(50)   NOT NULL NULL COLLATE 'utf8_unicode_ci',
	index (articleNo),
	index (memberNo)
)
COLLATE='utf8_unicode_ci'
ENGINE=InnoDB
;


















