package dev.mvc.songcate;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.songcate.SongcateVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.plist.PlistProc;
import dev.mvc.plist.PlistProcInter;
import dev.mvc.plist.PlistVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/songcate")
public class SongcateCont {
  @Autowired
  @Qualifier("dev.mvc.songcate.SongcateProc")
  private SongcateProcInter songcateProc;
  
  @Autowired
  @Qualifier("dev.mvc.plist.PlistProc")
  private PlistProcInter plistProc;
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 8;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 6;
  
  /** 페이징 목록 주소 */
  private String list_file_name = "/songcate/list_search";
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  public SongcateCont() {
    System.out.println("-> SongcateCont created.");
  }
  
//  @GetMapping(value="/create") // http://localhost:9092/songcate/create
//  @ResponseBody // html 파일 내용임.
//  public String create() {
//    return "<h2>Create test</h2>";
//  }

//  @GetMapping(value="/create") // http://localhost:9092/songcate/create
//  public String create() {
//    return "/cate/create"; // /templates/songcate/create.html
//  }

  /**
   * 등록 폼
   * @param model
   * @return
   */
  // http://localhost:9092/songcate/create/ X
  // http://localhost:9092/songcate/create
  @GetMapping(value="/create") 
  public String create(Model model) {
    SongcateVO songcateVO = new SongcateVO();
    model.addAttribute("songcateVO", songcateVO);
    
    songcateVO.setGenre("장르를 입력하세요.");
    songcateVO.setArtist("가수를 입력하세요.");
    songcateVO.setVisible("Y");
    return "/songcate/create"; // /templates/songcate/create.html
  }
  
// - 유형 1 (권장): 옵션 모두 표시
//  @PostMapping(value="/create") // http://localhost:9092/songcate/create
//  public String create(Model model, @Valid @ModelAttribute("btccateVO") BtcCateVO btccateVO, BindingResult bindingresult) {
//
// - 유형 2: 코드 생략형
// @PostMapping(value="/create") // http://localhost:9092/songcate/create
// public String create(Model model, @Valid CateVO cateVO, BindingResult bindingResult) {
  /**
   * 등록 처리, http://localhost:9092/songcate/create
   * @param model Controller -> Thymeleaf HTML로 데이터 전송에 사용
   * @param cateVO Form 태그 값 -> 검증 -> cateVO 자동 저장, request.getParameter() 자동 실행
   * @param bindingResult 폼에 에러가 있는지 검사 지원
   * @return
   */
  @PostMapping(value="/create")
  public String create(Model model, 
                             @Valid @ModelAttribute("songcateVO") SongcateVO songcateVO, 
                             BindingResult bindingResult) {
//    System.out.println("-> create post.");
    if (bindingResult.hasErrors() == true) { // 에러가 있으면 폼으로 돌아갈 것.
//      System.out.println("-> ERROR 발생");
      return "/songcate/create"; // /templates/songcate/create.html
    }
    
//    System.out.println(cateVO.getSongtitle());
//    System.out.println(cateVO.getSeqno());
//    System.out.println(cateVO.getVisible());
    
    int cnt = this.songcateProc.create(songcateVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
//      model.addAttribute("code", "create_success");
//      model.addAttribute("songtitle", songcateVO.getSongtitle());
      
//      return "redirect:/songcate/list_all";
      return "redirect:/songcate/list_search"; //  @GetMapping(value="/list_search")
    } else {
      model.addAttribute("code", "create_fail");
    }

    model.addAttribute("cnt", cnt);
    
    return "/songcate/msg"; // /templates/songcate/msg.html
  }
  
  /**
   * 등록 폼 및 목록
   * @param model
   * @return
   */
  // http://localhost:9092/songcate/list_all
  @GetMapping(value="/list_all") 
  public String list_all(Model model) {
    SongcateVO songcateVO = new SongcateVO();
//    songcateVO.setGenre("장르");
//    songcateVO.setSongtitle("노래제목을 입력하세요."); // Form으로 초기값을 전달
    
    // 카테고리 그룹 목록
    ArrayList<String> list_genre = this.songcateProc.genreset();
    songcateVO.setGenre(String.join("/", list_genre));
    
    model.addAttribute("songcateVO", songcateVO);
    
    ArrayList<SongcateVO> list = this.songcateProc.list_all();
    model.addAttribute("list", list);
    
//    ArrayList<SongcateVO> menu = this.songcateProc.list_all_songcategrp_y();
//    model.addAttribute("menu", menu);
    
    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
    model.addAttribute("menu", menu);
    
    return "/songcate/list_all";  // /templates/cate/list_all.html
  }
  
