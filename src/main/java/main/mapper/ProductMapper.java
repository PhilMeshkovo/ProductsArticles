package main.mapper;

import main.dto.ProductDto;
import main.dto.ProductWithIdDto;
import main.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductDto productToProductDto(Product product) {
    ProductDto productDto = new ProductDto();
    productDto.setTitle(product.getTitle());
    productDto.setDescription(product.getDescription());
    productDto.setPrice(product.getPrice());
    return productDto;
  }

  public ProductWithIdDto productToProductWithIdDto(Product product) {
    ProductWithIdDto productDto = new ProductWithIdDto();
    productDto.setTitle(product.getTitle());
    productDto.setDescription(product.getDescription());
    productDto.setPrice(product.getPrice());
    productDto.setId(product.getId());
    return productDto;
  }

  public Product productDtoToProduct(ProductDto productDto) {
    Product product = new Product();
    product.setTitle(productDto.getTitle());
    product.setDescription(productDto.getDescription());
    product.setPrice(productDto.getPrice());
    return product;
  }
}
