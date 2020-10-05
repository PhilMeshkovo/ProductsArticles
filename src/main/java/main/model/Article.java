package main.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter
@Setter
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private long id;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String text;

  @Column(nullable = false)
  private LocalDate time;

}