  /**
   * 조회
   * http://localhost:9092/songcate/read/1
   */
  @GetMapping(value="/read/{songcateno}")
  public String read(Model model, @PathVariable("songcateno") Integer songcateno,
                    @RequestParam(name="now_page", defaultValue = "") int now_page,
                    @RequestParam(name="word", defaultValue = "") String word) {
     SongcateVO songcateVO = this.songcateProc.read(songcateno);
     model.addAttribute("songcateVO", songcateVO);
     
//     ArrayList<SongcateVO> list = this.songcateProc.list_all();
     ArrayList<SongcateVO> list = this.songcateProc.list_search_paging(word, now_page, this.record_per_page);
     
     // 각 SongcateVO의 자료 수 계산
        for (SongcateVO songcate : list) {
            if (songcate.getArtist().equals("--")) {  // 대분류인 경우
                int totalCnt = 0;

                // 중분류 카테고리에서 대분류에 속하는 자료 수를 합산
                for (SongcateVO subSongcate : list) {
                    if (!subSongcate.getArtist().equals("--") && subSongcate.getGenre().equals(songcate.getGenre())) {
                        totalCnt += this.songcateProc.cntcount(subSongcate.getSongcateno());
                    }
                } 
                songcate.setCnt(totalCnt); // 대분류 카테고리의 자료 수 설정
            } else {
                int postCount = this.songcateProc.cntcount(songcate.getSongcateno()); // 각 중분류 카테고리의 자료 수 조회
                songcate.setCnt(postCount); // 중분류 카테고리의 자료 수 설정
            }
        }
     
     model.addAttribute("list", list);
     
     ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
     model.addAttribute("menu", menu);
     
     model.addAttribute("word", word);
     
  // --------------------------------------------------------------------------------------
     // 페이지 번호 목록 생성
     // --------------------------------------------------------------------------------------
     int search_count = this.songcateProc.list_search_count(word);
     String paging = this.songcateProc.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
     model.addAttribute("paging", paging);
     model.addAttribute("now_page", now_page);
     
     // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
     int no = search_count - ((now_page - 1) * this.record_per_page);
     model.addAttribute("no", no);
     // --------------------------------------------------------------------------------------  
     
     
     return "/songcate/read";
  }
  
  /**
   * 수정폼
   * http://localhost:9092/songcate/update/1
   */
  @GetMapping(value="/update/{songcateno}")
  public String update(Model model, @PathVariable("songcateno") Integer songcateno,
                       @RequestParam(name="word", defaultValue = "") String word,
                       @RequestParam(name="now_page", defaultValue = "1") int now_page) {
     SongcateVO songcateVO = this.songcateProc.read(songcateno);
     model.addAttribute("songcateVO", songcateVO);
     
//     ArrayList<SongcateVO> list = this.songcateProc.list_all();
     ArrayList<SongcateVO> list = this.songcateProc.list_search_paging(word, now_page, this.record_per_page);
     
     // 각 SongcateVO의 자료 수 계산
        for (SongcateVO songcate : list) {
            if (songcate.getArtist().equals("--")) {  // 대분류인 경우
                int totalCnt = 0;

                // 중분류 카테고리에서 대분류에 속하는 자료 수를 합산
                for (SongcateVO subSongcate : list) {
                    if (!subSongcate.getArtist().equals("--") && subSongcate.getGenre().equals(songcate.getGenre())) {
                        totalCnt += this.songcateProc.cntcount(subSongcate.getSongcateno());
                    }
                } 
                songcate.setCnt(totalCnt); // 대분류 카테고리의 자료 수 설정
            } else {
                int postCount = this.songcateProc.cntcount(songcate.getSongcateno()); // 각 중분류 카테고리의 자료 수 조회
                songcate.setCnt(postCount); // 중분류 카테고리의 자료 수 설정
            }
        }
        
     model.addAttribute("list", list);
     
     ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
     model.addAttribute("menu", menu);
     
     ArrayList<String> list_genre = this.songcateProc.genreset();
     model.addAttribute("list_genre", String.join("/", list_genre));
     
     model.addAttribute("word", word);
     
  // --------------------------------------------------------------------------------------
     // 페이지 번호 목록 생성
     // --------------------------------------------------------------------------------------
     int search_count = this.songcateProc.list_search_count(word);
     String paging = this.songcateProc.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
     model.addAttribute("paging", paging);
     model.addAttribute("now_page", now_page);
     
     // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
     int no = search_count - ((now_page - 1) * this.record_per_page);
     model.addAttribute("no", no);
     // --------------------------------------------------------------------------------------  
  
     
     return "/songcate/update";  // templaes/songcate/update.html
  }
  
