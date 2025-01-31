-- /src/main/webapp/WEB-INF/doc/게시글/posts_c.sql
DROP TABLE PLIST CASCADE CONSTRAINTS; -- 자식 무시하고 삭제 가능
DROP TABLE PLIST;

/**********************************/
/* Table Name: 게시글 */
/**********************************/
CREATE TABLE PLIST(
		PLISTNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		memberno                      		NUMBER(10)		     NULL,
		SONGCATENO                    		NUMBER(10)		 NOT NULL,
		TITLE                         		VARCHAR2(100)	 NOT NULL,
		POST                       		CLOB		     NOT NULL,
		RECOM                         		NUMBER(7)		 DEFAULT 0  NOT NULL,
		CNT                           		NUMBER(7)		 DEFAULT 0  NOT NULL,
		REPLYCNT                      		NUMBER(7)		 DEFAULT 0  NOT NULL,
		PASSWD                        		VARCHAR2(100)	 NOT NULL,
		WORD                          		VARCHAR2(200)		 NULL,
		RDATE                         		DATE		     NOT NULL,
		FILE1                         		VARCHAR2(100)		 NULL,
		FILE1SAVED                    		VARCHAR2(100)		 NULL,
		THUMB1                        		VARCHAR2(100)		 NULL,
		SIZE1                         		NUMBER(10)		     NULL,
		MAP                           		VARCHAR2(1000)		 NULL,
		YOUTUBE                       		VARCHAR2(1000)		 NULL,
		VISIBLE                       		CHAR(1)		     DEFAULT 'Y' NOT NULL,
  FOREIGN KEY (SONGCATENO) REFERENCES SONGCATE (SONGCATENO),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
);

ALTER TABLE PLIST
MODIFY YOUTUBE CLOB;

COMMENT ON TABLE PLIST is '컨텐츠';
COMMENT ON COLUMN PLIST.PLISTNO is '플레이리스트 번호';
COMMENT ON COLUMN PLIST.memberno is '회원번호';
COMMENT ON COLUMN PLIST.SONGCATENO is '카테고리 번호';
COMMENT ON COLUMN PLIST.TITLE is '제목';
COMMENT ON COLUMN PLIST.POST is '내용';
COMMENT ON COLUMN PLIST.RECOM is '추천수';
COMMENT ON COLUMN PLIST.CNT is '조회수';
COMMENT ON COLUMN PLIST.REPLYCNT is '댓글수';
COMMENT ON COLUMN PLIST.PASSWD is '패스워드';
COMMENT ON COLUMN PLIST.WORD is '검색어';
COMMENT ON COLUMN PLIST.RDATE is '등록일';
COMMENT ON COLUMN PLIST.FILE1 is '메인 이미지';
COMMENT ON COLUMN PLIST.FILE1SAVED is '실제 저장된 메인 이미지';
COMMENT ON COLUMN PLIST.THUMB1 is '메인 이미지 Preview';
COMMENT ON COLUMN PLIST.SIZE1 is '메인 이미지 크기';
COMMENT ON COLUMN PLIST.MAP is '지도';
COMMENT ON COLUMN PLIST.YOUTUBE is 'Youtube 영상';
COMMENT ON COLUMN PLIST.VISIBLE is '출력 모드';

DROP SEQUENCE plist_seq;

CREATE SEQUENCE plist_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지

