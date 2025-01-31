package dev.mvc.plist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.plist.PlistVO;

/**
 * 개발자가 구현합니다.
 * @author soldesk
 *
 */
public interface PlistProcInter {
  /**
   * 등록
   * @param plistVO
   * @return
   */
  public int create(PlistVO plistVO);

  /**
   * 모든 카테고리의 등록된 글목록
   * @return
   */
  public ArrayList<PlistVO> list_all();
 
  /**
   * 카테고리별 등록된 글 목록
   * @param songcateno
   * @return
   */
  public ArrayList<PlistVO> list_by_songcateno(int songcateno);
  
  /**
   * 조회
   * @param plistno
   * @return
   */
  public PlistVO read(int plistno);
  
  /**
   * map 등록, 수정, 삭제
   * @param map
   * @return 수정된 레코드 갯수
   */
  public int map(HashMap<String, Object> map);
  
  /**
   * youtube 등록, 수정, 삭제
   * @param youtube
   * @return 수정된 레코드 갯수
   */
  public int youtube(HashMap<String, Object> map);
  
  /**
   * 카테고리별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<PlistVO> list_by_songcateno_search(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int list_by_songcateno_search_count(HashMap<String, Object> hashMap);
  
  /**
   * 카테고리별 검색 목록 + 페이징
   * @param contentsVO
   * @return
   */
  public ArrayList<PlistVO> list_by_songcateno_search_paging(HashMap<String, Object> map);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno 카테고리 번호
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int songcateno, int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block);   

  /**
   * 패스워드 검사
   * @param hashMap
   * @return
   */
  public int password_check(HashMap<String, Object> hashMap);
  
  /**
   * 글 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_text(PlistVO plistVO);
 
  /**
   * 파일 정보 수정
   * @param contentsVO
   * @return 처리된 레코드 갯수
   */
  public int update_file(PlistVO plistVO);
 
  /**
   * 삭제
   * @param contentsno
   * @return 삭제된 레코드 갯수
   */
  public int delete(int plistno);
  
  /**
   * FK cateno 값이 같은 레코드 갯수 산출
   * @param cateno
   * @return
   */
  public int count_by_songcateno(int songcateno);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param cateno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_songcateno(int songcateno);
  
  /**
   * FK memberno 값이 같은 레코드 갯수 산출
   * @param memberno
   * @return
   */
  public int count_by_memberno(int memberno);
 
  /**
   * 특정 카테고리에 속한 모든 레코드 삭제
   * @param memberno
   * @return 삭제된 레코드 갯수
   */
  public int delete_by_memberno(int memberno);
  
  /**
   * 글 수 증가
   * @param 
   * @return
   */ 
  public int increaseReplycnt(int plistno);
 
  /**
   * 글 수 감소
   * @param 
   * @return
   */   
  public int decreaseReplycnt(int plistno);
  
  
  //해당 카테고리 번호에 속한 콘텐츠 리스트 가져오기
  public ArrayList<PlistVO> listBySongcateNo(int songcateno);
 
  // 해당 카테고리 번호에 속한 콘텐츠 삭제
  public int deleteBySongcateNo(int songcateno);





}
