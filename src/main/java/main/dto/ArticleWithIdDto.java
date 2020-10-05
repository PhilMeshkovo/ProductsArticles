package main.dto;

import lombok.Data;

@Data
public class ArticleWithIdDto extends ArticleDto {
  private long id;
}
