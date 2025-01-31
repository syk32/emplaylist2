package dev.mvc.songcate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE songcate(
//    SONGCATENO                            NUMBER(10)     NOT NULL     PRIMARY KEY,
//    GENRE                             VARCHAR2(20)  NOT NULL,  
//    ARTIST                            VARCHAR2(70)     NOT NULL,
//    CNT                               NUMBER(7)     DEFAULT 0     NOT NULL,
//    SEQNO                             NUMBER(5)     DEFAULT 1     NOT NULL,
//    VISIBLE                           CHAR(1)      DEFAULT 'N'    NOT NULL,
//    RDATE                             DATE          NOT NULL
//);

@Setter @Getter @ToString
public class SongcateVO {
  /** 노래 번호, Sequence에서 자동 생성 */  
  private Integer songcateno=0;

  /** 장르명 */
  @NotEmpty(message="장르명은 필수 항목입니다.")
  @Size(min=1, max=10, message="장르명은 최소 1자에서 최대 10자입니다.")
  private String genre;
  
  /** 가수 */
  @NotEmpty(message="가수 입력은 필수 항목입니다.")
  @Size(min=1, max=50, message="가수 입력은 최소 1자에서 최대 50자입니다.")
  private String artist;
  
  /** 관련 자료수 */
  @NotNull(message="관련 자료수는 필수 입력 항목입니다.")
  @Min(value=0)
  @Max(value=1000000)
  private Integer cnt=0;
  
  /** 출력 순서 */
  @NotNull(message="출력 순서는 필수 입력 항목입니다.")
  @Min(value=1)
  @Max(value=1000000)  
  private Integer seqno;
  
  /** 출력 모드 */
  @NotEmpty(message="출력 모드는 필수 항목입니다.")
  @Pattern(regexp="^[YN]$", message="Y 또는 N만 입력 가능합니다.")
  private String visible;
  
  /** 등록일, sysdate 자동 생성 */
  private String rdate = "";

//  @Override
//  public String toString() {
//    return "CateVO [cateno=" + cateno + ", name=" + name + ", cnt=" + cnt + ", seqno=" + seqno + ", visible=" + visible
//        + ", rdate=" + rdate + "]";
//  }
  
  // CateVO(cateno=1, name=캠핑, cnt=1, seqno=1, visible=N, rdate=2024-09-03 12:15:57)
  
  
}



