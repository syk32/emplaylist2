package dev.mvc.songcate;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.songcate.SongcateVO;

public interface SongcateDAOInter {
  /**
   * <pre>
   * MyBATIS: insert id="create" parameterType="dev.mvc.cate.CateVO"
   * insert: int를 리턴, 등록한 레코드 갯수를 리턴
   * id="create": 메소드명으로 사용
   * parameterType="dev.mvc.cate.CateVO": 메소드의 파라미터
   * Spring Boot가 자동으로 구현
   * </pre>
   * @param songcateVO
   * @return
   */
  public int create(SongcateVO songcateVO);
  
  /**
   * 전체 목록
   * SQL -> CateVO 객체 레코드 수 만큼 생성 -> ArrayList<songcateVO> 객체 생성되어 CateDAOInter로 리턴 
   * select id="list_all" resultType="dev.mvc.cate.CateVO"
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
  public ArrayList<SongcateVO> list_search_paging(Map<String, Object> map); 
  
  public int cntcount(int songcateno);
  
}