  /**
   * 수정 처리, http://localhost:9092/songcate/update
   * @param model Controller -> Thymeleaf HTML로 데이터 전송에 사용
   * @param cateVO Form 태그 값 -> 검증 -> cateVO 자동 저장, request.getParameter() 자동 실행
   * @param bindingResult 폼에 에러가 있는지 검사 지원
   * @return
   */
  @PostMapping(value="/update")
  public String update(Model model, @Valid @ModelAttribute("songcateVO") SongcateVO songcateVO, 
                                    BindingResult bindingResult,
                                    @RequestParam(name="word", defaultValue = "") String word,
                                    @RequestParam(name="now_page", defaultValue = "1") int now_page,
                                    RedirectAttributes ra) {
//    System.out.println("-> update post.");
    if (bindingResult.hasErrors() == true) { // 에러가 있으면 폼으로 돌아갈 것.
//      System.out.println("-> ERROR 발생");
      return "/songcate/update"; // /templates/songcate/update.html
    }
    
//    System.out.println(cateVO.getSongtitle());
//    System.out.println(cateVO.getSeqno());
//    System.out.println(cateVO.getVisible());
    
    int cnt = this.songcateProc.update(songcateVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
//      model.addAttribute("code", "update_success");
//      model.addAttribute("genre", songcateVO.getGenre());
//      model.addAttribute("songtitle", songcateVO.getSongtitle());
      
      ra.addAttribute("word", word); //redirct로 데이터 전송
      
      return "redirect:/songcate/update/" + songcateVO.getSongcateno();
      
    } else {
      model.addAttribute("code", "update_fail");
    }

    model.addAttribute("cnt", cnt);
    
 // --------------------------------------------------------------------------------------
    // 페이지 번호 목록 생성
    // --------------------------------------------------------------------------------------
    int search_count = this.songcateProc.list_search_count(word);
    String paging = this.songcateProc.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    // --------------------------------------------------------------------------------------  

    
    return "/songcate/msg"; // /templates/songcate/msg.html
  }
  
