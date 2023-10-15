package com.rashad.icon.repository;

import com.rashad.icon.entity.IconSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IconSetRepo extends JpaRepository<IconSet, Long> {

    @Query("SELECT i FROM IconSet i LEFT JOIN FETCH i.iconSetCategories")
    List<IconSet> findAllIcons();

    @Query("SELECT i FROM IconSet i LEFT JOIN FETCH i.iconSetCategories c LEFT JOIN FETCH c.icons WHERE i.id = :id")
    IconSet findIconById(Long id);
}
