package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import main.dto.ArticleDto;
import main.dto.ArticleWithIdAndTimeDto;
import main.mapper.ArticleMapper;
import main.model.Article;
import main.model.Product;
import main.repository.ArticleRepo;
import main.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

  @Autowired
  ArticleRepo articleRepo;

  @Autowired
  ProductRepo productRepo;

  @Autowired
  ArticleMapper articleMapper;

  public List<ArticleWithIdAndTimeDto> getAllArticles(String mode) throws Exception {
    List<ArticleWithIdAndTimeDto> articleWithIdDtoList = new ArrayList<>();
    List<Article> allArticles;
    if (mode.toUpperCase().equals("EARLY")) {
      allArticles = articleRepo.findAll(Sort.by("time"));
      for (Article article : allArticles) {
        ArticleWithIdAndTimeDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
        articleWithIdDtoList.add(articleWithIdDto);
      }
    } else if (mode.toUpperCase().equals("RECENT")) {
      allArticles = articleRepo.findAll(Sort.by("time").descending());
      for (Article article : allArticles) {
        ArticleWithIdAndTimeDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
        articleWithIdDtoList.add(articleWithIdDto);
      }
    } else {
      throw new Exception("no such mode");
    }
    return articleWithIdDtoList;
  }

  public ArticleWithIdAndTimeDto getArticleById(long id) throws NotFoundException {
    Optional<Article> articleById = articleRepo.findById(id);
    if (articleById.isPresent()) {
      ArticleWithIdAndTimeDto articleWithIdDto = articleMapper
          .articleToArticleWithIdDto(articleById.get());
      return articleWithIdDto;
    } else {
      throw new NotFoundException("No such article");
    }
  }

  public void addArticle(ArticleDto articleDto) throws NotFoundException {
    Optional<Product> productById = productRepo.findById(articleDto.getProductId());
    if (productById.isPresent()) {
      Article article = articleMapper.articleDtoToArticle(articleDto);
      articleRepo.save(article);
    } else {
      throw new NotFoundException("No such product");
    }
  }

  @Transactional
  public ArticleWithIdAndTimeDto updateArticle(long id, ArticleDto articleDto)
      throws NotFoundException {
    Optional<Article> articleById = articleRepo.findById(id);
    if (articleById.isPresent()) {
      Article article = articleRepo.getOne(id);
      article.setText(articleDto.getText());
      article.setTitle(articleDto.getTitle());
      ArticleWithIdAndTimeDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
      return articleWithIdDto;
    } else {
      throw new NotFoundException("No such article");
    }
  }

  public void deleteArticle(long id) throws NotFoundException {
    Optional<Article> articleById = articleRepo.findById(id);
    if (articleById.isPresent()) {
      articleRepo.delete(articleById.get());
    } else {
      throw new NotFoundException("No such article");
    }
  }
}
