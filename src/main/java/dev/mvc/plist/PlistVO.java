package dev.mvc.plist;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
        		PLISTNO                    		NUMBER(10)		NOT NULL		 PRIMARY KEY,
		memberno                      		NUMBER(10)		     NULL,
		SONGCATENO                    	NUMBER(10)		NOT NULL,
		TITLE                         		VARCHAR2(100)	 	NOT NULL,
		POST                       		CLOB		     	NOT NULL,
		RECOM                         		NUMBER(7)		NOT NULL,
		CNT                           		NUMBER(7)		NOT NULL,
		REPLYCNT                      		NUMBER(7)		NOT NULL,
		PASSWD                        		VARCHAR2(100)	 	NOT NULL,
		WORD                          		VARCHAR2(200)			NULL,
		RDATE                         		DATE		     	NOT NULL,
		FILE1                         		VARCHAR2(100)		 	NULL,
		FILE1SAVED                    		VARCHAR2(100)		 	NULL,
		THUMB1                        		VARCHAR2(100)		 	NULL,
		SIZE1                         		NUMBER(10)		     	NULL,
		MAP                           		VARCHAR2(1000)		 	NULL,
		YOUTUBE                       		VARCHAR2(1000)		 	NULL,
		VISIBLE                       		CHAR(1)		     	NOT NULL,
  FOREIGN KEY (SONGCATENO) REFERENCES SONGCATE (SONGCATENO),
  FOREIGN KEY (memberno) REFERENCES member (memberno)
 */

@Getter @Setter @ToString
public class PlistVO {
    /** 플레이리스트 번호 */
    private int plistno;
    /** 관리자 권한의 회원 번호 */
    private int memberno;
    /** 장르 번호 */
    private int songcateno;
    /** 제목 */
    private String title = "";
    /** 내용 */
    private String post = "";
    /** 추천수 */
    private int recom;
    /** 조회수 */
    private int cnt = 0;
    /** 댓글수 */
    private int replycnt = 0;
    /** 패스워드 */
    private String passwd = "";
    /** 검색어 */
    private String word = "";
    /** 등록 날짜 */
    private String rdate = "";
    /** 지도 */
    private String map = "";
    /** Youtube */
    private String youtube = "";
    
    // 파일 업로드 관련
    // -----------------------------------------------------------------------------------
    /**
    이미지 파일
    <input type='file' class="form-control" name='file1MF' id='file1MF' 
               value='' placeholder="파일 선택">
    */
    private MultipartFile file1MF = null;
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String size1_label = "";
    /** 메인 이미지 */
    private String file1 = "";
    /** 실제 저장된 메인 이미지 */
    private String file1saved = "";
    /** 메인 이미지 preview */
    private String thumb1 = "";
    /** 메인 이미지 크기 */
    private long size1 = 0;
    
  
}