/**********************************/
/* Table Name: 노래 카테고리 */
/**********************************/

DROP TABLE songcate;
DROP SEQUENCE songcate_SEQ;
-- 제약 조건과 함께 삭제(제약 조건이 있어도 삭제됨, 권장하지 않음.)
DROP TABLE songcate CASCADE CONSTRAINTS; 

CREATE TABLE songcate(
    SONGCATENO                            NUMBER(10)     NOT NULL     PRIMARY KEY,
    GENRE                             VARCHAR2(20)  NOT NULL,  
    ARTIST                            VARCHAR2(70)		 NOT NULL,
    CNT                               NUMBER(7)     DEFAULT 0     NOT NULL,
    SEQNO                             NUMBER(5)     DEFAULT 1     NOT NULL,
    VISIBLE                           CHAR(1)      DEFAULT 'N'    NOT NULL,
    RDATE                             DATE          NOT NULL
);

CREATE SEQUENCE songcate_SEQ
START WITH 1         -- 시작 번호
INCREMENT BY 1       -- 증가값
MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
CACHE 2              -- 2번은 메모리에서만 계산
NOCYCLE;             -- 다시 1부터 생성되는 것을 방지

COMMENT ON TABLE songcate is '노래';
COMMENT ON COLUMN songcate.SONGCATENO is '노래 번호';
COMMENT ON COLUMN songcate.GENRE is '장르';
COMMENT ON COLUMN songcate.ARTIST is '가수';
COMMENT ON COLUMN songcate.CNT is '관련 자료수';
COMMENT ON COLUMN songcate.SEQNO is '출력 순서';
COMMENT ON COLUMN songcate.VISIBLE is '출력 모드';
COMMENT ON COLUMN songcate.RDATE is '등록일';

COMMIT;
-- CRUD
-- 등록
INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, 'Rock', 'WOODZ', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '알앤비','40',0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '발라드', '태연', 0, 0, 'Y', sysdate);

commit;

-- 목록
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate ORDER BY songcateno ASC;
SONGCATENO GENRE                ARTIST                                                                        CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------- ---------- ---------- - -----------------
         1 Rock                 WOODZ                                                                           0          0 Y 24/10/31 05:48:44
         2 알앤비               40                                                                              0          0 Y 24/10/31 05:48:44
         3 알앤비               태연                                                                            0          0 Y 24/10/31 05:48:44

-- 조회
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE songcateno=1;
SONGCATENO GENRE                ARTIST                                                                        CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------- ---------- ---------- - -----------------
         1 Rock                 WOODZ                                                                           0          0 Y 24/10/31 05:48:44

-- 수정
UPDATE songcate SET genre='댄스', name = '신나', cnt=10, seqno=20, visible='Y', rdate=sysdate WHERE songcateno=3; 

UPDATE songcate SET genre='댄스' WHERE songcateno=1;

commit;

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE songcateno=3;
    SONGNO GENRE                NAME                                  CNT      SEQNO V RDATE            
---------- -------------------- ------------------------------ ---------- ---------- - -----------------
         3 댄스                 신나                                   10         20 Y 24/09/10 05:50:09
         
-- 삭제
DELETE FROM songcate WHERE songcateno=3;
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate ORDER BY songcateno ASC;
     SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         1 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          0 Y 24/09/13 10:22:59
         2 알앤비               듣는 편지                                                                                            40                                                                     듣는 편지                                                              13/01/25 12:00:00 40                                                                                                            0          0 Y 24/09/13 10:23:36

SELECT * FROM songcate;

COMMIT;

-- ----------------------------------------------------------------------------
-- emplaylsit2
-- ----------------------------------------------------------------------------

-- 데이터 삭제

DELETE FROM songcate;
COMMIT;

-- 데이터 준비
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate ORDER BY songcateno ASC;
INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, 'Rock', '--', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '알앤비', '--', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '발라드','--', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '인디','--', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, 'POP','--', 0, 0, 'Y', sysdate);

INSERT INTO songcate(songcateno, genre, artist, cnt, seqno, visible, rdate) 
VALUES(songcate_seq.nextval, '댄스','--', 0, 0, 'Y', sysdate);

-- 목록 변경됨
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         4 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          0 Y 24/09/13 10:35:42
         5 알앤비               듣는 편지                                                                                            40                                                                     듣는 편지                                                              13/01/25 12:00:00 40                                                                                                            0          0 Y 24/09/13 10:35:42
         6 알앤비               Siren                                                                                                태연                                                                   INVU                                                                   22/02/14 12:00:00 Mike Daley                                                                                                    0          0 Y 24/09/13 10:35:42

