package com.rashad.icon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface IconResponse {

    Long getId();
    String getName();
    String getUnicode();
}
