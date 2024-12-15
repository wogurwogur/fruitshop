---- **** fruitshop ?„¸ë¯¸ì›¹?”„ë¡œì ?Š¸ ?—?„œ ?‘?—…?•œ ê²? **** ----

-- ?˜¤?¼?´ ê³„ì • ?ƒ?„±?„ ?œ„?•´?„œ?Š” SYS ?˜?Š” SYSTEM ?œ¼ë¡? ?—°ê²°í•˜?—¬ ?‘?—…?„ ?•´?•¼ ?•©?‹ˆ?‹¤. [SYS ?‹œ?‘] --
show user;
-- USER?´(ê°?) "SYS"?…?‹ˆ?‹¤.

-- ?˜¤?¼?´ ê³„ì • ?ƒ?„±?‹œ ê³„ì •ëª? ?•?— c## ë¶™ì´ì§? ?•Šê³? ?ƒ?„±?•˜?„ë¡? ?•˜ê² ìŠµ?‹ˆ?‹¤.
alter session set "_ORACLE_SCRIPT"=true;
-- Session?´(ê°?) ë³?ê²½ë˜?—ˆ?Šµ?‹ˆ?‹¤.

-- ?˜¤?¼?´ ê³„ì •ëª…ì? MYMVC_USER ?´ê³? ?•”?˜¸?Š” gclass ?¸ ?‚¬?š©? ê³„ì •?„ ?ƒ?„±?•©?‹ˆ?‹¤.
create user fruitshop_user identified by gclass default tablespace users;
-- User MYMVC_USER?´(ê°?) ?ƒ?„±?˜?—ˆ?Šµ?‹ˆ?‹¤.

-- ?œ„?—?„œ ?ƒ?„±?˜?–´ì§? MYMVC_USER ?´?¼?Š” ?˜¤?¼?´ ?¼ë°˜ì‚¬?š©? ê³„ì •?—ê²? ?˜¤?¼?´ ?„œë²„ì— ? ‘?†?´ ?˜?–´ì§?ê³?,
-- ?…Œ?´ë¸? ?ƒ?„± ?“±?“±?„ ?•  ?ˆ˜ ?ˆ?„ë¡? ?—¬?Ÿ¬ê°?ì§? ê¶Œí•œ?„ ë¶??—¬?•´ì£¼ê² ?Šµ?‹ˆ?‹¤.
grant connect, resource, create view, unlimited tablespace to fruitshop_user;
-- Grant?„(ë¥?) ?„±ê³µí–ˆ?Šµ?‹ˆ?‹¤.

-----------------------------------------------------------------------


show user;


/* ë©”ì¸?˜?´ì§? ë°°ë„ˆ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_main_page
(
  imgno         NUMBER          NOT NULL
, imgname       NVARCHAR2(20)   NOT NULL
, imgfilename   NVARCHAR2(30)   NOT NULL

, CONSTRAINT PK_tbl_main_page_imgno PRIMARY KEY (imgno)
);

CREATE SEQUENCE seq_main_image
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;

INSERT INTO tbl_main_page(imgno, imgname, imgfilename) VALUES (seq_main_image.NEXTVAL, 'pick', 'main_pick.png');
INSERT INTO tbl_main_page(imgno, imgname, imgfilename) VALUES (seq_main_image.NEXTVAL, 'bestseller', 'main_best.png');
INSERT INTO tbl_main_page(imgno, imgname, imgfilename) VALUES (seq_main_image.NEXTVAL, 'delivery', 'main_delivery.png');

COMMIT;
/* ë©”ì¸?˜?´ì§? ë°°ë„ˆ ?…Œ?´ë¸? ? */



