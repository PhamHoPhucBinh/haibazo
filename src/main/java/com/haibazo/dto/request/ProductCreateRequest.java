package com.haibazo.dto.request;


import com.haibazo.enums.Color;
import com.haibazo.enums.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {
    String productName;
    double originalPrice;
    double discountPrice;
    Color color;
    Size size;
    String description;
    String imageUrl;
    int category;
    int style;
}
