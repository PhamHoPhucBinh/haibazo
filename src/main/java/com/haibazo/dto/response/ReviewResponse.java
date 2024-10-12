package com.haibazo.dto.response;

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
public class ReviewResponse {
    @Enumerated(EnumType.STRING)
    Rating rating;
    String comment;
}
