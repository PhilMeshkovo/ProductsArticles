package main.dto;

import lombok.Data;

@Data
public class ProductWithIdDto extends ProductDto {
    private long id;
}
