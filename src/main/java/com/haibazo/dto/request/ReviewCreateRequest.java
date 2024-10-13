package com.haibazo.dto.request;

import com.haibazo.enums.Rating;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCreateRequest {
    @Enumerated(EnumType.STRING)
    Rating rating;
    String comment;
    int userId;
    int productId;
}
