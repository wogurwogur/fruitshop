---- **** fruitshop 세미웹프로젝트 에서 작업한 것 **** ----

-- 오라클 계정 생성을 위해서는 SYS 또는 SYSTEM 으로 연결하여 작업을 해야 합니다. [SYS 시작] --
show user;
-- USER이(가) "SYS"입니다.

-- 오라클 계정 생성시 계정명 앞에 c## 붙이지 않고 생성하도록 하겠습니다.
alter session set "_ORACLE_SCRIPT"=true;
-- Session이(가) 변경되었습니다.

-- 오라클 계정명은 MYMVC_USER 이고 암호는 gclass 인 사용자 계정을 생성합니다.
create user fruitshop_user identified by gclass default tablespace users;
-- User MYMVC_USER이(가) 생성되었습니다.

-- 위에서 생성되어진 MYMVC_USER 이라는 오라클 일반사용자 계정에게 오라클 서버에 접속이 되어지고,
-- 테이블 생성 등등을 할 수 있도록 여러가지 권한을 부여해주겠습니다.
grant connect, resource, create view, unlimited tablespace to fruitshop_user;
-- Grant을(를) 성공했습니다.

-----------------------------------------------------------------------


show user;


/* 메인페이지 배너 테이블 생성 시작 */
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
/* 메인페이지 배너 테이블 끝 */



/* 회원 테이블 생성 시작 */
CREATE TABLE tbl_member
( user_no           NUMBER          NOT NULL        /* 회원번호 */
, userid            VARCHAR2(20)    NOT NULL        /* 회원아이디 */
, passwd            VARCHAR2(200)   NOT NULL        /* 회원비밀번호 */
, name              NVARCHAR2(10)   NOT NULL        /* 회원이름 */
, birthday          VARCHAR2(20)    NOT NULL        /* 생년월일 */
, email             VARCHAR2(200)   NOT NULL        /* 이메일 */
, tel               VARCHAR2(15)                    /* 연락처 */
, postcode          VARCHAR2(5)                     /* 우편번호 */
, address           VARCHAR2(200)                   /* 주소 */
, detailaddress     VARCHAR2(200)                   /* 상세주소 */
, extraadress       VARCHAR2(200)                   /* 참고사항 */
, gender            NVARCHAR2(2)                    /* 성별(남/여) */
, point             NUMBER    DEFAULT 0             /* 적립금 */
, registerday       DATE DEFAULT SYSDATE NOT NULL   /* 가입일 */
, lastpwdchangedate DATE DEFAULT SYSDATE NOT NULL   /* 마지막 비밀번호 변경일 */
, idle              NUMBER(1) DEFAULT 1  NOT NULL   /* 휴면상태(0:휴면중, 1:활동중) */
, status            NUMBER(1) DEFAULT 1  NOT NULL   /* 가입상태(0:탈퇴, 1:가입중) */
, role              NUMBER(1) DEFAULT 1  NOT NULL   /* 유저권한(1:일반유저, 2:관리자) */


/* 제약조건 */
, CONSTRAINT PK_tbl_member_user_no PRIMARY KEY (user_no)
, CONSTRAINT UQ_tbl_member_userid  UNIQUE (userid)
, CONSTRAINT UQ_tbl_member_email   UNIQUE (email)
, CONSTRAINT CK_tbl_member_idle    CHECK (idle IN (0, 1))
, CONSTRAINT CK_tbl_member_status  CHECK (status IN (0, 1))
, CONSTRAINT CK_tbl_member_role    CHECK (role IN (1, 2))
);
-- Table TBL_MEMBER created.


COMMENT ON TABLE tbl_member IS '회원 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_member.user_no IS '회원번호';

COMMENT ON COLUMN tbl_member.userid IS '회원아이디';

COMMENT ON COLUMN tbl_member.passwd IS '회원비밀번호';

COMMENT ON COLUMN tbl_member.name IS '회원이름';

COMMENT ON COLUMN tbl_member.birthday IS '생년월일';

COMMENT ON COLUMN tbl_member.email IS '이메일';

