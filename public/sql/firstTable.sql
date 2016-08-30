-- 1.建立表單
CREATE TABLE firstTable( seq int ,Message varchar(255));

-- 2.查詢
SELECT * FROM firstTable;

-- 3.新增
INSERT firstTable VALUES ('1','Hello World');
INSERT firstTable VALUES ('2','Play World');

-- 4.新增後查詢
SELECT * FROM firstTable;

-- 5.刪除
DELETE FROM firstTable WHERE seq = '1';

-- 6.刪除後查詢
SELECT * FROM firstTable;

-- 7.更新
UPDATE firstTable SET seq = '1' WHERE seq = '2';

-- 7.更新後查詢
SELECT * FROM firstTable;

-- 8.丟棄
DROP TABLE firstTable;