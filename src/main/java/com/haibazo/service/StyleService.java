package com.haibazo.service;

import com.haibazo.model.Style;
import com.haibazo.repository.StyleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StyleService {

    StyleRepository styleRepository;

    public List<Style> getAllStyles() {
        return styleRepository.findAll();
    }

    public Optional<Style> getStyleById(Integer styleId) {
        return styleRepository.findById(styleId);
    }

    public Style saveStyle(Style style) {
        return styleRepository.save(style);
    }

    public void deleteStyle(Integer styleId) {
        styleRepository.deleteById(styleId);
    }
}
