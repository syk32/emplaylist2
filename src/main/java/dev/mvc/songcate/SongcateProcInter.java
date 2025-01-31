package dev.mvc.songcate;

import java.util.ArrayList;
import java.util.Map;

public interface SongcateProcInter {
  /**
   * <pre>
   * 등록
   * </pre>
   * @param cateVO
   * @return
   */
  public int create(SongcateVO songcateVO);
  
  /**
   * 전체 목록
   * @return
   */
  public ArrayList<SongcateVO> list_all(); 
  
  /**
   * 조회
   * @param cateno
   * @return
   */
  public SongcateVO read(Integer songcateno);
  
  /**
   * 수정
   * @param cateVO 수정할 내용
   * @return 수정된 레코드 갯수
   */
  public int update(SongcateVO songcateVO); 
  
  /**
   * 삭제
   * @param cateno 삭제할 레코드 PK
   * @return 삭제된 레코드 갯수
   */
  public int delete(int songcateno);
  
  /**
   * 우선 순위 높임, 10 등 -> 1 등
   * @param songcateno
   * @return
   */
  public int update_seqno_forward(int songcateno);
  
  /**
   * 우선 순위 낮춤, 1 등 -> 10 등
   * @param songcateno
   * @return
   */
  public int update_seqno_backward(int songcateno);
  
  /**
   * 카테고리 공개 설정
   * @param songcateno
   * @return
   */
  public int update_visible_y(int songcateno);
  
  /**
   * 카테고리 비공개 설정
   * @param songcateno
   * @return
   */
  public int update_visible_n(int songcateno);
  
  /**
   * 숨긴 카테고리 그룹을 제외한 접속자에게 공개할 '카테고리 그룹' 출력
   * SQL -> CateVO 객체 레코드 수 만큼 생성 -> ArrayList<CateVO> 객체 생성되어 CateDAOInter
   * select id="list_all_cate_y" resultType="dev.mvc.cate.CateVO"
   * @return
   */
  public ArrayList<SongcateVO> list_all_songcategrp_y();
  
  /**
   * 숨긴 카테고리 그룹을 제외한 접속자에게 공개할 '카테고리 그룹' 출력
   * SQL -> CateVO 객체 레코드 수 만큼 생성 -> ArrayList<CateVO> 객체 생성되어 CateDAOInter
   * select id="list_all_cate_y" resultType="dev.mvc.cate.CateVO"
   * @return
   */
  public ArrayList<SongcateVO> list_all_songcate_y(String genre);
  
  /**
   * 화면 상단 메뉴
   * @return
   */
  public ArrayList<SongcateVOMenu> menu();
  
  /**
   * 장르 목록
   * @return
   */
  public ArrayList<String> genreset();
  
  /**
   * 검색
   * SQL -> CateVO 객체 레코드 수 만큼 생성 
   * -> ArrayList<cateVO> 객체 생성되어 CateDAOInter로 리턴
   * @return
   */
  public ArrayList<SongcateVO> list_search(String word);
  
  /**
   *  검색 갯수
   * @param word
   * @return
   */
  public Integer list_search_count(String word);
  
  /**
   * 검색 + 페이징
   * select id="list_search_paging" resultType="dev.mvc.songcate.SongcateVO" parameterType="Map"
   * @return
   */
  public ArrayList<SongcateVO> list_search_paging(String word, int now_page, int record_per_page);

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file_name 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */
  String pagingBox(int now_page, String word, String list_file_name, int search_count, int record_per_page,
      int page_per_block);
  
  public int cntcount(int songcateno);



}






