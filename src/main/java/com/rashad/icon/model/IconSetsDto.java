package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IconSetsDto {

    private Long id;
    private String name;
    private Integer categoryCount = 0;
    private List<IconDto> icons;
    private Set<Long> categoryIds;

    public IconSetsDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.icons = new ArrayList<>();
        this.categoryIds = new HashSet<>();
    }

    public void addIcon(IconDto icon) {
        icons.add(icon);
    }

    public Set<Long> getCategoryIds() {
        return categoryIds;
    }
}