/* ?šŒ?› ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_member
( user_no           NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, userid            VARCHAR2(20)    NOT NULL        /* ?šŒ?›?•„?´?”” */
, passwd            VARCHAR2(200)   NOT NULL        /* ?šŒ?›ë¹„ë?ë²ˆí˜¸ */
, name              NVARCHAR2(10)   NOT NULL        /* ?šŒ?›?´ë¦? */
, birthday          VARCHAR2(20)    NOT NULL        /* ?ƒ?…„?›”?¼ */
, email             VARCHAR2(200)   NOT NULL        /* ?´ë©”ì¼ */
, tel               VARCHAR2(15)                    /* ?—°?½ì²? */
, postcode          VARCHAR2(5)                     /* ?š°?¸ë²ˆí˜¸ */
, address           VARCHAR2(200)                   /* ì£¼ì†Œ */
, detailaddress     VARCHAR2(200)                   /* ?ƒ?„¸ì£¼ì†Œ */
, extraadress       VARCHAR2(200)                   /* ì°¸ê³ ?‚¬?•­ */
, gender            NVARCHAR2(2)                    /* ?„±ë³?(?‚¨/?—¬) */
, point             NUMBER    DEFAULT 0             /* ? ë¦½ê¸ˆ */
, registerday       DATE DEFAULT SYSDATE NOT NULL   /* ê°??…?¼ */
, lastpwdchangedate DATE DEFAULT SYSDATE NOT NULL   /* ë§ˆì?ë§? ë¹„ë?ë²ˆí˜¸ ë³?ê²½ì¼ */
, idle              NUMBER(1) DEFAULT 1  NOT NULL   /* ?œ´ë©´ìƒ?ƒœ(0:?œ´ë©´ì¤‘, 1:?™œ?™ì¤?) */
, status            NUMBER(1) DEFAULT 1  NOT NULL   /* ê°??…?ƒ?ƒœ(0:?ƒˆ?‡´, 1:ê°??…ì¤?) */
, role              NUMBER(1) DEFAULT 1  NOT NULL   /* ?œ ??ê¶Œí•œ(1:?¼ë°˜ìœ ??, 2:ê´?ë¦¬ì) */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_member_user_no PRIMARY KEY (user_no)
, CONSTRAINT UQ_tbl_member_userid  UNIQUE (userid)
, CONSTRAINT UQ_tbl_member_email   UNIQUE (email)
, CONSTRAINT CK_tbl_member_idle    CHECK (idle IN (0, 1))
, CONSTRAINT CK_tbl_member_status  CHECK (status IN (0, 1))
, CONSTRAINT CK_tbl_member_role    CHECK (role IN (1, 2))
);
-- Table TBL_MEMBER created.


COMMENT ON TABLE tbl_member IS '?šŒ?› ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_member.user_no IS '?šŒ?›ë²ˆí˜¸';

COMMENT ON COLUMN tbl_member.userid IS '?šŒ?›?•„?´?””';

COMMENT ON COLUMN tbl_member.passwd IS '?šŒ?›ë¹„ë?ë²ˆí˜¸';

COMMENT ON COLUMN tbl_member.name IS '?šŒ?›?´ë¦?';

COMMENT ON COLUMN tbl_member.birthday IS '?ƒ?…„?›”?¼';

COMMENT ON COLUMN tbl_member.email IS '?´ë©”ì¼';

COMMENT ON COLUMN tbl_member.tel IS '?—°?½ì²?';

COMMENT ON COLUMN tbl_member.postcode IS '?š°?¸ë²ˆí˜¸';

COMMENT ON COLUMN tbl_member.address IS 'ì£¼ì†Œ';

COMMENT ON COLUMN tbl_member.detailaddress IS '?ƒ?„¸ì£¼ì†Œ';

COMMENT ON COLUMN tbl_member.extraadress IS 'ì°¸ê³ ?‚¬?•­';

COMMENT ON COLUMN tbl_member.gender IS '?„±ë³?(?‚¨/?—¬)';

COMMENT ON COLUMN tbl_member.point IS '? ë¦½ê¸ˆ';

COMMENT ON COLUMN tbl_member.registerday IS 'ê°??…?¼';

COMMENT ON COLUMN tbl_member.lastpwdchangedate IS 'ë§ˆì?ë§? ë¹„ë?ë²ˆí˜¸ ë³?ê²½ì¼';

COMMENT ON COLUMN tbl_member.idle IS '?œ´ë©´ìƒ?ƒœ(0:?œ´ë©´ì¤‘, 1:?™œ?™ì¤?)';

COMMENT ON COLUMN tbl_member.status IS 'ê°??…?ƒ?ƒœ(0:?ƒˆ?‡´, 1:ê°??…ì¤?)';

COMMENT ON COLUMN tbl_member.role IS '?œ ??ê¶Œí•œ(1:?¼ë°˜ìœ ??, 2:ê´?ë¦¬ì)';


CREATE SEQUENCE user_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence USER_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_MEMBER';
/* ?šŒ?› ?…Œ?´ë¸? ? */








/* ë¡œê·¸?¸ ê¸°ë¡ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_loginhistory
( loghis_no         NUMBER          NOT NULL        /* ë¡œê·¸?¸ê¸°ë¡ ë²ˆí˜¸ */
, fk_user_no        NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, login_date        DATE DEFAULT SYSDATE NOT NULL   /* ë¡œê·¸?¸?¼? */
, clinetip          VARCHAR2(20)    NOT NULL        /* ? ‘?†? IPì£¼ì†Œ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_history_loghis_no   PRIMARY KEY (loghis_no)
, CONSTRAINT FK_tbl_history_fk_user_no  FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
);
-- Table TBL_LOGINHISTORY created.


COMMENT ON TABLE tbl_loginhistory IS 'ë¡œê·¸?¸ ê¸°ë¡?´ ?‹´ê¸°ëŠ” ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_loginhistory.loghis_no IS 'ë¡œê·¸?¸ ê¸°ë¡ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_loginhistory.fk_user_no IS '?šŒ?›ë²ˆí˜¸';

COMMENT ON COLUMN tbl_loginhistory.login_date IS 'ë¡œê·¸?¸ ?¼?';

COMMENT ON COLUMN tbl_loginhistory.clinetip IS '? ‘?†? IPì£¼ì†Œ';



CREATE SEQUENCE login_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence USER_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_LOGINHISTORY';
/* ë¡œê·¸?¸ ê¸°ë¡ ?…Œ?´ë¸? ? */








