package main.dto;

import lombok.Data;

@Data
public class ArticleWithIdAndTimeDto extends ArticleDto {

  private long id;
  private String time;
}
