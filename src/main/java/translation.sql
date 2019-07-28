
/* Drop Tables */

DROP TABLE translation_his CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE translation_his
(
	th_num number NOT NULL UNIQUE,
	th_source char(5) NOT NULL,
	th_target char(5) NOT NULL,
	th_before_text varchar2(4000) NOT NULL,
	-- 성공시 1 실패시 0
	th_result number(1) NOT NULL,
	th_after_text varchar2(4000),
	th_error_code char(6),
	PRIMARY KEY (th_num)
);



/* Comments */

COMMENT ON COLUMN translation_his.th_result IS '성공시 1 실패시 0';