/* ê³„ì ˆ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_seasons
( season_no         NUMBER          NOT NULL        /* ê³„ì ˆë²ˆí˜¸ */
, season_name       NVARCHAR2(5)    NOT NULL        /* ê³„ì ˆëª?(ë´?/?—¬ë¦?/ê°??„/ê²¨ìš¸) */
, season_image      VARCHAR2(50)    NOT NULL        /* ê³„ì ˆ???‘œ?´ë¯¸ì? */

, CONSTRAINT PK_tbl_seasons_season_no   PRIMARY KEY (season_no)
);
-- Table TBL_SEASONS created.


COMMENT ON TABLE tbl_seasons IS 'ê³„ì ˆ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_seasons.season_no IS 'ê³„ì ˆë²ˆí˜¸';

COMMENT ON COLUMN tbl_seasons.season_name IS 'ê³„ì ˆëª?(ë´?/?—¬ë¦?/ê°??„/ê²¨ìš¸)';

COMMENT ON COLUMN tbl_seasons.season_image IS 'ê³„ì ˆ???‘œ?´ë¯¸ì?';


--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (1, 'ë´?', 'sping.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (2, '?—¬ë¦?', 'summer.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (3, 'ê°??„', 'autumn.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (4, 'ê²¨ìš¸', 'winter.png');

--COMMIT;


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_SEASONS';
/* ê³„ì ˆ ?…Œ?´ë¸? ? */








/* ?ƒ?’ˆ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_products
( prod_no           NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */
, prod_name         NVARCHAR2(20)   NOT NULL        /* ?ƒ?’ˆëª? */
, prod_cost         NUMBER          NOT NULL        /* ?ƒ?’ˆ?›ê°? */
, prod_price        NUMBER          NOT NULL        /* ?ƒ?’ˆê°?ê²? */
, prod_thumnail     VARCHAR2(100)                   /* ?ƒ?’ˆ?¸?„¤?¼ */
, prod_descript     VARCHAR2(100)                   /* ?ƒ?’ˆ ?„¤ëª? ?ƒ?„¸?‚´?š© */
, prod_inventory    NUMBER(1)       DEFAULT 0       /* ?ƒ?’ˆ?¬ê³ ëŸ‰ */
, fk_season_no      NUMBER          NOT NULL        /* ê³„ì ˆë²ˆí˜¸ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_products_prod_no        PRIMARY KEY (prod_no)
, CONSTRAINT FK_tbl_products_fk_season_no   FOREIGN KEY (fk_season_no) REFERENCES tbl_seasons(season_no)
);
-- Table TBL_PRODUCTS created.


COMMENT ON TABLE tbl_products IS '?ƒ?’ˆ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_products.prod_no IS '?ƒ?’ˆë²ˆí˜¸';

COMMENT ON COLUMN tbl_products.prod_name IS '?ƒ?’ˆëª?';

COMMENT ON COLUMN tbl_products.prod_cost IS '?ƒ?’ˆ?›ê°?';

COMMENT ON COLUMN tbl_products.prod_price IS '?ƒ?’ˆê°?ê²?';

COMMENT ON COLUMN tbl_products.prod_thumnail IS '?ƒ?’ˆ?¸?„¤?¼';

COMMENT ON COLUMN tbl_products.prod_descript IS '?ƒ?’ˆ ?„¤ëª? ?ƒ?„¸?‚´?š©';

COMMENT ON COLUMN tbl_products.prod_inventory IS '?ƒ?’ˆ?¬ê³ ëŸ‰';

COMMENT ON COLUMN tbl_products.fk_season_no IS 'ê³„ì ˆë²ˆí˜¸';



CREATE SEQUENCE prod_no
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence PROD_NO created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_PRODUCTS';
/* ?ƒ?’ˆ ?…Œ?´ë¸? ? */








/* ë°°ì†¡ì§? ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_ship
( ship_no            NUMBER          NOT NULL        /* ë°°ì†¡ì§?ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, ship_name          NVARCHAR2(10)   NOT NULL        /* ë°°ì†¡ì§?ëª? */
, ship_postcode      VARCHAR2(5)                     /* ?š°?¸ë²ˆí˜¸ */
, ship_address       VARCHAR2(200)                   /* ì£¼ì†Œ */
, ship_detailaddress VARCHAR2(200)                   /* ?ƒ?„¸ì£¼ì†Œ */
, ship_extraadress   VARCHAR2(200)                   /* ì°¸ê³ ?‚¬?•­ */
, ship_default       NUMBER(1)     DEFAULT 0         /* ê¸°ë³¸ë°°ì†¡ì§??„¤? •?—¬ë¶?(0:ê·¸ì™¸, 1:ê¸°ë³¸) */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_ship_ship_no      PRIMARY KEY (ship_no)
, CONSTRAINT FK_tbl_ship_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
);
-- Table TBL_SHIP created.


COMMENT ON TABLE tbl_ship IS 'ë°°ì†¡ì§? ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_ship.ship_no IS 'ë°°ì†¡ì§?ë²ˆí˜¸';

COMMENT ON COLUMN tbl_ship.fk_user_no IS '?šŒ?›ë²ˆí˜¸';

COMMENT ON COLUMN tbl_ship.ship_name IS 'ë°°ì†¡ì§?ëª?';

COMMENT ON COLUMN tbl_ship.ship_postcode IS 'ë°°ì†¡ì§??š°?¸ë²ˆí˜¸';

COMMENT ON COLUMN tbl_ship.ship_address IS 'ë°°ì†¡ì§?ì£¼ì†Œ';

COMMENT ON COLUMN tbl_ship.ship_detailaddress IS 'ë°°ì†¡ì§? ?ƒ?„¸ì£¼ì†Œ';

COMMENT ON COLUMN tbl_ship.ship_extraadress IS 'ë°°ì†¡ì§? ì°¸ê³ ?‚¬?•­';

COMMENT ON COLUMN tbl_ship.ship_default IS 'ê¸°ë³¸ë°°ì†¡ì§??„¤? •?—¬ë¶?(0:ê·¸ì™¸, 1:ê¸°ë³¸)';



CREATE SEQUENCE ship_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence SHIP_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_SHIP';
/* ë°°ì†¡ì§? ?…Œ?´ë¸? ? */







/* ì°œëª©ë¡? ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_wish
( wish_no            NUMBER          NOT NULL        /* ì°œë²ˆ?˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, fk_prod_no         NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_wish_wish_no      PRIMARY KEY (wish_no)
, CONSTRAINT FK_tbl_wish_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_wish_fk_prod_no   FOREIGN KEY (fk_prod_no) REFERENCES tbl_products(prod_no)
);
-- Table TBL_WISH created.


COMMENT ON TABLE tbl_wish IS 'ì°œëª©ë¡? ? •ë³´ë?? ?‹´ê³? êº¼ë‚´?Š” ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_wish.wish_no IS 'ë°°ì†¡ì§?ë²ˆí˜¸';

COMMENT ON COLUMN tbl_wish.fk_user_no IS '?šŒ?›ë²ˆí˜¸';

COMMENT ON COLUMN tbl_wish.fk_prod_no IS '?ƒ?’ˆë²ˆí˜¸';


CREATE SEQUENCE wish_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence WISH_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_WISH';
/* ì°œëª©ë¡? ?…Œ?´ë¸? ? */








/* êµ¬ë§¤?›„ê¸? ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_reviews
( review_no          NUMBER          NOT NULL        /* êµ¬ë§¤?›„ê¸°ë²ˆ?˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, fk_prod_no         NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */
, review_title       NVARCHAR2(100)  NOT NULL        /* êµ¬ë§¤?›„ê¸°ì œëª? */
, review_contents    NVARCHAR2(200)  NOT NULL        /* êµ¬ë§¤?›„ê¸°ë‚´?š© */
, review_status      NUMBER(1)  DEFAULT 1            /* êµ¬ë§¤?›„ê¸°ìƒ?ƒœ (0:?‚­? œ, 1:ê²Œì‹œì¤?) */
, review_viewcount   NUMBER     DEFAULT 0            /* êµ¬ë§¤?›„ê¸? ì¡°íšŒ?ˆ˜ */
, review_image       VARCHAR2(100)                   /* êµ¬ë§¤?›„ê¸? ?´ë¯¸ì? */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_reviews_review_no    PRIMARY KEY (review_no)
, CONSTRAINT FK_tbl_reviews_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_reviews_fk_prod_no   FOREIGN KEY (fk_prod_no) REFERENCES tbl_products(prod_no)
);
-- Table TBL_REVIEWS created.


