package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IconSetDto {

    private Long iconSetId;
    private String iconSetName;
    private List<CategoryDto> categories;

    public IconSetDto(Long iconSetId, String iconSetName) {
        this.iconSetId = iconSetId;
        this.iconSetName = iconSetName;
        this.categories = new ArrayList<>();
    }

    public void addCategory(CategoryDto category) {
        categories.add(category);
    }

    public CategoryDto getCategory(Long categoryId) {
        for (CategoryDto category : categories) {
            if (category.getCategoryId() == categoryId) {
                return category;
            }
        }
        return null;
    }
}
