package dev.mvc.plistgood;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//  CREATE TABLE plistgood (
//      goodno        NUMBER(10) NOT NULL PRIMARY KEY, -- AUTO_INCREMENT 대체
//      plistno      NUMBER(10)         NOT NULL,
//      rdate         DATE          NOT NULL, -- 등록 날짜
//      memberno      NUMBER(10)     NOT NULL , -- FK
//      FOREIGN KEY (memberno) REFERENCES member (memberno),-- 일정을 등록한 관리자 
//      FOREIGN KEY (plistno) REFERENCES plist (plistno) ON DELETE CASCADE
//    );

@Getter @Setter @ToString
public class PlistgoodVO {
  
  /** 추천 번호 */
  private int goodno;
  
  /** 플레이리스트 번호 */
  private int plistno;
  
  /** 회원 번호 */
  private int memberno;
  
  /** 추천 날짜 */
  private String rdate;
  
  

}
