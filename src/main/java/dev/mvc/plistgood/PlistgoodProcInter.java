package dev.mvc.plistgood;

import java.util.ArrayList;
import java.util.HashMap;

public interface PlistgoodProcInter {
  
  /*
   * 등록
   */
  public int create(PlistgoodVO plistgoodVO);
  
  /*
   * 목록
   */
  public ArrayList<PlistgoodVO> list_all();
  
  /*
   * 삭제
   */
  public int delete(int goodno);
  
  /*
   * 추천수
   */
  public int heartCnt(HashMap<String, Object> map);
  
  /*
   * 조회
   */
  public PlistgoodVO read(int goodno);
  
  /*
   * plistno, memberno로 조회
   */
  public PlistgoodVO readByplistmember(HashMap<String, Object> map);
  
  /*
   * 목록
   */
  public ArrayList<PlistgoodMemberVO> list_all_join();
  
  /*
   * 검색 개수
   */
  public int count_search(HashMap<String, Object> Map);
  
  /*
   * 검색 + 페이징
   */
  public ArrayList<PlistgoodMemberVO> list_search_paging(HashMap<String, Object> Map);

  /*
   * 페이징 박스
   */
  public String pagingBox(int goodno, int now_page, String word, String list_file, int search_count, 
      int record_per_page, int page_per_block);

}