  /**
   * 삭제폼
   * http://localhost:9092/songcate/delete/1
   * @param session 
   */
  @GetMapping(value="/delete/{songcateno}")
  public String delete(Model model, @PathVariable("songcateno") Integer songcateno,
                       @RequestParam(name="word", defaultValue = "") String word,
                       @RequestParam(name="now_page", defaultValue = "1") int now_page, HttpSession session) {
    
    if (this.memberProc.isMemberAdmin(session)) {
      SongcateVO songcateVO = this.songcateProc.read(songcateno);
      model.addAttribute("songcateVO", songcateVO);
      int cnt = this.songcateProc.cntcount(songcateno);
      
//      ArrayList<SongcateVO> list = this.songcateProc.list_all();
      ArrayList<SongcateVO> list = this.songcateProc.list_search_paging(word, now_page, this.record_per_page);
      
   // 각 SongcateVO의 자료 수 계산
      for (SongcateVO songcate : list) {
          if (songcate.getArtist().equals("--")) {  // 대분류인 경우
              int totalCnt = 0;

              // 중분류 카테고리에서 대분류에 속하는 자료 수를 합산
              for (SongcateVO subSongcate : list) {
                  if (!subSongcate.getArtist().equals("--") && subSongcate.getGenre().equals(songcate.getGenre())) {
                      totalCnt += this.songcateProc.cntcount(subSongcate.getSongcateno());
                  }
              } 
              songcate.setCnt(totalCnt); // 대분류 카테고리의 자료 수 설정
          } else {
              int postCount = this.songcateProc.cntcount(songcate.getSongcateno()); // 각 중분류 카테고리의 자료 수 조회
              songcate.setCnt(postCount); // 중분류 카테고리의 자료 수 설정
          }
      }
      
      model.addAttribute("list", list);
      
      ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
      model.addAttribute("menu", menu);
      
      model.addAttribute("cnt", cnt);  // 콘텐츠 개수 추가
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      
   // --------------------------------------------------------------------------------------
      // 페이지 번호 목록 생성
      // --------------------------------------------------------------------------------------
      int search_count = this.songcateProc.list_search_count(word);
      String paging = this.songcateProc.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      // --------------------------------------------------------------------------------------  
      if (cnt == 0) {
        // 콘텐츠가 없을 경우 cate/delete.html로 이동
        return "/songcate/delete";
      } else {
        // 콘텐츠가 있을 경우 cate/list_all_delete.html로 이동
        ArrayList<PlistVO> plistList = plistProc.listBySongcateNo(songcateno);  // 해당 카테고리의 콘텐츠 리스트 불러오기
        model.addAttribute("plistList", plistList);
        model.addAttribute("cnt", cnt);
        model.addAttribute("word", word);
        model.addAttribute("now_page", now_page);
        return "/songcate/list_all_delete"; // cate/list_all_delete.html로 이동
      }
    } else {
      return "redirect:/member/login_cookie_need"; // 관리자 권한 필요
    }
  }
  
  /**
   * 카테고리 및 연관 자료 삭제 처리
   */
  @PostMapping(value = "/delete_all_confirm")
  public String deleteAllSongcategory(@RequestParam (name="songcateno", defaultValue="0") int songcateno,
                                                       RedirectAttributes redirectAttributes) {
    // 콘텐츠 삭제
    plistProc.deleteBySongcateNo(songcateno);

    // 카테고리 삭제
    songcateProc.delete(songcateno);

    redirectAttributes.addFlashAttribute("msg", "카테고리와 관련된 모든 자료가 삭제되었습니다.");
    return "redirect:/songcate/list_search";
  }
  
  /**
   * 카테고리 삭제 폼
   */
  @GetMapping(value = "/delete")
  public String delete(Model model) {
    // 기본 삭제 폼
    return "/songcate/delete";  // songcate/delete.html로 이동
  }
  
