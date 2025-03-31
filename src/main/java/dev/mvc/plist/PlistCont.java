package dev.mvc.plist;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.songcate.SongcateProcInter;
import dev.mvc.songcate.SongcateVO;
import dev.mvc.songcate.SongcateVOMenu;
import dev.mvc.plist.PlistVO;
import dev.mvc.plistgood.PlistgoodProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@RequestMapping(value = "/plist")
@Controller
public class PlistCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.songcate.SongcateProc") // @Component("dev.mvc.songcate.songcateProc")
  private SongcateProcInter songcateProc;

  @Autowired
  @Qualifier("dev.mvc.plist.PlistProc") // @Component("dev.mvc.plist.plistProc")
  private PlistProcInter plistProc;
  
  @Autowired
  @Qualifier("dev.mvc.plistgood.PlistgoodProc")
  private PlistgoodProcInter plistgoodProc;

  public PlistCont() {
    System.out.println("-> PlistCont created.");
  }

  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   */
  @GetMapping(value = "/post2get")
  public String post2get(Model model, 
      @RequestParam(name="url", defaultValue="") String url) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    return url; // forward, /templates/...
  }

  /**
   * 등록
   */
  @GetMapping(value = "/create")
  public String create(Model model, 
      @ModelAttribute("plistVO") PlistVO plistVO, 
      @RequestParam(name="songcateno", defaultValue="0") int songcateno) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    SongcateVO songcateVO = this.songcateProc.read(songcateno); // create.jsp에 카테고리 정보를 출력하기위한 목적
    model.addAttribute("songcateVO", songcateVO);

    return "/plist/create"; // /templates/plist/create.html
  }

  /**
   * 등록 처리 
   */
  @PostMapping(value = "/create")
  public String create(HttpServletRequest request, HttpSession session, Model model, 
      @ModelAttribute("plistVO") PlistVO plistVO,
      RedirectAttributes ra) {

    if (memberProc.isMemberAdmin(session)) { // 관리자로 로그인한경우

      // 파일 전송 코드 시작
      String file1 = ""; // 원본 파일명 image
      String file1saved = ""; // 저장된 파일명, image
      String thumb1 = ""; // preview image

      String upDir = Plist.getUploadDir(); 

      MultipartFile mf = plistVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);

      long size1 = mf.getSize(); // 파일 크기
      if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150);
          }

          plistVO.setFile1(file1); // 순수 원본 파일명
          plistVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
          plistVO.setThumb1(thumb1); // 원본이미지 축소판
          plistVO.setSize1(size1); // 파일 크기

        } else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/plist/msg"); // msg.html, redirect parameter 적용
          return "redirect:/plist/msg"; // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      int memberno = (int) session.getAttribute("memberno"); // memberno FK
      plistVO.setMemberno(memberno);
      int cnt = this.plistProc.create(plistVO);

      if (cnt == 1) {
        ra.addAttribute("songcateno", plistVO.getSongcateno()); // controller -> controller: O
        return "redirect:/plist/list_by_songcateno";

      } else {
        ra.addFlashAttribute("code", "create_fail"); // DBMS 등록 실패
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/plist/msg"); // msg.html, redirect parameter 적용
        return "redirect:/plist/msg"; // Post -> Get - param...
      }
    } else { // 로그인 실패 한 경우
      return "redirect:/member/login_cookie_need"; // /member/login_cookie_need.html
    }
  }

  /**
   * 전체 목록, 관리자만 사용 가능 
   */
  @GetMapping(value = "/list_all")
  public String list_all(HttpSession session, Model model) {
    // System.out.println("-> list_all");
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    if (this.memberProc.isMemberAdmin(session)) {  // 관리자만 조회 가능
      ArrayList<PlistVO> list = this.plistProc.list_all();  // 모든 목록

      model.addAttribute("list", list);
      return "/plist/list_all";

    } else {
      return "redirect:/member/login_cookie_need";

    }

  }

  /**
   * 카테고리별 목록 + 검색 + 페이징 
   */
  @GetMapping(value = "/list_by_songcateno")
  public String list_by_songcateno_search_paging(HttpSession session, 
      Model model, 
      @RequestParam(name="songcateno", defaultValue="1") int songcateno,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    SongcateVO songcateVO = this.songcateProc.read(songcateno);
    model.addAttribute("songcateVO", songcateVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("songcateno", songcateno);
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<PlistVO> list = this.plistProc.list_by_songcateno_search_paging(map);
    model.addAttribute("list", list);

    model.addAttribute("word", word);

    int search_count = this.plistProc.list_by_songcateno_search_count(map);
    String paging = this.plistProc.pagingBox(songcateno, now_page, word, "/plist/list_by_songcateno", search_count,
        Plist.RECORD_PER_PAGE, Plist.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    int no = search_count - ((now_page - 1) * Plist.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    return "plist/list_by_songcateno_search_paging"; 
  }

  /**
   * 카테고리별 목록 + 검색 + 페이징 + Grid
   */
  @GetMapping(value = "/list_by_songcateno_grid")
  public String list_by_songcateno_search_paging_grid(HttpSession session, 
      Model model, 
      @RequestParam(name="songcateno", defaultValue="0") int songcateno,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    SongcateVO songcateVO = this.songcateProc.read(songcateno);
    model.addAttribute("songcateVO", songcateVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("songcateno", songcateno);
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<PlistVO> list = this.plistProc.list_by_songcateno_search_paging(map);
    model.addAttribute("list", list);

    model.addAttribute("word", word);

    int search_count = this.plistProc.list_by_songcateno_search_count(map);
    String paging = this.plistProc.pagingBox(songcateno, now_page, word, "/plist/list_by_songcateno", search_count,
        Plist.RECORD_PER_PAGE, Plist.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("search_count", search_count);

    int no = search_count - ((now_page - 1) * Plist.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    return "/plist/list_by_songcateno_search_paging_grid";
  }

  /**
   * 조회 
   */
  @GetMapping(value = "/read")
  public String read(HttpSession session, Model model, 
      @RequestParam(name="plistno", defaultValue="0") int plistno, 
      @RequestParam(name="word", defaultValue="") String word, 
      @RequestParam(name="now_page", defaultValue="1") int now_page) { 
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    PlistVO plistVO = this.plistProc.read(plistno);

    long size1 = plistVO.getSize1();
    String size1_label = Tool.unit(size1);
    plistVO.setSize1_label(size1_label);

    SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno());
    model.addAttribute("songcateVO", songcateVO);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("plistVO", plistVO);
    
    // 추천관련
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("plistno", plistno);
    
    int heartCnt = 0;
    if(session.getAttribute("memberno")!= null) {
      int memberno = (int)session.getAttribute("memberno");
      map.put("memberno", memberno);
      heartCnt = this.plistgoodProc.heartCnt(map);
    }
    model.addAttribute("heartCnt", heartCnt);
    
    return "plist/read";
  }
  
  /**
   * 맵 등록/수정/삭제 폼 
   */
  @GetMapping(value = "/map")
  public String map(Model model, 
                                @RequestParam(name="plistno", defaultValue="0") int plistno) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    PlistVO plistVO = this.plistProc.read(plistno); 
    model.addAttribute("plistVO", plistVO); 

    SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno()); 
    model.addAttribute("songcateVO", songcateVO);

    return "/plist/map";
  }

  /**
   * MAP 등록/수정/삭제 처리 
   */
  @PostMapping(value = "/map")
  public String map_update(Model model, 
      @RequestParam(name="plistno", defaultValue="0") int plistno, 
      @RequestParam(name="map", defaultValue="") String map) {
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("plistno", plistno);
    hashMap.put("map", map);

    this.plistProc.map(hashMap);

    return "redirect:/plist/read?plistno=" + plistno;
  }
  
  /**
   * Youtube 등록/수정/삭제 폼 
   */
  @GetMapping(value = "/youtube")
  public String youtube(Model model, 
      @RequestParam(name="plistno", defaultValue="0") int plistno, 
      @RequestParam(name="word", defaultValue="") String word, 
      @RequestParam(name="now_page", defaultValue="1") int now_page) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    PlistVO plistVO = this.plistProc.read(plistno); 
    model.addAttribute("plistVO", plistVO); 

    SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno()); 
    model.addAttribute("songcateVO", songcateVO);

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    return "plist/youtube";  
  }

  /**
   * Youtube 등록/수정/삭제 처리 
   */
  @PostMapping(value = "/youtube")
  public String youtube_update(Model model, 
                               RedirectAttributes ra,
                               @RequestParam(name="plistno", defaultValue="0") int plistno, 
                               @RequestParam(name="word", defaultValue="") String word,
                               @RequestParam(name="youtube", defaultValue="") String youtube,
                               @RequestParam(name="now_page", defaultValue="1") int now_page) {

    if (youtube.trim().length() > 0) { 
      youtube = Tool.youtubeResize(youtube, 640); 
    }

    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("plistno", plistno);
    hashMap.put("youtube", youtube);

    this.plistProc.youtube(hashMap);
    ra.addAttribute("plistno", plistno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/plist/read";
  }
  
  /**
   * 글 수정 폼 
   */
  @GetMapping(value = "/update_text")
  public String update_text(HttpSession session, 
      Model model, 
      @RequestParam(name="plistno", defaultValue="1") int plistno, 
      RedirectAttributes ra, 
      @RequestParam(name="word", defaultValue="") String word,
      @RequestParam(name="now_page", defaultValue="1") int now_page) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    if (this.memberProc.isMemberAdmin(session)) { // 관리자로 로그인한경우
      PlistVO plistVO = this.plistProc.read(plistno);
      model.addAttribute("plistVO", plistVO);

      SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno());
      model.addAttribute("songcateVO", songcateVO);

      return "/plist/update_text"; 

    } else {
      return "member/login_cookie_need";
    }

  }

  /**
   * 수정 처리 
   */
  @PostMapping(value = "/update_text")
  public String update_text(HttpSession session, 
      Model model, 
      @ModelAttribute("plistVO") PlistVO plistVO, 
      RedirectAttributes ra,
      @RequestParam(name="search_word", defaultValue="") String search_word, 
      @RequestParam(name="now_page", defaultValue="1") int now_page) {
      ra.addAttribute("word", search_word);
      ra.addAttribute("now_page", now_page);

    if (this.memberProc.isMemberAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("plistno", plistVO.getPlistno());
      map.put("passwd", plistVO.getPasswd());

      if (this.plistProc.password_check(map) == 1) { // 패스워드 일치
        this.plistProc.update_text(plistVO); // 글수정

        // mav 객체 이용
        ra.addAttribute("plistno", plistVO.getPlistno());
        ra.addAttribute("songcateno", plistVO.getSongcateno());
        return "redirect:/plist/read"; // @GetMapping(value = "/read")

      } else { // 패스워드 불일치
        ra.addFlashAttribute("code", "passwd_fail");
        ra.addFlashAttribute("cnt", 0);
        ra.addAttribute("url", "/plist/msg"); 

        return "redirect:/plist/post2get"; 
      }
    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      ra.addAttribute("url", "/member/login_cookie_need"); 
      return "redirect:/plist/post2get"; 
    }

  }
  
  /**
   * 파일 수정 폼 
   */
  @GetMapping(value = "/update_file")
  public String update_file(HttpSession session, Model model, 
         @RequestParam(name="plistno", defaultValue="0") int plistno,
         @RequestParam(name="word", defaultValue="") String word, 
         @RequestParam(name="now_page", defaultValue="1") int now_page) {
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);
    
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    PlistVO plistVO = this.plistProc.read(plistno);
    model.addAttribute("plistVO", plistVO);

    SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno());
    model.addAttribute("songcateVO", songcateVO);

    return "/plist/update_file";

  }

  /**
   * 파일 수정 처리 
   */
  @PostMapping(value = "/update_file")
  public String update_file(HttpSession session, Model model, RedirectAttributes ra,
                            @ModelAttribute("plistVO") PlistVO plistVO,
                            @RequestParam(name="word", defaultValue="") String word, 
                            @RequestParam(name="now_page", defaultValue="1") int now_page) {

    if (this.memberProc.isMemberAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      PlistVO plistVO_old = plistProc.read(plistVO.getPlistno());

      // 파일 삭제 시작
      String file1saved = plistVO_old.getFile1saved(); 
      String thumb1 = plistVO_old.getThumb1(); 
      long size1 = 0;

      String upDir = Plist.getUploadDir(); 

      Tool.deleteFile(upDir, file1saved); 
      Tool.deleteFile(upDir, thumb1);
      
      // 파일 전송 시작
      String file1 = ""; 
      MultipartFile mf = plistVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize(); // 파일 크기

      if (size1 > 0) { 
        file1saved = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(file1saved)) { 
          thumb1 = Tool.preview(upDir, file1saved, 250, 200);
        }

      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1 = "";
        file1saved = "";
        thumb1 = "";
        size1 = 0;
      }

      plistVO.setFile1(file1);
      plistVO.setFile1saved(file1saved);
      plistVO.setThumb1(thumb1);
      plistVO.setSize1(size1);

      this.plistProc.update_file(plistVO); // Oracle 처리
      ra.addAttribute ("plistno", plistVO.getPlistno());
      ra.addAttribute("songcateno", plistVO.getSongcateno());
      ra.addAttribute("word", word);
      ra.addAttribute("now_page", now_page);
      
      return "redirect:/plist/read";
    } else {
      ra.addAttribute("url", "/member/login_cookie_need"); 
      return "redirect:/plist/post2get"; // GET
    }
  }

  /**
   * 파일 삭제 폼
   */
  @GetMapping(value = "/delete")
  public String delete(HttpSession session, Model model, RedirectAttributes ra,
                               @RequestParam(name="songcateno", defaultValue="") int songcateno, 
                               @RequestParam(name="plistno", defaultValue="0") int plistno, 
                               @RequestParam(name="word", defaultValue="") String word, 
                               @RequestParam(name="now_page", defaultValue="1") int now_page) {
    if (this.memberProc.isMemberAdmin(session)) { // 관리자로 로그인한경우
      model.addAttribute("songcateno", songcateno);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      
      ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
      model.addAttribute("menu", menu);
      
      PlistVO plistVO = this.plistProc.read(plistno);
      model.addAttribute("plistVO", plistVO);
      
      SongcateVO songcateVO = this.songcateProc.read(plistVO.getSongcateno());
      model.addAttribute("songcateVO", songcateVO);
      
      return "/plist/delete"; // forward
      
    } else {
      ra.addAttribute("url", "/admin/login_cookie_need");
      return "redirect:/plist/msg"; 
    }

  }
  
  /**
   * 삭제 처리 
   */
  @PostMapping(value = "/delete")
  public String delete(RedirectAttributes ra,
      @RequestParam(name="songcateno", defaultValue="") int songcateno, 
      @RequestParam(name="plistno", defaultValue="0") int plistno, 
      @RequestParam(name="word", defaultValue="") String word, 
      @RequestParam(name="now_page", defaultValue="1") int now_page) {

    // 파일 삭제 시작
    PlistVO PlistVO_read = plistProc.read(plistno);
        
    String file1saved = PlistVO_read.getFile1saved();
    String thumb1 = PlistVO_read.getThumb1();
    
    String uploadDir = Plist.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  
    Tool.deleteFile(uploadDir, thumb1);  
        
    this.plistProc.delete(plistno); 
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("songcateno", songcateno);
    map.put("word", word);
    
    if (this.plistProc.list_by_songcateno_search_count(map) % Plist.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; 
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }

    ra.addAttribute("songcateno", songcateno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    
    return "redirect:/plist/list_by_songcateno";    
    
  }   
   
  
}