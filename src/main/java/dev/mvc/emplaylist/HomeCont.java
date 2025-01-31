package dev.mvc.emplaylist;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mvc.songcate.SongcateVOMenu;
import dev.mvc.tool.Security;
import dev.mvc.songcate.SongcateProcInter;
import dev.mvc.songcate.SongcateVO;

@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.songcate.SongcateProc")
  private SongcateProcInter songcateProc; // CateProc class 객체가 생성되어 할당
  
  @Autowired
  private Security security;
  
  public HomeCont() {
    System.out.println("-> HomeCnont created.");
  }
  
  // http://localhost:9092
  // http://localhost:9092/index.do
  @GetMapping(value={"/", "/index.do"}) 
  public String home(Model model) {
//    ArrayList<SongcateVO> menu = this.songcateProc.list_all_songcategrp_y();
//    model.addAttribute("menu", menu);
    
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);
    
    return "index";  // /templates/index.html
  }
  
}
