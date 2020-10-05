package main.repository;

import java.util.List;
import main.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

  @Query(nativeQuery = true,
      value = "select * from product left join article "
          + "on product.id = article.product_id group by product.id "
          + "order by count(article.id) desc")
  List<Product> findAllSortedByArticles();
}
