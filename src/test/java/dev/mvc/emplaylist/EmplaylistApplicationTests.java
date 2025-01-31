package dev.mvc.emplaylist;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.songcate.SongcateDAOInter;
import dev.mvc.songcate.SongcateProcInter;
import dev.mvc.songcate.SongcateVO;
import dev.mvc.songcate.SongcateVOMenu;

@SpringBootTest
class EmplaylistApplicationTests {
  @Autowired  // CateDAOInter를 구현한 클래스의 객체를 자동으로 생성하여 cateDAO 객체에 할당
  private SongcateDAOInter songcateDAO;
  
  @Autowired
  @Qualifier("dev.mvc.songcate.SongcateProc")
  private SongcateProcInter  songcateProc;
  
	@Test
	void contextLoads() {
	}
	
//	@Test
//  public void testCreate() {
//	  CateVO cateVO = new CateVO();
//	  cateVO.setName("영화");
//	  cateVO.setSeqno(1);
//	  cateVO.setVisible("Y");
//    int cnt = this.cateDAO.create(cateVO);
//    System.out.println("cnt: " + cnt);
//  }
	
//	@Test
//	public void testCreate() {
//    CateVO cateVO = new CateVO();
//    cateVO.setName("여행");
//    cateVO.setSeqno(1);
//    cateVO.setVisible("Y");
//    
//    int cnt = this.cateProc.create(cateVO);
//    System.out.println("cnt: " + cnt);
//  
//	}
	
//	@Test
//	public void list_all() {
//	  ArrayList<CateVO> list = this.cateProc.list_all();
//	  
//	  for (CateVO cateVO:list) {
//	    System.out.println(cateVO.toString());
//	  }
//	}

//	@Test
//	public void read() {
//	  SongcateVO songcateVO = this.songcateProc.read(1);
//	  System.out.println(songcateVO.toString());
//	}

//	@Test
//  public void menu() {
//    ArrayList<SongcateVOMenu> menu = this.songcateProc.menu();
//    
//    for(SongcateVOMenu songcateVOMenu: menu) {
//      System.out.println("-> genre: " + songcateVOMenu.getGenre());
//      
//      ArrayList<SongcateVO> list_name = songcateVOMenu.getList_name();
//      for(SongcateVO songcateVO: list_name) {
//        System.out.println("    " + songcateVO.getArtist());
//      }
//    }
//  }
	
//	@Test
//	public void genreset() {
//	  ArrayList<String> list = this.songcateProc.genreset();
//	  for(String genre:list) {
//	    System.out.println(genre);
//	  }
//	  System.out.println(String.join("/", list));
//	}
	
	@Test
	public void list_search_paging() {
	  this.songcateProc.list_search_paging("Rock", 1, 3);
	  this.songcateProc.list_search_paging("알앤비&소울", 1, 3);
	  this.songcateProc.list_search_paging("발라드", 1, 3);
	  this.songcateProc.list_search_paging("POP", 1, 3);
	  this.songcateProc.list_search_paging("인디", 1, 3);
	  this.songcateProc.list_search_paging("댄스", 1, 3);
	  
//	  WHERE r >= 1 AND r <= 3
//    -> 3
//    WHERE r >= 1 AND r <= 3
//    -> 3
//    WHERE r >= 1 AND r <= 3
//    -> 3
//    WHERE r >= 1 AND r <= 3
//    -> 3
//    WHERE r >= 1 AND r <= 3
//    -> 2
//    WHERE r >= 1 AND r <= 3
//    -> 3
	}
}