COMMENT ON TABLE tbl_reviews IS 'êµ¬ë§¤?›„ê¸°ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_reviews.review_no IS '?›„ê¸? ë²ˆí˜¸';

COMMENT ON COLUMN tbl_reviews.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_reviews.fk_prod_no IS '?ƒ?’ˆ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_reviews.review_title IS 'êµ¬ë§¤?›„ê¸°ì œëª?';

COMMENT ON COLUMN tbl_reviews.review_contents IS 'êµ¬ë§¤?›„ê¸°ë‚´?š©';

COMMENT ON COLUMN tbl_reviews.review_status IS 'êµ¬ë§¤?›„ê¸°ìƒ?ƒœ (0:?‚­? œ, 1:ê²Œì‹œì¤?)';

COMMENT ON COLUMN tbl_reviews.review_viewcount IS 'êµ¬ë§¤?›„ê¸? ì¡°íšŒ?ˆ˜';

COMMENT ON COLUMN tbl_reviews.review_image IS 'êµ¬ë§¤?›„ê¸? ?´ë¯¸ì?';



CREATE SEQUENCE review_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence REVIEW_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_REVIEWS';
/* êµ¬ë§¤?›„ê¸? ?…Œ?´ë¸? ? */







/* êµ¬ë§¤?›„ê¸°ëŒ“ê¸? ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_comments
( comment_no         NUMBER          NOT NULL        /* êµ¬ë§¤?›„ê¸°ëŒ“ê¸?ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, fk_review_no       NUMBER          NOT NULL        /* ?›„ê¸°ë²ˆ?˜¸ */
, comment_contents   NVARCHAR2(100)  NOT NULL        /* ?›„ê¸°ëŒ“ê¸? ?‚´?š© */
, comments_pwd       VARCHAR2(200)   NOT NULL        /* ?›„ê¸°ëŒ“ê¸? ë¹„ë?ë²ˆí˜¸ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_comments_comment_no    PRIMARY KEY (comment_no)
, CONSTRAINT FK_tbl_comments_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_comments_fk_review_no  FOREIGN KEY (fk_review_no) REFERENCES tbl_reviews(review_no)
);
-- Table TBL_COMMENTS created.


COMMENT ON TABLE tbl_comments IS 'êµ¬ë§¤?›„ê¸°ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_comments.comment_no IS '?›„ê¸°ëŒ“ê¸? ë²ˆí˜¸';

COMMENT ON COLUMN tbl_comments.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_comments.fk_review_no IS '?›„ê¸? ë²ˆí˜¸';

COMMENT ON COLUMN tbl_comments.comment_contents IS '?›„ê¸°ëŒ“ê¸? ?‚´?š©';

COMMENT ON COLUMN tbl_comments.comments_pwd IS '?›„ê¸°ëŒ“ê¸? ë¹„ë?ë²ˆí˜¸';


CREATE SEQUENCE comment_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence COMMENT_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_COMMENTS';
/* êµ¬ë§¤?›„ê¸°ëŒ“ê¸? ?…Œ?´ë¸? ? */