-- 등록 화면 유형 1: 커뮤니티(공지사항, 게시판, 자료실, 갤러리,  Q/A...)글 등록
INSERT INTO plist(plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(plist_seq.nextval, 17, 23, '댄스곡 추천', '우기의 FREAK', 0, 0, 0, '123',
       '댄스', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);

-- 유형 2 카테고리별 목록
INSERT INTO plist(plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(plist_seq.nextval, 17, 26, '알앤비 추천', '듣는편지 조음', 0, 0, 0, '123',
       '알앤비', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);
            
INSERT INTO plist(plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, 
                     word, rdate, file1, file1saved, thumb1, size1)
VALUES(plist_seq.nextval,17, 29, 'POP 추천곡', '팝송 듣기', 0, 0, 0, '123',
       'POP', sysdate, 'space.jpg', 'space_1.jpg', 'space_t.jpg', 1000);

COMMIT;

-- 전체 목록
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
ORDER BY plistno DESC;
PLISTNO   MEMBERNO SONGCATENO TITLE                                                                                                POST                                                                                  RECOM        CNT   REPLYCNT PASSWD                                                                                               WORD                                                                                                                                                                                                     RDATE             FILE1                                                                                                FILE1SAVED                                                                                           THUMB1                                                                                                    SIZE1 MAP                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      YOUTUBE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
---------- ---------- ---------- ---------------------------------------------------------------------------------------------------- -------------------------------------------------------------------------------- ---------- ---------- ---------- ---------------------------------------------------------------------------------------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------- ---------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
         3         17         29 POP 추천곡                                                                                           팝송 듣기                                                                                 0          0          0 123                                                                                                  POP                                                                                                                                                                                                      24/10/18 12:04:54 space.jpg                                                                                            space_1.jpg                                                                                          space_t.jpg                                                                                                1000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
         2         17         26 알앤비 추천                                                                                          듣는편지 조음                                                                             0          0          0 123                                                                                                  알앤비                                                                                                                                                                                                   24/10/18 12:04:54 space.jpg                                                                                            space_1.jpg                                                                                          space_t.jpg                                                                                                1000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
         1         17         23 댄스곡 추천                                                                                          우기의 FREAK                                                                              0          0          0 123                                                                                                  댄스                                                                                                                                                                                                     24/10/18 11:33:25 space.jpg                                                                                            space_1.jpg                                                                                          space_t.jpg                                                                                                1000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  


-- 23번 songcateno 만 출력
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
        LOWER(file1) as file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=23
ORDER BY plistno DESC;
 PLISTNO   MEMBERNO SONGCATENO TITLE                                                                                                POST                                                                                  RECOM        CNT   REPLYCNT PASSWD                                                                                               WORD                                                                                                                                                                                                     RDATE             FILE1                                                                                                FILE1SAVED                                                                                           THUMB1                                                                                                    SIZE1 MAP                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      YOUTUBE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
---------- ---------- ---------- ---------------------------------------------------------------------------------------------------- -------------------------------------------------------------------------------- ---------- ---------- ---------- ---------------------------------------------------------------------------------------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------------------------------------- ---------- ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
         1         17         23 댄스곡 추천                                                                                          우기의 FREAK                                                                              0          0          0 123                                                                                                  댄스                                                                                                                                                                                                     24/10/18 11:33:25 space.jpg                                                                                            space_1.jpg                                                                                          space_t.jpg                                                                                                1000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  


-- 2번 cateno 만 출력
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
        LOWER(file1) as file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=2
ORDER BY plistno ASC;

-- 3번 cateno 만 출력
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
        LOWER(file1) as file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=5
ORDER BY plistno ASC;

commit;

-- 모든 레코드 삭제
DELETE FROM plist;
commit;

-- 삭제
DELETE FROM plist
WHERE songcateno = 11;
commit;

SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
ORDER BY plistno DESC;

DELETE FROM plist
WHERE songcateno=12 AND plistno <= 41;

commit;


-- ----------------------------------------------------------------------------------------------------
-- 검색, cateno별 검색 목록
-- ----------------------------------------------------------------------------------------------------
-- 모든글
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, word, rdate,
       file1, file1saved, thumb1, size1, map, youtube
FROM plist
ORDER BY plistno ASC;

-- 카테고리별 목록
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, word, rdate,
       file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=2
ORDER BY plistno ASC;

-- 1) 검색
-- ① cateno별 검색 목록
-- word 컬럼의 존재 이유: 검색 정확도를 높이기 위하여 중요 단어를 명시
-- 글에 'swiss'라는 단어만 등장하면 한글로 '스위스'는 검색 안됨.
-- 이런 문제를 방지하기위해 'swiss,스위스,스의스,수의스,유럽' 검색어가 들어간 word 컬럼을 추가함.
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=8 AND word LIKE '%부대찌게%'
ORDER BY plistno DESC;

-- title, post, word column search
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=8 AND (title LIKE '%부대찌게%' OR post LIKE '%부대찌게%' OR word LIKE '%부대찌게%')
ORDER BY plistno DESC;

-- ② 검색 레코드 갯수
-- 전체 레코드 갯수, 집계 함수
SELECT COUNT(*)
FROM plist
WHERE songcateno=8;

  COUNT(*)  <- 컬럼명
----------
         5
         
SELECT COUNT(*) as cnt -- 함수 사용시는 컬럼 별명을 선언하는 것을 권장
FROM plist
WHERE songcateno=8;

       CNT <- 컬럼명
----------
         5

-- cateno 별 검색된 레코드 갯수
SELECT COUNT(*) as cnt
FROM plist
WHERE songcateno=8 AND word LIKE '%부대찌게%';

SELECT COUNT(*) as cnt
FROM plist
WHERE songcateno=8 AND (title LIKE '%부대찌게%' OR post LIKE '%부대찌게%' OR word LIKE '%부대찌게%');

-- SUBSTR(컬럼명, 시작 index(1부터 시작), 길이), 부분 문자열 추출
SELECT plistno, SUBSTR(title, 1, 4) as title
FROM plist
WHERE songcateno=8 AND (post LIKE '%부대%');

-- SQL은 대소문자를 구분하지 않으나 WHERE문에 명시하는 값은 대소문자를 구분하여 검색
SELECT plistno, title, word
FROM plist
WHERE songcateno=8 AND (word LIKE '%FOOD%');

SELECT plistno, title, word
FROM plist
WHERE songcateno=8 AND (word LIKE '%food%'); 

SELECT plistno, title, word
FROM plist
WHERE songcateno=8 AND (LOWER(word) LIKE '%food%'); -- 대소문자를 일치 시켜서 검색

SELECT plistno, title, word
FROM plist
WHERE songcateno=8 AND (UPPER(word) LIKE '%' || UPPER('FOOD') || '%'); -- 대소문자를 일치 시켜서 검색 ★

SELECT plistno, title, word
FROM plist
WHERE songcateno=8 AND (LOWER(word) LIKE '%' || LOWER('Food') || '%'); -- 대소문자를 일치 시켜서 검색

SELECT plistno || '. ' || title || ' 태그: ' || word as title -- 컬럼의 결합, ||
FROM plist
WHERE songcateno=8 AND (LOWER(word) LIKE '%' || LOWER('Food') || '%'); -- 대소문자를 일치 시켜서 검색


SELECT UPPER('한글') FROM dual; -- dual: 오라클에서 SQL 형식을 맞추기위한 시스템 테이블

-- ----------------------------------------------------------------------------------------------------
-- 검색 + 페이징 + 메인 이미지
-- ----------------------------------------------------------------------------------------------------
-- step 1
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE songcateno=1 AND (title LIKE '%단풍%' OR post LIKE '%단풍%' OR word LIKE '%단풍%')
ORDER BY plistno DESC;

-- step 2
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, rownum as r
FROM (
          SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                     file1, file1saved, thumb1, size1, map, youtube
          FROM plist
          WHERE songcateno=1 AND (title LIKE '%단풍%' OR post LIKE '%단풍%' OR word LIKE '%단풍%')
          ORDER BY plistno DESC
);

-- step 3, 1 page
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1 AND (title LIKE '%단풍%' OR post LIKE '%단풍%' OR word LIKE '%단풍%')
                     ORDER BY plistno DESC
           )          
)
WHERE r >= 1 AND r <= 3;

