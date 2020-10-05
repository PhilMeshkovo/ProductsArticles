package main.mapper;

import java.time.LocalDate;
import main.dto.ArticleDto;
import main.dto.ArticleWithIdDto;
import main.model.Article;
import main.model.Product;
import main.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

  @Autowired
  ProductRepo productRepo;

  public ArticleWithIdDto articleToArticleWithIdDto(Article article) {
    ArticleWithIdDto articleWithIdDto = new ArticleWithIdDto();
    articleWithIdDto.setId(article.getId());
    articleWithIdDto.setProductId(article.getProduct().getId());
    articleWithIdDto.setText(article.getText());
    articleWithIdDto.setTitle(article.getTitle());
    articleWithIdDto.setTime(article.getTime().toString());
    return articleWithIdDto;
  }

  public Article articleDtoToArticle(ArticleDto articleDto) {
    Article article = new Article();
    Product product = productRepo.findById(articleDto.getProductId()).get();
    article.setProduct(product);
    article.setText(articleDto.getText());
    article.setTitle(articleDto.getTitle());

    LocalDate localDate = LocalDate.parse(articleDto.getTime());

    article.setTime(localDate);
    return article;
  }
}
