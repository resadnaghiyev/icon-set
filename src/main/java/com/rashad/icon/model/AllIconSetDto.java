package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AllIconSetDto {

    private List<IconSetsDto> iconSets;

    public AllIconSetDto() {
        this.iconSets = new ArrayList<>();
    }

    public void addIconSet(IconSetsDto iconSetsDto) {
        iconSets.add(iconSetsDto);
    }

    public IconSetsDto getIconSet(Long iconSetId) {
        for (IconSetsDto iconSet : iconSets) {
            if (iconSet.getId() == iconSetId) {
                return iconSet;
            }
        }
        return null;
    }
}
