package main.controller;

import java.util.List;
import javassist.NotFoundException;
import main.dto.ArticleDto;
import main.dto.ArticleWithIdAndTimeDto;
import main.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  ArticleService articleService;

  @GetMapping
  public ResponseEntity<?> getAllArticles(@RequestParam(value = "mode",
      defaultValue = "RECENT", required = false) String mode) {
    try {
      List<ArticleWithIdAndTimeDto> articles = articleService.getAllArticles(mode);
      return ResponseEntity.ok(articles);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  public @ResponseBody
  ResponseEntity<?> getArticleById(@PathVariable long id) {
    try {
      ArticleWithIdAndTimeDto articleWithIdDto = articleService.getArticleById(id);
      return ResponseEntity.ok(articleWithIdDto);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public @ResponseBody
  void createArticle(@RequestBody ArticleDto articleDto) throws NotFoundException {
    articleService.addArticle(articleDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateArticle(@PathVariable long id,
      @RequestBody ArticleDto articleDto) {
    try {
      ArticleWithIdAndTimeDto articleWithIdDto = articleService.updateArticle(id, articleDto);
      return ResponseEntity.ok(articleWithIdDto);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  private @ResponseBody
  void deleteArticle(@PathVariable long id) throws NotFoundException {
    articleService.deleteArticle(id);
  }
}