  /**
   * 삭제 처리, http://localhost:9092/songcate/delete
   * @param model Controller -> Thymeleaf HTML로 데이터 전송에 사용
   * @param session 
   * @param cateVO Form 태그 값 -> 검증 -> cateVO 자동 저장, request.getParameter() 자동 실행
   * @param bindingResult 폼에 에러가 있는지 검사 지원
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, 
                                          @RequestParam(name = "songcateno", defaultValue = "0") Integer songcateno,
                                          @RequestParam(name="word", defaultValue = "") String word,
                                          @RequestParam(name="now_page", defaultValue = "1") int now_page,
                                          RedirectAttributes ra, HttpSession session) {
    
    if (this.memberProc.isMemberAdmin(session)) {
      System.out.println("-> delete_process");
      
      SongcateVO songcateVO = this.songcateProc.read(songcateno); // 삭제전에 삭제 결과를 출력할 레코드 조회
      model.addAttribute("songcateVO", songcateVO);
      
      // 카테고리에 속한 콘텐츠 개수 확인
      int cnt = this.songcateProc.cntcount(songcateno); // 해당 카테고리 내 콘텐츠 수

      if (cnt == 0) {
          // 콘텐츠가 없으면 카테고리만 삭제
          int deleteCnt = this.songcateProc.delete(songcateno);
          System.out.println("-> deleteCnt: " + deleteCnt);

          if (deleteCnt == 1) {
              ra.addAttribute("word", word); // redirect로 데이터 전송

              // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야 함.
              int search_cnt = this.songcateProc.list_search_count(word);
              if (search_cnt % this.record_per_page == 0) {
                  now_page = now_page - 1;
                  if (now_page < 1) {
                      now_page = 1; // 최소 시작 페이지
                  }
              }

              ra.addAttribute("now_page", now_page); // redirect로 데이터 전송

              return "redirect:/songcate/list_search"; // 카테고리 목록 페이지로 리다이렉트
            } else {
              model.addAttribute("code", "delete_fail");
              return "/songcate/msg"; // 실패 메시지 출력
          }

      } else {
          // 콘텐츠가 있을 경우 cate/list_all_delete.html로 이동하여 확인 요청
          ArrayList<PlistVO> plsitList = plistProc.listBySongcateNo(songcateno); // 해당 카테고리의 콘텐츠 리스트 불러오기
          model.addAttribute("plsitList", plsitList);
          model.addAttribute("cnt", cnt);
          model.addAttribute("word", word);
          model.addAttribute("now_page", now_page);
          return "/songcate/list_all_delete"; // cate/list_all_delete.html로 이동
          }
      } else {
          return "redirect:/member/login_cookie_need";  // 권한 없을 때 로그인 페이지로 리다이렉트
     }
        
  }
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등, http://localhost:9092/songcate/update_seqno_forward/1
   * @param model Controller -> Thymeleaf HTML로 데이터 전송에 사용
   * @return
   */
  @GetMapping(value="/update_seqno_forward/{songcateno}")
  public String update_seqno_forward(Model model, @PathVariable("songcateno") Integer songcateno,
                                    @RequestParam(name="word", defaultValue = "") String word,
                                    @RequestParam(name="now_page", defaultValue = "") int now_page,
                                    RedirectAttributes ra) {
    this.songcateProc.update_seqno_forward(songcateno);
    
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    return "redirect:/songcate/list_search";  // @GetMapping(value="/lsit_all")
  }
  
  /**
   * 우선 순위 낮춤, 1 등 -> 10 등, http://localhost:9092/songcate/update_seqno_backward/1
   * @param model Controller -> Thymeleaf HTML로 데이터 전송에 사용
   * @return
   */
  @GetMapping(value="/update_seqno_backward/{songcateno}")
  public String update_seqno_backward(Model model, @PathVariable("songcateno") Integer songcateno,
                                      @RequestParam(name="word", defaultValue = "") String word,
                                      @RequestParam(name="now_page", defaultValue = "") int now_page,
                                      RedirectAttributes ra) {
    this.songcateProc.update_seqno_backward(songcateno);
    
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    return "redirect:/songcate/list_search";  // @GetMapping(value="/lsit_all")
  }
  
  /**
   * 카테고리 공개 설정, http://localhost:9092/songcate/update_visible_y/1
   * @param model
   * @param songcateno
   * @return
   */
  @GetMapping(value="/update_visible_y/{songcateno}")
  public String update_visible_y(Model model, @PathVariable("songcateno") Integer songcateno,
                                                            @RequestParam(name="word", defaultValue = "") String word,
                                                            @RequestParam(name="now_page", defaultValue = "") int now_page,
                                                            RedirectAttributes ra) {
    this.songcateProc.update_visible_y(songcateno);
    
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    return "redirect:/songcate/list_search";
  }
  
