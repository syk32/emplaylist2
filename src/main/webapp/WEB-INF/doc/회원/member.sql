/**********************************/
/* Table Name: 회원 */
/**********************************/

- 테이블 구조
-- member 삭제전에 FK가 선언된 blog 테이블 먼저 삭제합니다.
DROP TABLE qna;
DROP TABLE reply;
DROP TABLE member;
-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)
DROP TABLE member CASCADE CONSTRAINTS; 

CREATE TABLE member(
		memberno  NUMERIC(10)    NOT NULL PRIMARY KEY,  -- 회원 번호, 레코드를 구분하는 컬럼 
		ID        VARCHAR(30)    NOT NULL UNIQUE,  -- 이메일(아이디), 중복 안됨, 레코드를 구분 
		passwd    VARCHAR(200)    NOT NULL,
		mname     VARCHAR(30)    NOT NULL,
		tel       VARCHAR(14)    NOT NULL,
		zipcode   VARCHAR(5)         NULL,
		address1  VARCHAR(80)        NULL,
		address2  VARCHAR(50)        NULL,
		mdate     DATE           NOT NULL,
		grade     NUMERIC(2)     NOT NULL
);

COMMENT ON TABLE MEMBER is '회원';
COMMENT ON COLUMN MEMBER.MEMBERNO is '회원 번호';
COMMENT ON COLUMN MEMBER.ID is '아이디';
COMMENT ON COLUMN MEMBER.PASSWD is '패스워드';
COMMENT ON COLUMN MEMBER.MNAME is '성명';
COMMENT ON COLUMN MEMBER.TEL is '전화번호';
COMMENT ON COLUMN MEMBER.ZIPCODE is '우편번호';
COMMENT ON COLUMN MEMBER.ADDRESS1 is '주소1';
COMMENT ON COLUMN MEMBER.ADDRESS2 is '주소2';
COMMENT ON COLUMN MEMBER.MDATE is '가입일';
COMMENT ON COLUMN MEMBER.GRADE is '등급';

DROP SEQUENCE member_seq;

CREATE SEQUENCE member_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;  
  
  1. 등록
 
1) id 중복 확인(null 값을 가지고 있으면 count에서 제외됨)
SELECT COUNT(id)
FROM member
WHERE id='user1';

 COUNT(ID)
----------
         0

SELECT COUNT(id) as cnt
FROM member
WHERE id='user1';

       CNT
----------
         0
         
2) 등록
-- 회원 관리용 계정, Q/A 용 계정
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'sy', '1234', '통합 관리자', '000-0000-0000', '12345',
             '서울시 중랑구', '면목동', sysdate, 1);
             
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'uj', '1234', '질문답변관리자', '000-0000-0000', '12345',
             '서울시 광진구', '구의동', sysdate, 1);
             
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'admin', '1234', '통합 관리자', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 3);
 
-- 개인 회원 테스트 계정
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user1@gmail.com', '1234', '김예은', '000-0000-0000', '12345', '서울시 중랑구', '용마산', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user2@gmail.com', '1234', '김태연', '000-0000-0000', '12345', '인천광역시', '부평', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'user3@gmail.com', '1234', '허민경', '000-0000-0000', '12345', '서울시 강동구', '천호', sysdate, 15);
 
-- 부서별(그룹별) 공유 회원 기준
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team1', '1234', '개발팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team2', '1234', '웹퍼블리셔팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);
 
INSERT INTO member(memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'team3', '1234', '디자인팀', '000-0000-0000', '12345', '서울시 종로구', '관철동', sysdate, 15);

COMMIT;


2. 목록
- 검색을 하지 않는 경우, 전체 목록 출력
 
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
ORDER BY grade ASC, id ASC;
 MEMBERNO ID                 PASSWD     MNAME            TEL            ZIPCO ADDRESS1         ADDRESS2                                           MDATE                  GRADE
---------- ----------------- ---------- ---------------- -------------- ----- ---------------- -------------------------------------------------- ----------------- ----------
         1 sy                1234       통합 관리자        000-0000-0000  12345 서울시 중랑구       면목동                                             24/10/04 11:51:18          1
         2 uj                1234       질문답변관리자      000-0000-0000  12345 서울시 광진구       구의동                                             24/10/04 11:51:18          1
         3 user1@gmail.com   1234       김예은            000-0000-0000  12345 서울시 중랑구       용마산                                             24/10/04 11:56:38         15
         4 user2@gmail.com   1234       김태연            000-0000-0000  12345 인천광역시         부평                                               24/10/04 11:56:38         15
         5 user3@gmail.com   1234       허민경            000-0000-0000  12345 서울시 강동구       천호                                               24/10/04 11:56:39         15

3. 조회
 
1) 사원 정보 조회
SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE memberno = 1;

MEMBERNO ID                             PASSWD                                                                                                                                                                                                   MNAME                          TEL            ZIPCO ADDRESS1                                                                         ADDRESS2                                           MDATE                  GRADE
---------- ------------------------------ -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ------------------------------ -------------- ----- -------------------------------------------------------------------------------- -------------------------------------------------- ----------------- ----------
         1 sy                             1234                                                                                                                                                                                                     통합 관리자                    000-0000-0000  12345 서울시 중랑구                                                                    면목동                                             24/10/04 11:51:18          1


SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
WHERE id = 'user1@gmail.com';

MEMBERNO ID                             PASSWD                                                                                                                                                                                                   MNAME                          TEL            ZIPCO ADDRESS1                                                                         ADDRESS2                                           MDATE                  GRADE
---------- ------------------------------ -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ------------------------------ -------------- ----- -------------------------------------------------------------------------------- -------------------------------------------------- ----------------- ----------
         3 user1@gmail.com                1234                                                                                                                                                                                                     김예은                         000-0000-0000  12345 서울시 중랑구                                                                    용마산                                             24/10/04 11:56:38         15


4. 수정, PK: 변경 못함, UNIQUE: 변경을 권장하지 않음(id)
UPDATE member 
SET mname='김태연', tel='111-1111-1111', zipcode='00000',
    address1='인천', address2='부평시', grade=14
WHERE memberno=4;

COMMIT;

5. 삭제
1) 모두 삭제
DELETE FROM member;
 
2) 특정 회원 삭제
DELETE FROM member
WHERE memberno!=16;

COMMIT;

6. 로그인
SELECT COUNT(memberno) as cnt
FROM member
WHERE id='user1@gmail.com' AND passwd='1234';

       CNT
----------
         1

7. 패스워드 변경
1) 패스워드 검사
SELECT COUNT(memberno) as cnt
FROM member
WHERE memberno=1 AND passwd='1234';
 
2) 패스워드 수정
UPDATE member
SET passwd='0000'
WHERE memberno=1;

COMMIT;

-- 8.관리자계정 생성 암호화 버전

INSERT INTO member(memberno, id, passwd, mname, tel, zipcode,
                                address1, address2, mdate, grade)
VALUES (member_seq.nextval, 'admin', 'fS/kjO+fuEKk06Zl7VYMhg==', '통합 관리자', '000-0000-0000', '12345',
             '서울시 종로구', '관철동', sysdate, 1);

COMMIT;

SELECT memberno, id, passwd, mname, tel, zipcode, address1, address2, mdate, grade
FROM member
ORDER BY grade ASC, id ASC;