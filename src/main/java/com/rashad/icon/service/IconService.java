package com.rashad.icon.service;

import com.rashad.icon.entity.IconSet;
import com.rashad.icon.model.AllIconSetDto;
import com.rashad.icon.model.CategoryDto;
import com.rashad.icon.model.IconDto;
import com.rashad.icon.model.IconResponse;
import com.rashad.icon.model.IconSetDto;
import com.rashad.icon.model.IconSetaDto;
import com.rashad.icon.model.IconSetResponse;
import com.rashad.icon.model.IconSetsDto;
import com.rashad.icon.repository.IconCategoryRepo;
import com.rashad.icon.repository.IconRepo;
import com.rashad.icon.repository.IconSetCategoryRepo;
import com.rashad.icon.repository.IconSetRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IconService {

    private final IconRepo iconRepo;
    private final IconSetRepo iconSetRepo;
    private final IconCategoryRepo iconCategoryRepo;
    private final IconSetCategoryRepo iconSetCategoryRepo;

    @PersistenceContext
    private EntityManager entityManager;


    public ResponseEntity<?> getAllIconSet() {
        String query = "SELECT i.id, i.name, STRING_AGG(CAST(isc.id AS TEXT), ', ') AS iconSetCategoryIDs " +
                "FROM icon_set i " +
                "LEFT JOIN icon_set_categories isc ON i.id = isc.set_id " +
                "GROUP BY i.id";
        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> resultSet = nativeQuery.getResultList();
        List<IconSetResponse> iconSetResponses = new ArrayList<>();
        for (Object[] result : resultSet) {
            Long id = ((Number) result[0]).longValue();
            String name = (String) result[1];
            String setCategoryIDs = (String) result[2];
            List<Long> setCategoryIdsList = new ArrayList<>();
            List<IconResponse> icons = new ArrayList<>();
            if (setCategoryIDs != null && !setCategoryIDs.isEmpty()) {
                setCategoryIdsList = Arrays.stream(setCategoryIDs.split(", ")).map(Long::parseLong).toList();
                icons = iconRepo.findBySetCategoryIds(setCategoryIdsList);
            }
            iconSetResponses.add(IconSetResponse.builder().id(id).name(name)
                    .categoryCount(setCategoryIdsList.size()).icons(icons).build());
        }
        return ResponseEntity.status(200).body(iconSetResponses);
    }


    public IconSetDto findOneIconSet(Long id) {
        String jpql = "SELECT NEW com.rashad.icon.model.IconSetaDto(i.id, i.name, c.iconCategory.id, c.iconCategory.name, icon.id, icon.name, icon.unicode) " +
                "FROM IconSet i " +
                "LEFT JOIN i.iconSetCategories c " +
                "LEFT JOIN c.icons icon " +
                "WHERE i.id = :id";
        TypedQuery<IconSetaDto> query = entityManager.createQuery(jpql, IconSetaDto.class);
        query.setParameter("id", id);
        List<IconSetaDto> result = query.getResultList();
        if (result.isEmpty()) {
            throw new IllegalStateException("bos icon seti");
        }
        IconSetDto iconSet = new IconSetDto(result.get(0).getIconSetId(), result.get(0).getIconSetName());
        for (IconSetaDto item : result) {
            Long categoryId = item.getCategoryId();
            IconDto icon = new IconDto(item.getIconId(), item.getIconName(), item.getIconUnicode());
            CategoryDto category = iconSet.getCategory(categoryId);
            if (category == null) {
                category = new CategoryDto(categoryId, item.getCategoryName());
                iconSet.addCategory(category);
            }
            category.addIcon(icon);
        }
        return iconSet;
    }

    public ResponseEntity<?> findAllIconSet() {
        String jpql = "SELECT NEW com.rashad.icon.model.IconSetaDto(i.id, i.name, c.iconCategory.id, c.iconCategory.name, icon.id, icon.name, icon.unicode) " +
                "FROM IconSet i " +
                "LEFT JOIN i.iconSetCategories c " +
                "LEFT JOIN c.icons icon " +
                "WHERE icon.id IN (" +
                "    SELECT icon2.id FROM IconSet i2 " +
                "    LEFT JOIN i2.iconSetCategories c2 " +
                "    LEFT JOIN c2.icons icon2 " +
                "    WHERE i2.id = i.id " + // Match the IconSet
                "    ORDER BY icon2.id LIMIT 20" +
                ")";
        TypedQuery<IconSetaDto> query = entityManager.createQuery(jpql, IconSetaDto.class);
        List<IconSetaDto> result = query.getResultList();
        if (result.isEmpty()) {
            throw new IllegalStateException("bos icon seti");
        }

        AllIconSetDto iconSet = new AllIconSetDto();

        for (IconSetaDto item : result) {
            Long iconSetId = item.getIconSetId();
            IconDto icon = new IconDto(item.getIconId(), item.getIconName(), item.getIconUnicode());
            IconSetsDto setsDto = iconSet.getIconSet(iconSetId);
            if (setsDto == null) {
                setsDto = new IconSetsDto(iconSetId, item.getIconSetName());
                iconSet.addIconSet(setsDto);
            }
            setsDto.addIcon(icon);
            setsDto.getCategoryIds().add(item.getCategoryId());
            setsDto.setCategoryCount(setsDto.getCategoryIds().size());
        }

        return ResponseEntity.status(200).body(iconSet);
    }

}
