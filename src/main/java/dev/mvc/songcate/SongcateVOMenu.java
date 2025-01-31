package dev.mvc.songcate;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SongcateVOMenu {
  /** 카테고리 그룹 (대분류) */
  private String genre;
  
  /** 카테고리 (중분류) */
  private ArrayList<SongcateVO> list_name;

}