COMMENT ON COLUMN tbl_member.tel IS '연락처';

COMMENT ON COLUMN tbl_member.postcode IS '우편번호';

COMMENT ON COLUMN tbl_member.address IS '주소';

COMMENT ON COLUMN tbl_member.detailaddress IS '상세주소';

COMMENT ON COLUMN tbl_member.extraadress IS '참고사항';

COMMENT ON COLUMN tbl_member.gender IS '성별(남/여)';

COMMENT ON COLUMN tbl_member.point IS '적립금';

COMMENT ON COLUMN tbl_member.registerday IS '가입일';

COMMENT ON COLUMN tbl_member.lastpwdchangedate IS '마지막 비밀번호 변경일';

COMMENT ON COLUMN tbl_member.idle IS '휴면상태(0:휴면중, 1:활동중)';

COMMENT ON COLUMN tbl_member.status IS '가입상태(0:탈퇴, 1:가입중)';

COMMENT ON COLUMN tbl_member.role IS '유저권한(1:일반유저, 2:관리자)';


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
/* 회원 테이블 끝 */








/* 로그인 기록 테이블 생성 시작 */
CREATE TABLE tbl_loginhistory
( loghis_no         NUMBER          NOT NULL        /* 로그인기록 번호 */
, fk_user_no        NUMBER          NOT NULL        /* 회원번호 */
, login_date        DATE DEFAULT SYSDATE NOT NULL   /* 로그인일자 */
, clinetip          VARCHAR2(20)    NOT NULL        /* 접속자 IP주소 */


/* 제약조건 */
, CONSTRAINT PK_tbl_history_loghis_no   PRIMARY KEY (loghis_no)
, CONSTRAINT FK_tbl_history_fk_user_no  FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
);
-- Table TBL_LOGINHISTORY created.


COMMENT ON TABLE tbl_loginhistory IS '로그인 기록이 담기는 테이블';

COMMENT ON COLUMN tbl_loginhistory.loghis_no IS '로그인 기록 번호';

COMMENT ON COLUMN tbl_loginhistory.fk_user_no IS '회원번호';

COMMENT ON COLUMN tbl_loginhistory.login_date IS '로그인 일자';

COMMENT ON COLUMN tbl_loginhistory.clinetip IS '접속자 IP주소';



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
/* 로그인 기록 테이블 끝 */








/* 계절 테이블 생성 시작 */
CREATE TABLE tbl_seasons
( season_no         NUMBER          NOT NULL        /* 계절번호 */
, season_name       NVARCHAR2(5)    NOT NULL        /* 계절명(봄/여름/가을/겨울) */
, season_image      VARCHAR2(50)    NOT NULL        /* 계절대표이미지 */

, CONSTRAINT PK_tbl_seasons_season_no   PRIMARY KEY (season_no)
);
-- Table TBL_SEASONS created.


COMMENT ON TABLE tbl_seasons IS '계절 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_seasons.season_no IS '계절번호';

COMMENT ON COLUMN tbl_seasons.season_name IS '계절명(봄/여름/가을/겨울)';

COMMENT ON COLUMN tbl_seasons.season_image IS '계절대표이미지';


