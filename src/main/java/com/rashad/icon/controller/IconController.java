package com.rashad.icon.controller;

import com.rashad.icon.model.IconSetDto;
import com.rashad.icon.model.IconSetaDto;
import com.rashad.icon.service.IconService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/icon")
@RequiredArgsConstructor
public class IconController {

    private final IconService iconService;

    @GetMapping
    public ResponseEntity<?> getAllIconSet() {
        return iconService.getAllIconSet();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllIconSets() {
        return iconService.findAllIconSet();
    }

    @GetMapping("/{id}")
    public IconSetDto getOneIconSet(@PathVariable Long id) {
        return iconService.findOneIconSet(id);
    }
}
