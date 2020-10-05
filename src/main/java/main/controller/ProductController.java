package main.controller;

import java.util.List;
import javassist.NotFoundException;
import main.dto.ProductDto;
import main.dto.ProductWithIdDto;
import main.service.ProductService;
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
@RequestMapping("/product")
public class ProductController {

  @Autowired
  ProductService productService;

  @GetMapping
  public ResponseEntity<?> getAllProducts(@RequestParam(value = "mode",
      defaultValue = "ID", required = false) String mode) {
    try {
      List<ProductWithIdDto> products = productService.getAllProducts(mode);
      return ResponseEntity.ok(products);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProductById(@PathVariable long id) {
    try {
      ProductWithIdDto productDto = productService.getProductById(id);
      return ResponseEntity.ok(productDto);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public @ResponseBody
  void createProduct(@RequestBody ProductDto productDto) {
    productService.addProduct(productDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateProduct(@PathVariable long id,
      @RequestBody ProductDto productDto) {
    try {
      ProductWithIdDto productWithIdDto = productService.updateProduct(id, productDto);
      return ResponseEntity.ok(productWithIdDto);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  private @ResponseBody
  void deleteProduct(@PathVariable long id) throws NotFoundException {
    productService.deleteProduct(id);
  }
}
