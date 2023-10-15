package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IconSetaDto {

    private Long iconSetId;
    private String iconSetName;
    private Long categoryId;
    private String categoryName;
    private Long iconId;
    private String iconName;
    private String iconUnicode;
}
