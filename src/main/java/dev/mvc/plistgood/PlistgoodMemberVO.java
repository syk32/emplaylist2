package dev.mvc.plistgood;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PlistgoodMemberVO {
  
  /** 추천 번호 */
  public int goodno;
  
  /** 게시글 번호 */
  public int plistno;
  
  /** 등록 날짜 */
  public String rdate;
  
  /** 게시글 제목 */
  private String p_title = "";
  
  /** 회원 번호 */
  public int memberno;
  
  /** 회원 아이디 */
  private String id = "";
  
  /** 회원 이름 */
  private String mname="";

}
