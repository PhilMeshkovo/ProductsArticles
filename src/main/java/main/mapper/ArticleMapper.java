package main.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import main.dto.ArticleDto;
import main.dto.ArticleWithIdAndTimeDto;
import main.model.Article;
import main.model.Product;
import main.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

  @Autowired
  ProductRepo productRepo;

  public ArticleWithIdAndTimeDto articleToArticleWithIdDto(Article article) {
    ArticleWithIdAndTimeDto articleWithIdDto = new ArticleWithIdAndTimeDto();
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
    article.setTime(LocalDate.now(ZoneId.of("Europe/Moscow")));
    return article;
  }
}
