package dev.mvc.emplaylist;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.member.MemberProcInter;
import dev.mvc.plist.PlistProcInter;
@SpringBootTest
public class PlistTest {
    
    @Autowired
    @Qualifier("dev.mvc.member.MemberProc")
    private MemberProcInter memberProc;
    
    @Autowired
    @Qualifier("dev.mvc.plist.PlistProc")
    private PlistProcInter plistProc;
    
    // 패스워드 체크
    @Test
    public void password_check() {
      HashMap<String, Object> hashMap = new HashMap<String, Object>();
      hashMap.put("plistno", 10);
//      hashMap.put("passwd", "fS/kjO+fuEKk06Zl7VYMhg==");
      hashMap.put("passwd", "1234");
      
      System.out.println("-> cnt: " + this.plistProc.password_check(hashMap));
      
    }

}