  /**
   * 카테고리 비공개 설정, http://localhost:9092/songcate/update_visible_n/1
   * @param model
   * @param songcateno
   * @return
   */
  @GetMapping(value="/update_visible_n/{songcateno}")
  public String update_visible_n(Model model, @PathVariable("songcateno") Integer songcateno,
      @RequestParam(name="word", defaultValue = "") String word,
      @RequestParam(name="now_page", defaultValue = "") int now_page,
      RedirectAttributes ra) {
    this.songcateProc.update_visible_n(songcateno);
    
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    return "redirect:/songcate/list_search";
  }
  
//  /**
//   * 등록 폼 및 검색 목록
//   * @param model
//   * @return
//   */
//  // http://localhost:9092/songcate/list_all
//  @GetMapping(value="/list_search") 
//  public String list_search(Model model, 
//                            @RequestParam(name="word", defaultValue = "") String word) {
//    SongcateVO songcateVO = new SongcateVO();
////    songcateVO.setGenre("장르");
////    songcateVO.setSongtitle("노래제목을 입력하세요."); // Form으로 초기값을 전달
//    
//    // 카테고리 그룹 목록
//    ArrayList<String> list_genre = this.songcateProc.genreset();
//    songcateVO.setGenre(String.join("/", list_genre));
//    
//    model.addAttribute("songcateVO", songcateVO);
//    
//    word = Tool.checkNull(word);
//    ArrayList<SongcateVO> list = this.songcateProc.list_search(word);
//    model.addAttribute("list", list);
//    
////    ArrayList<SongcateVO> menu = this.songcateProc.list_all_songcategrp_y();
////    model.addAttribute("menu", menu);
//    
//    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
//    model.addAttribute("menu", menu);
//    
//    int search_cnt = this.songcateProc.list_search_count(word);
//    model.addAttribute("search_cnt", search_cnt);
//    
//    model.addAttribute("word", word);
//    
//    return "/songcate/list_search";  // /templates/cate/list_all.html
//  }
  
  /**
   * 등록 폼 및 검색 목록 + 페이징
   * @param model
   * @return
   */
  // http://localhost:9092/songcate/list_all
  @GetMapping(value="/list_search") 
  public String list_search_paging(HttpSession session, Model model, 
                            @RequestParam(name="word", defaultValue = "") String word,
                            @RequestParam(name="now_page", defaultValue = "1")int now_page) {
    
    if (this.memberProc.isMemberAdmin(session)) {
        SongcateVO songcateVO = new SongcateVO();
  //    songcateVO.setGenre("장르");
  //    songcateVO.setSongtitle("노래제목을 입력하세요."); // Form으로 초기값을 전달
      
      // 카테고리 그룹 목록
      ArrayList<String> list_genre = this.songcateProc.genreset();
      songcateVO.setGenre(String.join("/", list_genre));
      
      model.addAttribute("songcateVO", songcateVO);
      
      word = Tool.checkNull(word);
      ArrayList<SongcateVO> list = this.songcateProc.list_search_paging(word, now_page, this.record_per_page);
      model.addAttribute("list", list);
      
  //    ArrayList<SongcateVO> menu = this.songcateProc.list_all_songcategrp_y();
  //    model.addAttribute("menu", menu);
      
      for (SongcateVO songcate : list) {
        if (songcate.getArtist().equals("--")) {  // 대분류인 경우
            int totalCnt = 0;

            // 중분류 카테고리에서 대분류에 속하는 자료 수를 합산
            for (SongcateVO subSongcate : list) {
                if (!subSongcate.getArtist().equals("--") && subSongcate.getGenre().equals(songcate.getGenre())) {
                    totalCnt += this.songcateProc.cntcount(subSongcate.getSongcateno());
                    }
                } 
            songcate.setCnt(totalCnt); // 대분류 카테고리의 자료 수 설정
        } else {
            int postCount = this.songcateProc.cntcount(songcate.getSongcateno()); // 각 중분류 카테고리의 자료 수 조회
            songcate.setCnt(postCount); // CateVO 객체에 자료 수 설정
        }
      }
      
      ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
      model.addAttribute("menu", menu);
      
      int search_cnt = this.songcateProc.list_search_count(word);
      model.addAttribute("search_cnt", search_cnt);
      
      model.addAttribute("word", word);  // 검색어
      
      // -------------------------------------------------------------
      // 페이지 번호 목록 생성
      // -------------------------------------------------------------
      int search_count = this.songcateProc.list_search_count(word);
      String paging = this.songcateProc.pagingBox(now_page, word, this.list_file_name, search_count, this.record_per_page, this.page_per_block);
      
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      // -------------------------------------------------------------
      
      return "/songcate/list_search";  // /templates/cate/list_all.html
    } else {
      return "redirect:/member/login_cookie_need"; // redirect
    }
    
  }
}









