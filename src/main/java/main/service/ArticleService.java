package main.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import main.dto.ArticleDto;
import main.dto.ArticleWithIdDto;
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

  public List<ArticleWithIdDto> getAllArticles(String mode) {
    List<ArticleWithIdDto> articleWithIdDtoList = new ArrayList<>();
    List<Article> allArticles;
    if (mode.toUpperCase().equals("TIME")) {
      allArticles = articleRepo.findAll(Sort.by("time").descending());
      for (Article article : allArticles) {
        ArticleWithIdDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
        articleWithIdDtoList.add(articleWithIdDto);
      }
    } else {
      allArticles = articleRepo.findAll();
      for (Article article : allArticles) {
        ArticleWithIdDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
        articleWithIdDtoList.add(articleWithIdDto);
      }
    }
    return articleWithIdDtoList;
  }

  public ArticleWithIdDto getArticleById(long id) throws NotFoundException {
    Optional<Article> articleById = articleRepo.findById(id);
    if (articleById.isPresent()) {
      ArticleWithIdDto articleWithIdDto = articleMapper
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
      article.setTime(LocalDate.now());
      articleRepo.save(article);
    } else {
      throw new NotFoundException("No such product");
    }
  }

  @Transactional
  public ArticleWithIdDto updateArticle(long id, ArticleDto articleDto) throws NotFoundException {
    Optional<Article> articleById = articleRepo.findById(id);
    if (articleById.isPresent()) {
      Article article = articleRepo.getOne(id);
      article.setText(articleDto.getText());
      article.setTitle(articleDto.getTitle());
      ArticleWithIdDto articleWithIdDto = articleMapper.articleToArticleWithIdDto(article);
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