/* ë¬¸ì˜ê²Œì‹œ?Œ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_qna
( qna_no             NUMBER          NOT NULL        /* ë¬¸ì˜ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, fk_prod_no         NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */
, qna_title          NVARCHAR2(100)  NOT NULL        /* ë¬¸ì˜? œëª? */
, qna_contents       NVARCHAR2(200)  NOT NULL        /* ë¬¸ì˜?‚´?š© */
, qna_regidate       DATE DEFAULT SYSDATE            /* ë¬¸ì˜?“±ë¡ì¼? */
, qna_status         NUMBER     DEFAULT 1            /* ë¬¸ì˜ê¸??ƒ?ƒœ(0:?‚­? œ, 1:ê²Œì‹œì¤?) */
, qna_viewcount      NUMBER     DEFAULT 0            /* ë¬¸ì˜ê¸? ì¡°íšŒ?ˆ˜ */
, qna_answer         NVARCHAR2(200)                  /* ?‹µë³??‚´?š© */

/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_qna_qna_no        PRIMARY KEY (qna_no)
, CONSTRAINT FK_tbl_qna_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_qna_fk_prod_no    FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
, CONSTRAINT CK_tbl_qna_qna_status    CHECK (qna_status IN (0, 1))
);
-- Table TBL_QNA created.


COMMENT ON TABLE tbl_qna IS 'ë¬¸ì˜ê²Œì‹œ?Œ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_qna.qna_no IS 'ë¬¸ì˜ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_qna.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_qna.fk_prod_no IS '?ƒ?’ˆ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_qna.qna_title IS 'ë¬¸ì˜ ? œëª?';

COMMENT ON COLUMN tbl_qna.qna_contents IS 'ë¬¸ì˜ ?‚´?š©';

COMMENT ON COLUMN tbl_qna.qna_regidate IS 'ë¬¸ì˜ ?“±ë¡ì¼?';

COMMENT ON COLUMN tbl_qna.qna_status IS 'ë¬¸ì˜ê¸??ƒ?ƒœ(0:?‚­? œ, 1:ê²Œì‹œì¤?)';

COMMENT ON COLUMN tbl_qna.qna_viewcount IS 'ë¬¸ì˜ê¸? ì¡°íšŒ?ˆ˜';