--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (1, '봄', 'sping.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (2, '여름', 'summer.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (3, '가을', 'autumn.png');

--INSERT INTO tbl_seasons(season_no, season_name, season_image) VALUES (4, '겨울', 'winter.png');

--COMMIT;


SELECT column_name, comments
  FROM user_col_comments
 WHERE table_name = 'TBL_SEASONS';
/* 계절 테이블 끝 */








/* 상품 테이블 생성 시작 */
CREATE TABLE tbl_products
( prod_no           NUMBER          NOT NULL        /* 상품번호 */
, prod_name         NVARCHAR2(20)   NOT NULL        /* 상품명 */
, prod_cost         NUMBER          NOT NULL        /* 상품원가 */
, prod_price        NUMBER          NOT NULL        /* 상품가격 */
, prod_thumnail     VARCHAR2(100)                   /* 상품썸네일 */
, prod_descript     VARCHAR2(100)                   /* 상품 설명 상세내용 */
, prod_inventory    NUMBER(1)       DEFAULT 0       /* 상품재고량 */
, fk_season_no      NUMBER          NOT NULL        /* 계절번호 */


/* 제약조건 */
, CONSTRAINT PK_tbl_products_prod_no        PRIMARY KEY (prod_no)
, CONSTRAINT FK_tbl_products_fk_season_no   FOREIGN KEY (fk_season_no) REFERENCES tbl_seasons(season_no)
);
-- Table TBL_PRODUCTS created.


COMMENT ON TABLE tbl_products IS '상품 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_products.prod_no IS '상품번호';

COMMENT ON COLUMN tbl_products.prod_name IS '상품명';

COMMENT ON COLUMN tbl_products.prod_cost IS '상품원가';

COMMENT ON COLUMN tbl_products.prod_price IS '상품가격';

COMMENT ON COLUMN tbl_products.prod_thumnail IS '상품썸네일';

COMMENT ON COLUMN tbl_products.prod_descript IS '상품 설명 상세내용';

COMMENT ON COLUMN tbl_products.prod_inventory IS '상품재고량';

COMMENT ON COLUMN tbl_products.fk_season_no IS '계절번호';



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
/* 상품 테이블 끝 */








/* 배송지 테이블 생성 시작 */
CREATE TABLE tbl_ship
( ship_no            NUMBER          NOT NULL        /* 배송지번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, ship_name          NVARCHAR2(10)   NOT NULL        /* 배송지명 */
, ship_postcode      VARCHAR2(5)                     /* 우편번호 */
, ship_address       VARCHAR2(200)                   /* 주소 */
, ship_detailaddress VARCHAR2(200)                   /* 상세주소 */
, ship_extraadress   VARCHAR2(200)                   /* 참고사항 */
, ship_default       NUMBER(1)     DEFAULT 0         /* 기본배송지설정여부(0:그외, 1:기본) */


/* 제약조건 */
, CONSTRAINT PK_tbl_ship_ship_no      PRIMARY KEY (ship_no)
, CONSTRAINT FK_tbl_ship_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
);
-- Table TBL_SHIP created.


COMMENT ON TABLE tbl_ship IS '배송지 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_ship.ship_no IS '배송지번호';

COMMENT ON COLUMN tbl_ship.fk_user_no IS '회원번호';

COMMENT ON COLUMN tbl_ship.ship_name IS '배송지명';

COMMENT ON COLUMN tbl_ship.ship_postcode IS '배송지우편번호';

COMMENT ON COLUMN tbl_ship.ship_address IS '배송지주소';

COMMENT ON COLUMN tbl_ship.ship_detailaddress IS '배송지 상세주소';

COMMENT ON COLUMN tbl_ship.ship_extraadress IS '배송지 참고사항';

COMMENT ON COLUMN tbl_ship.ship_default IS '기본배송지설정여부(0:그외, 1:기본)';



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
/* 배송지 테이블 끝 */







/* 찜목록 테이블 생성 시작 */
CREATE TABLE tbl_wish
( wish_no            NUMBER          NOT NULL        /* 찜번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, fk_prod_no         NUMBER          NOT NULL        /* 상품번호 */


/* 제약조건 */
, CONSTRAINT PK_tbl_wish_wish_no      PRIMARY KEY (wish_no)
, CONSTRAINT FK_tbl_wish_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_wish_fk_prod_no   FOREIGN KEY (fk_prod_no) REFERENCES tbl_products(prod_no)
);
-- Table TBL_WISH created.


COMMENT ON TABLE tbl_wish IS '찜목록 정보를 담고 꺼내는 테이블';

COMMENT ON COLUMN tbl_wish.wish_no IS '배송지번호';

COMMENT ON COLUMN tbl_wish.fk_user_no IS '회원번호';

COMMENT ON COLUMN tbl_wish.fk_prod_no IS '상품번호';


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
/* 찜목록 테이블 끝 */








/* 구매후기 테이블 생성 시작 */
CREATE TABLE tbl_reviews
( review_no          NUMBER          NOT NULL        /* 구매후기번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, fk_prod_no         NUMBER          NOT NULL        /* 상품번호 */
, review_title       NVARCHAR2(100)  NOT NULL        /* 구매후기제목 */
, review_contents    NVARCHAR2(200)  NOT NULL        /* 구매후기내용 */
, review_status      NUMBER(1)  DEFAULT 1            /* 구매후기상태 (0:삭제, 1:게시중) */
, review_viewcount   NUMBER     DEFAULT 0            /* 구매후기 조회수 */
, review_image       VARCHAR2(100)                   /* 구매후기 이미지 */


/* 제약조건 */
, CONSTRAINT PK_tbl_reviews_review_no    PRIMARY KEY (review_no)
, CONSTRAINT FK_tbl_reviews_fk_user_no   FOREIGN KEY (fk_user_no) REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_reviews_fk_prod_no   FOREIGN KEY (fk_prod_no) REFERENCES tbl_products(prod_no)
);
-- Table TBL_REVIEWS created.


COMMENT ON TABLE tbl_reviews IS '구매후기가 담긴 테이블';

COMMENT ON COLUMN tbl_reviews.review_no IS '후기 번호';

COMMENT ON COLUMN tbl_reviews.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_reviews.fk_prod_no IS '상품 번호';

COMMENT ON COLUMN tbl_reviews.review_title IS '구매후기제목';

COMMENT ON COLUMN tbl_reviews.review_contents IS '구매후기내용';

COMMENT ON COLUMN tbl_reviews.review_status IS '구매후기상태 (0:삭제, 1:게시중)';

COMMENT ON COLUMN tbl_reviews.review_viewcount IS '구매후기 조회수';

COMMENT ON COLUMN tbl_reviews.review_image IS '구매후기 이미지';



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
/* 구매후기 테이블 끝 */







/* 구매후기댓글 테이블 생성 시작 */
CREATE TABLE tbl_comments
( comment_no         NUMBER          NOT NULL        /* 구매후기댓글번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, fk_review_no       NUMBER          NOT NULL        /* 후기번호 */
, comment_contents   NVARCHAR2(100)  NOT NULL        /* 후기댓글 내용 */
, comments_pwd       VARCHAR2(200)   NOT NULL        /* 후기댓글 비밀번호 */


/* 제약조건 */
, CONSTRAINT PK_tbl_comments_comment_no    PRIMARY KEY (comment_no)
, CONSTRAINT FK_tbl_comments_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_comments_fk_review_no  FOREIGN KEY (fk_review_no) REFERENCES tbl_reviews(review_no)
);
-- Table TBL_COMMENTS created.


COMMENT ON TABLE tbl_comments IS '구매후기가 담긴 테이블';

COMMENT ON COLUMN tbl_comments.comment_no IS '후기댓글 번호';

COMMENT ON COLUMN tbl_comments.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_comments.fk_review_no IS '후기 번호';

COMMENT ON COLUMN tbl_comments.comment_contents IS '후기댓글 내용';

COMMENT ON COLUMN tbl_comments.comments_pwd IS '후기댓글 비밀번호';


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
/* 구매후기댓글 테이블 끝 */










/* 문의게시판 테이블 생성 시작 */
CREATE TABLE tbl_qna
( qna_no             NUMBER          NOT NULL        /* 문의번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, fk_prod_no         NUMBER          NOT NULL        /* 상품번호 */
, qna_title          NVARCHAR2(100)  NOT NULL        /* 문의제목 */
, qna_contents       NVARCHAR2(200)  NOT NULL        /* 문의내용 */
, qna_regidate       DATE DEFAULT SYSDATE            /* 문의등록일자 */
, qna_status         NUMBER     DEFAULT 1            /* 문의글상태(0:삭제, 1:게시중) */
, qna_viewcount      NUMBER     DEFAULT 0            /* 문의글 조회수 */
, qna_answer         NVARCHAR2(200)                  /* 답변내용 */

/* 제약조건 */
, CONSTRAINT PK_tbl_qna_qna_no        PRIMARY KEY (qna_no)
, CONSTRAINT FK_tbl_qna_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_qna_fk_prod_no    FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
, CONSTRAINT CK_tbl_qna_qna_status    CHECK (qna_status IN (0, 1))
);
-- Table TBL_QNA created.


COMMENT ON TABLE tbl_qna IS '문의게시판 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_qna.qna_no IS '문의 번호';

COMMENT ON COLUMN tbl_qna.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_qna.fk_prod_no IS '상품 번호';

COMMENT ON COLUMN tbl_qna.qna_title IS '문의 제목';

COMMENT ON COLUMN tbl_qna.qna_contents IS '문의 내용';

COMMENT ON COLUMN tbl_qna.qna_regidate IS '문의 등록일자';

COMMENT ON COLUMN tbl_qna.qna_status IS '문의글상태(0:삭제, 1:게시중)';

COMMENT ON COLUMN tbl_qna.qna_viewcount IS '문의글 조회수';

COMMENT ON COLUMN tbl_qna.qna_answer IS '답변 내용';


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
/* 문의게시판 테이블 끝 */










/* 주문 테이블 생성 시작 */
CREATE TABLE tbl_order
( order_no           NUMBER          NOT NULL        /* 주문번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, order_date         DATE   DEFAULT SYSDATE          /* 주문일자 */
, order_request      NVARCHAR2(50)                   /* 요청사항 */
, order_tprice       NUMBER                          /* 총주문금액 */
, order_status       NUMBER(1)    DEFAULT 1          /* 주문 상태 (1: 주문 / 2: 교환/반품 / 3: 환불 / 4: 취소 / 5: 구매확정) */
, order_changedate   DATE   DEFAULT SYSDATE          /* 주문 상태 변경일자 */


/* 제약조건 */
, CONSTRAINT PK_tbl_order_order_no      PRIMARY KEY (order_no)
, CONSTRAINT FK_tbl_order_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT CK_tbl_order_order_status  CHECK (order_status IN (1, 2, 3, 4, 5))
);
-- Table TBL_ORDER created.


COMMENT ON TABLE tbl_order IS '주문 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_order.order_no IS '주문 번호';

COMMENT ON COLUMN tbl_order.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_order.order_date IS '주문 일자';

COMMENT ON COLUMN tbl_order.order_request IS '요청 사항';

COMMENT ON COLUMN tbl_order.order_tprice IS '총 주문금액';

COMMENT ON COLUMN tbl_order.order_status IS '주문 상태 (1: 주문 / 2: 교환/반품 / 3: 환불 / 4: 취소 / 5: 구매확정)';

COMMENT ON COLUMN tbl_order.order_changedate IS '주문 상태 변경일자';


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
/* 주문 테이블 끝 */








/* 주문상세 테이블 생성 시작 */
CREATE TABLE tbl_orderdetail
( ordetail_no        NUMBER          NOT NULL        /* 주문상세번호 */
, fk_order_no        NUMBER          NOT NULL        /* 주문번호 */
, fk_prod_no         NUMBER          NOT NULL        /* 상품번호 */
, ordetail_count     NUMBER                          /* 상품수량 */
, ordetail_price     NUMBER                          /* 개별 상품 금액 */


/* 제약조건 */
, CONSTRAINT PK_tbl_ordetail_ordetail_no   PRIMARY KEY (ordetail_no)
, CONSTRAINT FK_tbl_ordetail_fk_order_no   FOREIGN KEY (fk_order_no)  REFERENCES tbl_order(order_no)
, CONSTRAINT FK_tbl_ordetail_fk_prod_no    FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
);
-- Table TBL_ORDERDETAIL created.


COMMENT ON TABLE tbl_orderdetail IS '주문 상세 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_orderdetail.ordetail_no IS '주문 상세 번호';

COMMENT ON COLUMN tbl_orderdetail.fk_order_no IS '주문 번호';

COMMENT ON COLUMN tbl_orderdetail.fk_prod_no IS '상품 번호';

COMMENT ON COLUMN tbl_orderdetail.ordetail_count IS '상품 수량';

COMMENT ON COLUMN tbl_orderdetail.ordetail_price IS '개별 상품 금액';


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
/* 주문상세 테이블 끝 */









/* 결제 테이블 생성 시작 */
CREATE TABLE tbl_payments
( pay_no             NUMBER          NOT NULL        /* 결제번호 */
, fk_order_no        NUMBER          NOT NULL        /* 주문번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, pay_date           DATE       DEFAULT SYSDATE      /* 결제일자 */
, pay_refund         NUMBER(1)  DEFAULT 0            /* 환불 여부(0: 미환불, 1: 환불) */


/* 제약조건 */
, CONSTRAINT PK_tbl_payments_pay_no        PRIMARY KEY (pay_no)
, CONSTRAINT FK_tbl_payments_fk_order_no   FOREIGN KEY (fk_order_no)  REFERENCES tbl_order(order_no)
, CONSTRAINT FK_tbl_payments_fk_user_no    FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT CK_tbl_payments_pay_refund  CHECK (pay_refund IN (0, 1))
);
-- Table TBL_PAYMENTS created.


COMMENT ON TABLE tbl_payments IS '결제 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_payments.pay_no IS '결제 번호';

COMMENT ON COLUMN tbl_payments.fk_order_no IS '주문 번호';

COMMENT ON COLUMN tbl_payments.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_payments.pay_date IS '결제일자';

COMMENT ON COLUMN tbl_payments.pay_refund IS '환불 여부(0: 미환불, 1: 환불)';


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
/* 결제 테이블 끝 */








/* 장바구니는 주문 완료 시 해당 회원이 담은 상품들을 delete 해야 함 */
/* 장바구니 테이블 생성 시작 */
CREATE TABLE tbl_cart
( cart_no            NUMBER          NOT NULL        /* 장바구니번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, fk_prod_no         NUMBER          NOT NULL        /* 상품번호 */
, cart_prodcount     NUMBER          NOT NULL        /* 상품 수량 */


/* 제약조건 */
, CONSTRAINT PK_tbl_cart_cart_no      PRIMARY KEY (cart_no)
, CONSTRAINT FK_tbl_cart_fk_user_no   FOREIGN KEY (fk_user_no)   REFERENCES tbl_member(user_no)
, CONSTRAINT FK_tbl_cart_fk_prod_no   FOREIGN KEY (fk_prod_no)   REFERENCES tbl_products(prod_no)
);
-- Table TBL_CART created.


COMMENT ON TABLE tbl_cart IS '장바구니에 담은 상품 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_cart.cart_no IS '장바구니 번호';

COMMENT ON COLUMN tbl_cart.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_cart.fk_prod_no IS '상품 번호';

COMMENT ON COLUMN tbl_cart.cart_prodcount IS '상품 수량';



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
/* 장바구니 테이블 끝 */










/* 공지사항 테이블 생성 시작 */
CREATE TABLE tbl_notice
( notice_no          NUMBER          NOT NULL        /* 공지사항번호 */
, notice_title       NVARCHAR2(100)  NOT NULL        /* 공지사항제목 */
, notice_contents    NVARCHAR2(200)  NOT NULL        /* 공지사항내용 */
, notice_regidate    DATE   DEFAULT SYSDATE          /* 공지사항 작성일자 */
, notice_status      NUMBER(1)     DEFAULT 1         /* 게시글 상태 (0: 삭제, 1:게시중) */
, notice_viewcount   NUMBER        DEFAULT 0         /* 공지사항 조회수 */


/* 제약조건 */
, CONSTRAINT PK_tbl_notice_notice_no      PRIMARY KEY (notice_no)
, CONSTRAINT CK_tbl_notice_notice_status  CHECK (notice_status IN (0, 1))
);
-- Table TBL_NOTICE created.


COMMENT ON TABLE tbl_notice IS '공지사항 글 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_notice.notice_no IS '공지사항 번호';

COMMENT ON COLUMN tbl_notice.notice_title IS '공지사항 제목';

COMMENT ON COLUMN tbl_notice.notice_contents IS '공지사항 내용';

COMMENT ON COLUMN tbl_notice.notice_regidate IS '공지사항 등록일자';

COMMENT ON COLUMN tbl_notice.notice_status IS '공지사항 글 상태 (0: 삭제, 1:게시중)';

COMMENT ON COLUMN tbl_notice.notice_viewcount IS '공지사항 조회수';


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
/* 공지사항 테이블 끝 */










/* 자주하는질문 테이블 생성 시작 */
CREATE TABLE tbl_faq
( faq_no             NUMBER          NOT NULL        /* 자주하는질문 번호 */
, faq_title          NVARCHAR2(100)  NOT NULL        /* 자주하는질문 제목 */
, faq_contents       NVARCHAR2(200)  NOT NULL        /* 자주하는질문 내용 */
, faq_regidate       DATE   DEFAULT SYSDATE          /* 자주하는질문 작성일자 */
, faq_status         NUMBER(1)     DEFAULT 1         /* 자주하는질문 상태 (0: 삭제, 1:게시중) */
, faq_viewcount      NUMBER        DEFAULT 0         /* 자주하는질문 조회수 */


/* 제약조건 */
, CONSTRAINT PK_tbl_faqe_faq_no     PRIMARY KEY (faq_no)
, CONSTRAINT CK_tbl_faq_faq_status  CHECK (faq_status IN (0, 1))
);
-- Table TBL_FAQ created.


COMMENT ON TABLE tbl_faq IS '자주하는질문 글 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_faq.faq_no IS '자주하는질문 글 번호';

COMMENT ON COLUMN tbl_faq.faq_title IS '자주하는질문 글 제목';

COMMENT ON COLUMN tbl_faq.faq_contents IS '자주하는질문 글 내용';

COMMENT ON COLUMN tbl_faq.faq_regidate IS '자주하는질문 글 등록일자';

COMMENT ON COLUMN tbl_faq.faq_status IS '자주하는질문 글 상태 (0: 삭제, 1:게시중)';

COMMENT ON COLUMN tbl_faq.faq_viewcount IS '자주하는질문 글 조회수';


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
/* 자주하는질문 테이블 끝 */









/* 쿠폰 테이블 생성 시작 */
CREATE TABLE tbl_coupons
( coupon_no          NUMBER          NOT NULL        /* 쿠폰번호 */
, fk_user_no         NUMBER          NOT NULL        /* 회원번호 */
, coupon_name        VARCHAR2(50)    NOT NULL        /* 쿠폰명 */
, coupon_descript    VARCHAR2(50)    NOT NULL        /* 쿠폰설명 */
, coupon_expire      DATE            NOT NULL        /* 쿠폰 만료일자 */
, coupon_discount    NUMBER          NOT NULL        /* 할인금액 */


/* 제약조건 */
, CONSTRAINT PK_tbl_coupons_coupon_no   PRIMARY KEY (coupon_no)
, CONSTRAINT FK_tbl_coupons_fk_user_no  FOREIGN KEY (fk_user_no)  REFERENCES tbl_member(user_no)
);
-- Table TBL_COUPONS created.


COMMENT ON TABLE tbl_coupons IS '쿠폰 정보가 담긴 테이블';

COMMENT ON COLUMN tbl_coupons.coupon_no IS '쿠폰 번호';

COMMENT ON COLUMN tbl_coupons.fk_user_no IS '회원 번호';

COMMENT ON COLUMN tbl_coupons.coupon_name IS '쿠폰명';

COMMENT ON COLUMN tbl_coupons.coupon_descript IS '쿠폰 설명';

COMMENT ON COLUMN tbl_coupons.coupon_expire IS '쿠폰 만료 일자';

COMMENT ON COLUMN tbl_coupons.coupon_discount IS '할인 금액';


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
/* 쿠폰 테이블 끝 */



SELECT * FROM tab;

6 4 11 15 
select * from user_recyclebin;

purge recyclebin;
