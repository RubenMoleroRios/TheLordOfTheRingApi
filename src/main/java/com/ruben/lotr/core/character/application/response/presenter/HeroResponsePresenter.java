package com.ruben.lotr.core.character.application.response.presenter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ruben.lotr.core.character.application.response.dto.HeroDTO;

@Component
public class HeroResponsePresenter {

    public Map<String, Object> toMap(HeroDTO dto) {
        Map<String, Object> heroMap = new java.util.HashMap<>();
        heroMap.put("id", dto.getId());
        heroMap.put("name", dto.getName());
        heroMap.put("last_name", dto.getLastName());
        heroMap.put("breed_name", dto.getBreedName());
        heroMap.put("side_name", dto.getSideName());
        heroMap.put("eyes_color", dto.getEyesColor());
        heroMap.put("hair_color", dto.getHairColor());
        heroMap.put("height", dto.getHeight());
        heroMap.put("description", dto.getDescription());
        return heroMap;
    }

    public List<Map<String, Object>> toCollection(List<HeroDTO> dtos) {
        return dtos.stream().map(this::toMap).toList();
    }
}