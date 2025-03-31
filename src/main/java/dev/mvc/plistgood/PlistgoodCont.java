package dev.mvc.plistgood;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.plist.Plist;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/plistgood")
public class PlistgoodCont {
  @Autowired
  @Qualifier("dev.mvc.plistgood.PlistgoodProc")
  PlistgoodProcInter plistgoodProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") 
  private MemberProcInter memberProc;
  
  public PlistgoodCont() {
    System.out.println("-> PlistgoodCont created.");
  }
  
  /**
   * POST 전송
   * @return
   */
  @GetMapping(value = "/post2get")
  public String post2get(Model model, 
      @RequestParam(name="url", defaultValue = "") String url) {
    return url; 
  }
  
  /*
   * 생성
   */
  @PostMapping(value="/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody PlistgoodVO plistgoodVO) {
    System.out.println("-> 수신 데이터: " + plistgoodVO.toString());
    
    int memberno = (int)session.getAttribute("memberno");
    plistgoodVO.setMemberno(memberno);
    
    int cnt = this.plistgoodProc.create(plistgoodVO);
    
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    
    return json.toString();
  }
  
  /*
   * 목록
   */
  @GetMapping(value = "/list_all")
  public String list_search_paging(HttpSession session, Model model,
      @ModelAttribute("plistgoodVO") PlistgoodVO plistgoodVO,
      @RequestParam(name = "goodno", defaultValue = "0")int goodno,
      @RequestParam(name = "word", defaultValue = "")String word,
      @RequestParam(name = "now_page", defaultValue = "1")int now_page) {
    
    int record_per_page = 5;
    int start_row = (now_page - 1) * record_per_page + 1;
    int end_row = now_page * record_per_page;
    
    int memberno = (int)session.getAttribute("memberno");
    MemberVO memberVO = this.memberProc.read(memberno);
    if (memberVO == null) {
      memberVO = new MemberVO();
      memberVO.setMemberno(1);
      model.addAttribute("message", "회원 정보가 없습니다.");
    }
    
    word = Tool.checkNull(word).trim();
    model.addAttribute("memberVO", memberVO);
    model.addAttribute("goodno", goodno);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    HashMap<String, Object> map = new HashMap<>();
    map.put("word", word);
    map.put("now_page", now_page);
    map.put("start_row", start_row);
    map.put("end_row", end_row);
    
    ArrayList<PlistgoodMemberVO> list = this.plistgoodProc.list_all_join();
    model.addAttribute("list", list);
    
    int search_count = this.plistgoodProc.count_search(map);
    String paging = this.plistgoodProc.pagingBox(memberno, now_page, word, "/plistgood/list_all", search_count,
        Plist.RECORD_PER_PAGE, Plist.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);


    int no = search_count - ((now_page - 1) * Plist.RECORD_PER_PAGE);
    model.addAttribute("no", no);
    
    return "/plistgood/list_all";
  }
  
  /*
   * 삭제
   */
  @PostMapping(value="/delete")
  public String delete_proc(HttpSession session, Model model, RedirectAttributes ra,
      @RequestParam(name="goodno", defaultValue="0")int goodno) {
    
    if(this.memberProc.isMemberAdmin(session)) {
      this.plistgoodProc.delete(goodno);
      
      return "redirect:/plistgood/list_all";
    }else {
      ra.addAttribute("url", "/member/login_cookie_need");
      return "redirect:/plistgood/post2get";
    }
  }

}