COMMENT ON COLUMN tbl_qna.qna_answer IS '?‹µë³? ?‚´?š©';


CREATE SEQUENCE qna_answer
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence QNA_ANSWER created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_QNA';
/* ë¬¸ì˜ê²Œì‹œ?Œ ?…Œ?´ë¸? ? */










/* ì£¼ë¬¸ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_order
( order_no           NUMBER          NOT NULL        /* ì£¼ë¬¸ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, order_date         DATE   DEFAULT SYSDATE          /* ì£¼ë¬¸?¼? */
, order_request      NVARCHAR2(50)                   /* ?š”ì²??‚¬?•­ */
, order_tprice       NUMBER                          /* ì´ì£¼ë¬¸ê¸ˆ?•¡ */
, order_status       NUMBER(1)    DEFAULT 1          /* ì£¼ë¬¸ ?ƒ?ƒœ (1: ì£¼ë¬¸ / 2: êµí™˜/ë°˜í’ˆ / 3: ?™˜ë¶? / 4: ì·¨ì†Œ / 5: êµ¬ë§¤?™•? •) */
, order_changedate   DATE   DEFAULT SYSDATE          /* ì£¼ë¬¸ ?ƒ?ƒœ ë³?ê²½ì¼? */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_order_order_no      PRIMARY KEY (order_no)
, CONSTRAINT FK_tbl_order_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT CK_tbl_order_order_status  CHECK (order_status IN (1, 2, 3, 4, 5))
);
-- Table TBL_ORDER created.


COMMENT ON TABLE tbl_order IS 'ì£¼ë¬¸ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_order.order_no IS 'ì£¼ë¬¸ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_order.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_order.order_date IS 'ì£¼ë¬¸ ?¼?';

COMMENT ON COLUMN tbl_order.order_request IS '?š”ì²? ?‚¬?•­';

COMMENT ON COLUMN tbl_order.order_tprice IS 'ì´? ì£¼ë¬¸ê¸ˆì•¡';

COMMENT ON COLUMN tbl_order.order_status IS 'ì£¼ë¬¸ ?ƒ?ƒœ (1: ì£¼ë¬¸ / 2: êµí™˜/ë°˜í’ˆ / 3: ?™˜ë¶? / 4: ì·¨ì†Œ / 5: êµ¬ë§¤?™•? •)';

COMMENT ON COLUMN tbl_order.order_changedate IS 'ì£¼ë¬¸ ?ƒ?ƒœ ë³?ê²½ì¼?';


CREATE SEQUENCE order_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence ORDER_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_ORDER';
/* ì£¼ë¬¸ ?…Œ?´ë¸? ? */








/* ì£¼ë¬¸?ƒ?„¸ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_orderdetail
( ordetail_no        NUMBER          NOT NULL        /* ì£¼ë¬¸?ƒ?„¸ë²ˆí˜¸ */
, fk_order_no        NUMBER          NOT NULL        /* ì£¼ë¬¸ë²ˆí˜¸ */
, fk_prod_no         NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */
, ordetail_count     NUMBER                          /* ?ƒ?’ˆ?ˆ˜?Ÿ‰ */
, ordetail_price     NUMBER                          /* ê°œë³„ ?ƒ?’ˆ ê¸ˆì•¡ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_ordetail_ordetail_no   PRIMARY KEY (ordetail_no)
, CONSTRAINT FK_tbl_ordetail_fk_order_no   FOREIGN KEY (fk_order_no)  REFERENCES tbl_order(order_no)
, CONSTRAINT FK_tbl_ordetail_fk_prod_no    FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
);
-- Table TBL_ORDERDETAIL created.


COMMENT ON TABLE tbl_orderdetail IS 'ì£¼ë¬¸ ?ƒ?„¸ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_orderdetail.ordetail_no IS 'ì£¼ë¬¸ ?ƒ?„¸ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_orderdetail.fk_order_no IS 'ì£¼ë¬¸ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_orderdetail.fk_prod_no IS '?ƒ?’ˆ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_orderdetail.ordetail_count IS '?ƒ?’ˆ ?ˆ˜?Ÿ‰';

COMMENT ON COLUMN tbl_orderdetail.ordetail_price IS 'ê°œë³„ ?ƒ?’ˆ ê¸ˆì•¡';


CREATE SEQUENCE orderdetail_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence ORDERDETAIL_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_ORDERDETAIL';
/* ì£¼ë¬¸?ƒ?„¸ ?…Œ?´ë¸? ? */









