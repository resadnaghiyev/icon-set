package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class IconDto {

    private Long iconId;
    private String iconName;
    private String iconUnicode;

    public IconDto (Long iconId, String iconName, String iconUnicode) {
        this.iconId = iconId;
        this.iconName = iconName;
        this.iconUnicode = iconUnicode;
    }
}