-- 출력 우선순위 낮춤
UPDATE songcate SET seqno = seqno+10;
UPDATE songcate SET seqno = seqno+1 WHERE songcateno=4;
UPDATE songcate SET seqno = seqno+1 WHERE songcateno=5;
UPDATE songcate SET seqno = seqno+1 WHERE songcateno=6;
SONGCATENO GENRE                NAME                                  CNT      SEQNO V RDATE            
---------- -------------------- ------------------------------ ---------- ---------- - -----------------
        13 락                   Drowning                                0          1 Y 24/09/13 10:19:48
        14 알앤비               듣는편지                                0          2 Y 24/09/13 10:19:48
        15 발라드               사이렌                                  0          3 Y 24/09/13 10:19:48

-- 출력 우선순위 높임
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate ORDER BY songcateno ASC;
UPDATE songcate SET seqno = seqno-1 WHERE songcateno=13;
UPDATE songcate SET seqno = seqno-1 WHERE songcateno=14;
UPDATE songcate SET seqno = seqno-1 WHERE songcateno=15;
SONGCATENO GENRE                NAME                                  CNT      SEQNO V RDATE            
---------- -------------------- ------------------------------ ---------- ---------- - -----------------
        13 락                   Drowning                                0          3 Y 24/09/13 10:19:48
        14 알앤비               듣는편지                                0          2 Y 24/09/13 10:19:48
        15 발라드               사이렌                                  0          1 Y 24/09/13 10:19:48

COMMIT;

-- 카테고리 공개 설정
UPDATE songcate SET visible='Y' WHERE songcateno=1;

-- 카테고리 비공개 설정
UPDATE songcate SET visible='N' WHERE songcateno=9;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         4 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          1 Y 24/09/13 10:35:42
         5 알앤비               듣는 편지                                                                                            40                                                                     듣는 편지                                                              13/01/25 12:00:00 40                                                                                                            0          1 Y 24/09/13 10:35:42
         6 알앤비               Siren                                                                                                태연                                                                   INVU                                                                   22/02/14 12:00:00 Mike Daley                                                                                                    0          1 N 24/09/13 10:35:42

COMMIT;

-- -----------------------------------------------------------------------------
-- 회원/비회원에게 공개할 카테고리 그룹(대분류) 목록
-- -----------------------------------------------------------------------------
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate ORDER BY songcateno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         1 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/13 10:48:08
         2 알앤비               듣는 편지                                                                                            40                                                                     듣는 편지                                                              13/01/25 12:00:00 40                                                                                                            0         13 N 24/09/13 10:48:08
         3 알앤비               Siren                                                                                                태연                                                                   INVU                                                                   22/02/14 12:00:00 Mike Daley                                                                                                    0         12 Y 24/09/13 10:48:08
         4 발라드               어린왕자                                                                                             려욱                                                                   The Little Prince                                                      16/01/28 12:00:00 빨간머리앤                                                                                                    0         14 N 24/09/13 11:45:11
         5 rock                 스물다섯, 스물하나                                                                                   자우림                                                                 Goodbye, grief.                                                        13/10/14 12:00:00 김윤아                                                                                                        0         14 Y 24/09/13 11:47:07
         6 rock                 낙화(落花)                                                                                           자우림                                                                 연인(戀人)                                                             98/11/01 12:00:00 김윤아                                                                                                        0         13 Y 24/09/13 11:49:40
         7 발라드               Blue                                                                                                 --                                                                     사계(Four Seasons)                                                     19/03/24 12:00:00 Alex Mood                                                                                                     0          1 Y 24/09/21 03:58:00
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10
         9 댄스                 Dreaming                                                                                             --                                                                     Universe                                                                                 Tim Deal                                                                                                      0          3 Y 24/09/21 04:02:57

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE artist='--' ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         7 발라드               Blue                                                                                                 --                                                                     사계(Four Seasons)                                                     19/03/24 12:00:00 Alex Mood                                                                                                     0          1 Y 24/09/21 03:58:00
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10
         9 댄스                 Dreaming                                                                                             --                                                                     Universe                                                                                 Tim Deal                                                                                                      0          3 Y 24/09/21 04:02:57