-- step 3, 2 page
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1 AND (title LIKE '%단풍%' OR post LIKE '%단풍%' OR word LIKE '%단풍%')
                     ORDER BY plistno DESC
           )          
)
WHERE r >= 4 AND r <= 6;

-- 대소문자를 처리하는 페이징 쿼리
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1 AND (UPPER(title) LIKE '%' || UPPER('단풍') || '%' 
                                         OR UPPER(post) LIKE '%' || UPPER('단풍') || '%' 
                                         OR UPPER(word) LIKE '%' || UPPER('단풍') || '%')
                     ORDER BY plistno DESC
           )          
)
WHERE r >= 1 AND r <= 3;

-- ----------------------------------------------------------------------------
-- 조회
-- ----------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, passwd, word, rdate,
           file1, file1saved, thumb1, size1, map, youtube
FROM plist
WHERE plistno = 1;

-- ----------------------------------------------------------------------------
-- 다음 지도, MAP, 먼저 레코드가 등록되어 있어야함.
-- map                                   VARCHAR2(1000)         NULL ,
-- ----------------------------------------------------------------------------
-- MAP 등록/수정
UPDATE plist SET map='카페산 지도 스크립트' WHERE plistno=1;

-- MAP 삭제
UPDATE plist SET map='' WHERE plistno=1;

