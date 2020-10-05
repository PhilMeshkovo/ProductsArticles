package main.dto;

import lombok.Data;

@Data
public class ArticleDto {

  private long productId;
  private String title;
  private String text;
  private String time;
}