-- 숨긴 '카테고리 그룹'을 제외하고 접속자에게 공개할 '카테고리 그룹' 출력  
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE artist='--' AND visible='Y' ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         7 발라드               Blue                                                                                                 --                                                                     사계(Four Seasons)                                                     19/03/24 12:00:00 Alex Mood                                                                                                     0          1 Y 24/09/21 03:58:00
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10

-- -----------------------------------------------------------------------------
-- 회원/비회원에게 공개할 카테고리(중분류) 목록
-- -----------------------------------------------------------------------------
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE genre='Rock' ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10
         1 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/13 10:48:08
         6 Rock                 낙화(落花)                                                                                           자우림                                                                 연인(戀人)                                                             98/11/01 12:00:00 김윤아                                                                                                        0         13 Y 24/09/21 04:14:00
         5 Rock                 스물다섯, 스물하나                                                                                   자우림                                                                 Goodbye, grief.                                                        13/10/14 12:00:00 김윤아                                                                                                        0         14 Y 24/09/21 04:14:45

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE genre='Rock' AND visible='Y' ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10
         1 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/13 10:48:08
         5 Rock                 스물다섯, 스물하나                                                                                   자우림                                                                 Goodbye, grief.                                                        13/10/14 12:00:00 김윤아                                                                                                        0         14 Y 24/09/21 04:14:45

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE genre='Rock' AND visible='Y' AND composer='김윤아' ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         8 Rock                 샤이닝                                                                                               --                                                                     Ashes To Ashes                                                         06/10/20 12:00:00 김윤아                                                                                                        0          2 Y 24/09/21 04:00:10
         5 Rock                 스물다섯, 스물하나                                                                                   자우림                                                                 Goodbye, grief.                                                        13/10/14 12:00:00 김윤아                                                                                                        0         14 Y 24/09/21 04:14:45

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate FROM songcate WHERE genre='Rock' ORDER BY seqno ASC;

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate 
    FROM songcate 
    WHERE artist='--' AND genre='Rock' AND visible='Y' 
    ORDER BY seqno ASC;
    
COMMIT;

SELECT genre FROM songcate WHERE artist = '--' ORDER BY seqno ASC;
GENRE               
--------------------
Rock
알앤비
소울

-- 검색
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
FROM songcate
WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
        19 Rock                 Drowning                                                                                             --                                                                     OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          1 Y 24/09/19 05:46:38
         9 Rock                 예뻤어                                                                                               DAY6                                                                   Every DAY6 February                                                    17/02/06 12:00:00 Young K                                                                                                       0         11 Y 24/09/19 05:48:17
        11 Rock                 한페이지가 될 수 있게                                                                                DAY6                                                                   The Book of US : Gravity                                               19/07/15 12:00:00 Young K                                                                                                       0         12 Y 24/09/19 05:49:04
         8 Rock                 HAPPY                                                                                                DAY6                                                                   Fourever                                                               24/03/18 12:00:00 Young K                                                                                                       0         12 Y 24/09/19 05:48:44

-- 검색 갯수
SELECT COUNT(*) as cnt
FROM songcate
WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
ORDER BY seqno ASC;
       CNT
----------
         4

