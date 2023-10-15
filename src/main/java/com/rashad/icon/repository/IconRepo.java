package com.rashad.icon.repository;

import com.rashad.icon.entity.Icon;
import com.rashad.icon.model.IconResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IconRepo extends JpaRepository<Icon, Long> {

    @Query("SELECT i FROM Icon i WHERE i.iconSetCategory.id IN :setCategoryIdsList ORDER BY i.id LIMIT 20")
    List<IconResponse> findBySetCategoryIds(List<Long> setCategoryIdsList);
}
