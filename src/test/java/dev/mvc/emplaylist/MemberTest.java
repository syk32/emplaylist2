package dev.mvc.emplaylist;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.songcate.SongcateProcInter;
import dev.mvc.member.MemberProc;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;

@SpringBootTest
public class MemberTest {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  // 중복 id 체크
//  @Test
//  public void checkID() {
//    int cnt = this.memberProc.checkID("user1@gmail.com");
//    System.out.println("cnt: " + cnt);
//  }
  
  // 회원 가입 처리
  @Test
  public void create() {
    MemberVO memberVO = new MemberVO();
    memberVO.setId("user4@gmail.com");
    memberVO.setPasswd("1234");
    memberVO.setMname("왕눈이");
    memberVO.setTel("123-1234-1234");
    memberVO.setZipcode("12345");
    memberVO.setAddress1("서울시 종로구 종로 15길");
    memberVO.setAddress2("코아빌딩 5층");
    memberVO.setGrade(11);
    
    int cnt = this.memberProc.create(memberVO);
    System.out.println("cnt: " + cnt);
  }
  
}