commit;

-- ----------------------------------------------------------------------------
-- Youtube, 먼저 레코드가 등록되어 있어야함.
-- youtube                                   VARCHAR2(1000)         NULL ,
-- ----------------------------------------------------------------------------
-- youtube 등록/수정
UPDATE plist SET youtube='Youtube 스크립트' WHERE plistno=1;

-- youtube 삭제
UPDATE plist SET youtube='' WHERE plistno=1;

commit;

-- 패스워드 검사, id="password_check"
SELECT COUNT(*) as cnt 
FROM plist
WHERE plistno=1 AND passwd='123';

-- 텍스트 수정: 예외 컬럼: 추천수, 조회수, 댓글 수
UPDATE plist
SET title='기차를 타고', post='계획없이 여행 출발',  word='나,기차,생각' 
WHERE plistno = 2;

-- ERROR, " 사용 에러
UPDATE plist
SET title='기차를 타고', post="계획없이 '여행' 출발",  word='나,기차,생각'
WHERE plistno = 1;

-- ERROR, \' 에러
UPDATE plist
SET title='기차를 타고', post='계획없이 \'여행\' 출발',  word='나,기차,생각'
WHERE plistno = 1;

-- SUCCESS, '' 한번 ' 출력됨.
UPDATE plist
SET title='기차를 타고', post='계획없이 ''여행'' 출발',  word='나,기차,생각'
WHERE plistno = 1;

-- SUCCESS
UPDATE plist
SET title='기차를 타고', post='계획없이 "여행" 출발',  word='나,기차,생각'
WHERE plistno = 1;

commit;

-- 파일 수정
UPDATE plist
SET file1='train.jpg', file1saved='train.jpg', thumb1='train_t.jpg', size1=5000
WHERE plistno = 10;

-- 삭제
DELETE FROM plist
WHERE plistno = 42;

commit;

DELETE FROM plist
WHERE plistno >= 7;

commit;

-- 추천
UPDATE plist
SET recom = recom + 1
WHERE plistno = 1;

-- cateno FK 특정 그룹에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM plist 
WHERE songcateno=1;

-- memberno FK 특정 관리자에 속한 레코드 갯수 산출
SELECT COUNT(*) as cnt 
FROM plist 
WHERE memberno=1;

-- cateno FK 특정 그룹에 속한 레코드 모두 삭제
DELETE FROM plist
WHERE songcateno=1;

-- memberno FK 특정 관리자에 속한 레코드 모두 삭제
DELETE FROM plist
WHERE memberno=1;

commit;

-- 다수의 카테고리에 속한 레코드 갯수 산출: IN
SELECT COUNT(*) as cnt
FROM plist
WHERE songcateno IN(1,2,3);

-- 다수의 카테고리에 속한 레코드 모두 삭제: IN
SELECT plistno, memberno, songcateno, title
FROM plist
WHERE songcateno IN(1,2,3);

postSNO    ADMINNO     CATENO TITLE                                                                                                                                                                                                                                                                                                       
---------- ---------- ---------- ------------------------
         3             1                   1           인터스텔라                                                                                                                                                                                                                                                                                                  
         4             1                   2           드라마                                                                                                                                                                                                                                                                                                      
         5             1                   3           컨저링                                                                                                                                                                                                                                                                                                      
         6             1                   1           마션       
         
SELECT plistno, memberno, songcateno, title
FROM plist
WHERE songcateno IN('1','2','3');

postSNO    ADMINNO     CATENO TITLE                                                                                                                                                                                                                                                                                                       
---------- ---------- ---------- ------------------------
         3             1                   1           인터스텔라                                                                                                                                                                                                                                                                                                  
         4             1                   2           드라마                                                                                                                                                                                                                                                                                                      
         5             1                   3           컨저링                                                                                                                                                                                                                                                                                                      
         6             1                   1           마션       

-- ----------------------------------------------------------------------------------------------------
-- cate + posts INNER JOIN
-- ----------------------------------------------------------------------------------------------------
-- 모든글
SELECT c.name,
       t.plistno, t.memberno, t.songcateno, t.title, t.post, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube
FROM cate c, plist t
WHERE c.songcateno = t.songcateno
ORDER BY t.plistno DESC;