/* ê²°ì œ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_payments
( pay_no             NUMBER          NOT NULL        /* ê²°ì œë²ˆí˜¸ */
, fk_order_no        NUMBER          NOT NULL        /* ì£¼ë¬¸ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, pay_date           DATE       DEFAULT SYSDATE      /* ê²°ì œ?¼? */
, pay_refund         NUMBER(1)  DEFAULT 0            /* ?™˜ë¶? ?—¬ë¶?(0: ë¯¸í™˜ë¶?, 1: ?™˜ë¶?) */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_payments_pay_no        PRIMARY KEY (pay_no)
, CONSTRAINT FK_tbl_payments_fk_order_no   FOREIGN KEY (fk_order_no)  REFERENCES tbl_order(order_no)
, CONSTRAINT FK_tbl_payments_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT CK_tbl_payments_pay_refund  CHECK (pay_refund IN (0, 1))
);
-- Table TBL_PAYMENTS created.


COMMENT ON TABLE tbl_payments IS 'ê²°ì œ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_payments.pay_no IS 'ê²°ì œ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_payments.fk_order_no IS 'ì£¼ë¬¸ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_payments.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_payments.pay_date IS 'ê²°ì œ?¼?';

COMMENT ON COLUMN tbl_payments.pay_refund IS '?™˜ë¶? ?—¬ë¶?(0: ë¯¸í™˜ë¶?, 1: ?™˜ë¶?)';


CREATE SEQUENCE payments_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence PAYMENTS_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_PAYMENTS';
/* ê²°ì œ ?…Œ?´ë¸? ? */








/* ?¥ë°”êµ¬?‹ˆ?Š” ì£¼ë¬¸ ?™„ë£? ?‹œ ?•´?‹¹ ?šŒ?›?´ ?‹´?? ?ƒ?’ˆ?“¤?„ delete ?•´?•¼ ?•¨ */
/* ?¥ë°”êµ¬?‹ˆ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_cart
( cart_no            NUMBER          NOT NULL        /* ?¥ë°”êµ¬?‹ˆë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, fk_prod_no         NUMBER          NOT NULL        /* ?ƒ?’ˆë²ˆí˜¸ */
, cart_prodcount     NUMBER          NOT NULL        /* ?ƒ?’ˆ ?ˆ˜?Ÿ‰ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_cart_cart_no      PRIMARY KEY (cart_no)
, CONSTRAINT FK_tbl_cart_fk_user_no   FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_cart_fk_prod_no   FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
);
-- Table TBL_CART created.


COMMENT ON TABLE tbl_cart IS '?¥ë°”êµ¬?‹ˆ?— ?‹´?? ?ƒ?’ˆ ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_cart.cart_no IS '?¥ë°”êµ¬?‹ˆ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_cart.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_cart.fk_prod_no IS '?ƒ?’ˆ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_cart.cart_prodcount IS '?ƒ?’ˆ ?ˆ˜?Ÿ‰';



CREATE SEQUENCE cart_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence CART_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_CART';
/* ?¥ë°”êµ¬?‹ˆ ?…Œ?´ë¸? ? */










/* ê³µì??‚¬?•­ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_notice
( notice_no          NUMBER          NOT NULL        /* ê³µì??‚¬?•­ë²ˆí˜¸ */
, notice_title       NVARCHAR2(100)  NOT NULL        /* ê³µì??‚¬?•­? œëª? */
, notice_contents    NVARCHAR2(200)  NOT NULL        /* ê³µì??‚¬?•­?‚´?š© */
, notice_regidate    DATE   DEFAULT SYSDATE          /* ê³µì??‚¬?•­ ?‘?„±?¼? */
, notice_status      NUMBER(1)     DEFAULT 1         /* ê²Œì‹œê¸? ?ƒ?ƒœ (0: ?‚­? œ, 1:ê²Œì‹œì¤?) */
, notice_viewcount   NUMBER        DEFAULT 0         /* ê³µì??‚¬?•­ ì¡°íšŒ?ˆ˜ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_notice_notice_no      PRIMARY KEY (notice_no)
, CONSTRAINT CK_tbl_notice_notice_status  CHECK (notice_status IN (0, 1))
);
-- Table TBL_NOTICE created.


COMMENT ON TABLE tbl_notice IS 'ê³µì??‚¬?•­ ê¸? ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_notice.notice_no IS 'ê³µì??‚¬?•­ ë²ˆí˜¸';

COMMENT ON COLUMN tbl_notice.notice_title IS 'ê³µì??‚¬?•­ ? œëª?';

COMMENT ON COLUMN tbl_notice.notice_contents IS 'ê³µì??‚¬?•­ ?‚´?š©';

COMMENT ON COLUMN tbl_notice.notice_regidate IS 'ê³µì??‚¬?•­ ?“±ë¡ì¼?';

COMMENT ON COLUMN tbl_notice.notice_status IS 'ê³µì??‚¬?•­ ê¸? ?ƒ?ƒœ (0: ?‚­? œ, 1:ê²Œì‹œì¤?)';

COMMENT ON COLUMN tbl_notice.notice_viewcount IS 'ê³µì??‚¬?•­ ì¡°íšŒ?ˆ˜';


CREATE SEQUENCE notice_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence NOTICE_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_NOTICE';
/* ê³µì??‚¬?•­ ?…Œ?´ë¸? ? */










