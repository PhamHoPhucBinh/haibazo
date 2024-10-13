package com.haibazo.dto.response;

import com.haibazo.enums.Color;
import com.haibazo.enums.Size;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String productName;
    double originalPrice;
    double discountPrice;
    @Enumerated(EnumType.STRING)
    Color color;
    @Enumerated(EnumType.STRING)
    Size size;
    String description;
    String imageUrl;
    List<ReviewResponse> reviews;
}