-- posts, member INNER JOIN
SELECT t.plistno, t.memberno, t.songcateno, t.title, t.post, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube,
       a.mname
FROM member a, plist t
WHERE a.memberno = t.memberno
ORDER BY t.plistno DESC;

SELECT t.plistno, t.memberno, t.songcateno, t.title, t.post, t.recom, t.cnt, t.replycnt, t.word, t.rdate,
       t.file1, t.file1saved, t.thumb1, t.size1, t.map, t.youtube,
       a.mname
FROM member a INNER JOIN plist t ON a.memberno = t.memberno
ORDER BY t.plistno DESC;

-- ----------------------------------------------------------------------------------------------------
-- View + paging
-- ----------------------------------------------------------------------------------------------------
CREATE OR REPLACE VIEW vposts
AS
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, word, rdate,
        file1, file1saved, thumb1, size1, map, youtube
FROM plist
ORDER BY plistno DESC;
                     
-- 1 page
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
       file1, file1saved, thumb1, size1, map, youtube, r
FROM (
     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
            file1, file1saved, thumb1, size1, map, youtube, rownum as r
     FROM vposts -- View
     WHERE songcateno=14 AND (title LIKE '%야경%' OR post LIKE '%야경%' OR word LIKE '%야경%')
)
WHERE r >= 1 AND r <= 3;

-- 2 page
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
       file1, file1saved, thumb1, size1, map, youtube, r
FROM (
     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
            file1, file1saved, thumb1, size1, map, youtube, rownum as r
     FROM vposts -- View
     WHERE songcateno=14 AND (title LIKE '%야경%' OR post LIKE '%야경%' OR word LIKE '%야경%')
)
WHERE r >= 4 AND r <= 6;


-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 좋아요(recom) 기준, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, thumb1, r
FROM (
           SELECT plistno, memberno, songcateno, title, thumb1, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, thumb1
                     FROM posts
                     WHERE songcateno=1
                     ORDER BY recom DESC
           )          
)
WHERE r >= 1 AND r <= 7;

-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 평점(score) 기준, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1
                     ORDER BY score DESC
           )          
)
WHERE r >= 1 AND r <= 7;

-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 최신 상품 기준, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1
                     ORDER BY rdate DESC
           )          
)
WHERE r >= 1 AND r <= 7;

-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 조회수 높은 상품기준, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1
                     ORDER BY cnt DESC
           )          
)
WHERE r >= 1 AND r <= 7;

-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 낮은 가격 상품 추천, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1
                     ORDER BY price ASC
           )          
)
WHERE r >= 1 AND r <= 7;

-- ----------------------------------------------------------------------------------------------------
-- 관심 카테고리의 높은 가격 상품 추천, 1번 회원이 1번 카테고리를 추천 받는 경우, 추천 상품이 7건일 경우
-- ----------------------------------------------------------------------------------------------------
SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
           file1, file1saved, thumb1, size1, map, youtube, r
FROM (
           SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                      file1, file1saved, thumb1, size1, map, youtube, rownum as r
           FROM (
                     SELECT plistno, memberno, songcateno, title, post, recom, cnt, replycnt, rdate,
                                file1, file1saved, thumb1, size1, map, youtube
                     FROM plist
                     WHERE songcateno=1
                     ORDER BY price DESC
           )          
)
WHERE r >= 1 AND r <= 7;

-----------------------------------------------------------
-- FK cateno 컬럼에 대응하는 필수 SQL
-----------------------------------------------------------
-- 특정 카테고리에 속한 레코드 갯수를 리턴
SELECT COUNT(*) as cnt 
FROM plist 
WHERE songcateno=1;
  
-- 특정 카테고리에 속한 모든 레코드 삭제
DELETE FROM plist
WHERE songcateno=1;

-----------------------------------------------------------
-- FK memberno 컬럼에 대응하는 필수 SQL
-----------------------------------------------------------
-- 특정 회원에 속한 레코드 갯수를 리턴
SELECT COUNT(*) as cnt 
FROM plist 
WHERE memberno=1;
  
-- 특정 회원에 속한 모든 레코드 삭제
DELETE FROM plist
WHERE memberno=1;

1) 댓글수 증가
UPDATE plist
SET replycnt = replycnt + 1
WHERE plistno = 3;

2) 댓글수 감소
UPDATE plist
SET replycnt = replycnt - 1
WHERE plistno = 3;   

commit;