-- '카테고리 그룹'을 제외한 경우
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
FROM songcate
WHERE (artist != '--') AND (UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%')
ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
         9 Rock                 예뻤어                                                                                               DAY6                                                                   Every DAY6 February                                                    17/02/06 12:00:00 Young K                                                                                                       0         11 Y 24/09/19 05:48:17
        11 Rock                 한페이지가 될 수 있게                                                                                DAY6                                                                   The Book of US : Gravity                                               19/07/15 12:00:00 Young K                                                                                                       0         12 Y 24/09/19 05:49:04
         8 Rock                 HAPPY                                                                                                DAY6                                                                   Fourever                                                               24/03/18 12:00:00 Young K                                                                                                       0         12 Y 24/09/19 05:48:44

-- -----------------------------------------------------------------------------
-- 페이징 : 정렬 -> ROWNUM -> 분할
-- -----------------------------------------------------------------------------
-- 1. 정렬
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
FROM songcate
WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
ORDER BY seqno ASC;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE            
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - -----------------
        19 Rock                 Drowning                                                                                             --                                                                     OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          2 Y 24/09/19 05:46:38
         9 Rock                 예뻤어                                                                                               DAY6                                                                   Every DAY6 February                                                    17/02/06 12:00:00 Young K                                                                                                       0         10 N 24/09/24 05:51:14
        27 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/25 10:41:50
        11 Rock                 한페이지가 될 수 있게                                                                                DAY6                                                                   The Book of US : Gravity                                               19/07/15 12:00:00 Young K                                                                                                       0         14 N 24/09/19 05:49:04
         8 Rock                 HAPPY                                                                                                DAY6                                                                   Fourever                                                               24/03/18 12:00:00 Young K                                                                                                       0         15 Y 24/09/19 05:48:44
        32 Rock                 Broken Melodies                                                                                      NCT DREAM                                                              ISTJ - The 3rd Album                                                   23/07/17 12:00:00 Anne Judith Wik                                                                                               0         16 N 24/09/25 10:55:56

-- 2. 정렬 -> rownum
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate, rownum as r
FROM(
    SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
    FROM songcate
    WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
    ORDER BY seqno ASC
);
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE                      R
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - ----------------- ----------
        19 Rock                 Drowning                                                                                             --                                                                     OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          2 Y 24/09/19 05:46:38          1
         9 Rock                 예뻤어                                                                                               DAY6                                                                   Every DAY6 February                                                    17/02/06 12:00:00 Young K                                                                                                       0         10 N 24/09/24 05:51:14          2
        27 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/25 10:41:50          3
        11 Rock                 한페이지가 될 수 있게                                                                                DAY6                                                                   The Book of US : Gravity                                               19/07/15 12:00:00 Young K                                                                                                       0         14 N 24/09/19 05:49:04          4
         8 Rock                 HAPPY                                                                                                DAY6                                                                   Fourever                                                               24/03/18 12:00:00 Young K                                                                                                       0         15 Y 24/09/19 05:48:44          5
        32 Rock                 Broken Melodies                                                                                      NCT DREAM                                                              ISTJ - The 3rd Album                                                   23/07/17 12:00:00 Anne Judith Wik                                                                                               0         16 N 24/09/25 10:55:56          6

-- 3. 분할
SELECT songcateno, genre, artist, cnt, seqno, visible, rdate, r
FROM(
    SELECT songcateno, genre, artist, cnt, seqno, visible, rdate, rownum as r
    FROM(
        SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
        FROM songcate
        WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
        ORDER BY seqno ASC
    )
)
WHERE r >= 1 AND r <= 3;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE                      R
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - ----------------- ----------
        19 Rock                 Drowning                                                                                             --                                                                     OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0          2 Y 24/09/19 05:46:38          1
         9 Rock                 예뻤어                                                                                               DAY6                                                                   Every DAY6 February                                                    17/02/06 12:00:00 Young K                                                                                                       0         10 N 24/09/24 05:51:14          2
        27 Rock                 Drowning                                                                                             WOODZ                                                                  OO-LI                                                                  23/04/26 12:00:00 WOODZ                                                                                                         0         12 Y 24/09/25 10:41:50          3

SELECT songcateno, genre, artist, cnt, seqno, visible, rdate, r
FROM(
    SELECT songcateno, genre, artist, cnt, seqno, visible, rdate, rownum as r
    FROM(
        SELECT songcateno, genre, artist, cnt, seqno, visible, rdate
        FROM songcate
        WHERE UPPER(genre) LIKE '%' || UPPER('Rock') || '%' OR UPPER(artist) LIKE '%' || UPPER('Rock') || '%'
        ORDER BY seqno ASC
    )
)
WHERE r >= 4 AND r <= 6;
SONGCATENO GENRE                SONGTITLE                                                                                            ARTIST                                                                 ALBUM                                                                  RELEASEDATE       COMPOSER                                                                                                    CNT      SEQNO V RDATE                      R
---------- -------------------- ---------------------------------------------------------------------------------------------------- ---------------------------------------------------------------------- ---------------------------------------------------------------------- ----------------- ---------------------------------------------------------------------------------------------------- ---------- ---------- - ----------------- ----------
        11 Rock                 한페이지가 될 수 있게                                                                                DAY6                                                                   The Book of US : Gravity                                               19/07/15 12:00:00 Young K                                                                                                       0         14 N 24/09/19 05:49:04          4
         8 Rock                 HAPPY                                                                                                DAY6                                                                   Fourever                                                               24/03/18 12:00:00 Young K                                                                                                       0         15 Y 24/09/19 05:48:44          5
        32 Rock                 Broken Melodies                                                                                      NCT DREAM                                                              ISTJ - The 3rd Album                                                   23/07/17 12:00:00 Anne Judith Wik                                                                                               0         16 N 24/09/25 10:55:56          6

COMMIT;





















