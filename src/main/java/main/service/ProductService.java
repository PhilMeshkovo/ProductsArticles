package main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javassist.NotFoundException;
import main.dto.ProductDto;
import main.dto.ProductWithIdDto;
import main.mapper.ProductMapper;
import main.model.Product;
import main.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

  @Autowired
  ProductRepo productRepo;

  @Autowired
  ProductMapper productMapper;

  public List<ProductWithIdDto> getAllProducts() {
    List<Product> products = productRepo.findAll();
    List<ProductWithIdDto> productDtoList = new ArrayList<>();
    for (Product product : products) {
        ProductWithIdDto productDto = productMapper.productToProductWithIdDto(product);
        productDtoList.add(productDto);
    }
    return productDtoList;
  }

  public ProductWithIdDto getProductById(long id) throws NotFoundException {
    Optional<Product> productById = productRepo.findById(id);
    if (productById.isPresent()){
      ProductWithIdDto productDto = productMapper.productToProductWithIdDto(productById.get());
      return productDto;
    } else {
      throw new NotFoundException("No such product");
    }
  }

  public void addProduct(ProductDto productDto) {
    Product product = productMapper.productDtoToProduct(productDto);
    productRepo.save(product);
  }

  @Transactional
  public ProductWithIdDto updateProduct(long id, ProductDto productDto) throws NotFoundException {
    Optional<Product> productById = productRepo.findById(id);
    if (productById.isPresent()){
      Product product = productRepo.getOne(id);
      product.setTitle(productDto.getTitle());
      product.setDescription(productDto.getDescription());
      product.setPrice(productDto.getPrice());
      ProductWithIdDto productWithIdDto = productMapper.productToProductWithIdDto(product);
      return productWithIdDto;
    } else {
      throw new NotFoundException("No such product");
    }
  }

  public void deleteProduct(long id) throws NotFoundException {
    Optional<Product> productById = productRepo.findById(id);
    if (productById.isPresent()) {
        productRepo.delete(productById.get());
    } else {
      throw new NotFoundException("No such product");
    }
  }
}