/* ?ì£¼í•˜?Š”ì§ˆë¬¸ ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_faq
( faq_no             NUMBER          NOT NULL        /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ë²ˆí˜¸ */
, faq_title          NVARCHAR2(100)  NOT NULL        /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ? œëª? */
, faq_contents       NVARCHAR2(200)  NOT NULL        /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ?‚´?š© */
, faq_regidate       DATE   DEFAULT SYSDATE          /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ?‘?„±?¼? */
, faq_status         NUMBER(1)     DEFAULT 1         /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ?ƒ?ƒœ (0: ?‚­? œ, 1:ê²Œì‹œì¤?) */
, faq_viewcount      NUMBER        DEFAULT 0         /* ?ì£¼í•˜?Š”ì§ˆë¬¸ ì¡°íšŒ?ˆ˜ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_faqe_faq_no     PRIMARY KEY (faq_no)
, CONSTRAINT CK_tbl_faq_faq_status  CHECK (faq_status IN (0, 1))
);
-- Table TBL_FAQ created.


COMMENT ON TABLE tbl_faq IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_faq.faq_no IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ë²ˆí˜¸';

COMMENT ON COLUMN tbl_faq.faq_title IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ? œëª?';

COMMENT ON COLUMN tbl_faq.faq_contents IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ?‚´?š©';

COMMENT ON COLUMN tbl_faq.faq_regidate IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ?“±ë¡ì¼?';

COMMENT ON COLUMN tbl_faq.faq_status IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ?ƒ?ƒœ (0: ?‚­? œ, 1:ê²Œì‹œì¤?)';

COMMENT ON COLUMN tbl_faq.faq_viewcount IS '?ì£¼í•˜?Š”ì§ˆë¬¸ ê¸? ì¡°íšŒ?ˆ˜';


CREATE SEQUENCE faq_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence FAQ_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_FAQ';
/* ?ì£¼í•˜?Š”ì§ˆë¬¸ ?…Œ?´ë¸? ? */









/* ì¿ í° ?…Œ?´ë¸? ?ƒ?„± ?‹œ?‘ */
CREATE TABLE tbl_coupons
( coupon_no          NUMBER          NOT NULL        /* ì¿ í°ë²ˆí˜¸ */
, fk_user_no         NUMBER          NOT NULL        /* ?šŒ?›ë²ˆí˜¸ */
, coupon_name        VARCHAR2(50)    NOT NULL        /* ì¿ í°ëª? */
, coupon_descript    VARCHAR2(50)    NOT NULL        /* ì¿ í°?„¤ëª? */
, coupon_expire      DATE            NOT NULL        /* ì¿ í° ë§Œë£Œ?¼? */
, coupon_discount    NUMBER          NOT NULL        /* ?• ?¸ê¸ˆì•¡ */


/* ? œ?•½ì¡°ê±´ */
, CONSTRAINT PK_tbl_coupons_coupon_no   PRIMARY KEY (coupon_no)
, CONSTRAINT FK_tbl_coupons_fk_user_no  FOREIGN KEY (fk_user_no)  REFERENCES tbl_member(user_no)
);
-- Table TBL_COUPONS created.


COMMENT ON TABLE tbl_coupons IS 'ì¿ í° ? •ë³´ê? ?‹´ê¸? ?…Œ?´ë¸?';

COMMENT ON COLUMN tbl_coupons.coupon_no IS 'ì¿ í° ë²ˆí˜¸';

COMMENT ON COLUMN tbl_coupons.fk_user_no IS '?šŒ?› ë²ˆí˜¸';

COMMENT ON COLUMN tbl_coupons.coupon_name IS 'ì¿ í°ëª?';

COMMENT ON COLUMN tbl_coupons.coupon_descript IS 'ì¿ í° ?„¤ëª?';

COMMENT ON COLUMN tbl_coupons.coupon_expire IS 'ì¿ í° ë§Œë£Œ ?¼?';

COMMENT ON COLUMN tbl_coupons.coupon_discount IS '?• ?¸ ê¸ˆì•¡';


CREATE SEQUENCE coupon_seq
START WITH 1
INCREMENT BY 1
NOMAXVALUE
NOMINVALUE
NOCYCLE
NOCACHE;
-- Sequence ORDERDETAIL_SEQ created.


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_COUPONS';
/* ì¿ í° ?…Œ?´ë¸? ? */

select *
from tbl_member;

select user_no, userid, passwd, name, birthday, email, tel,
                postcode, address, detailaddress, extraaddress, gender, point,
				   registerday, lastpwdchangedate, idle, status, role 
				   from tbl_member;

SELECT * FROM tab;

6 4 11 15 
select * from user_recyclebin;

purge recyclebin;

tbl_member

SELECT * FROM tbl_member;
