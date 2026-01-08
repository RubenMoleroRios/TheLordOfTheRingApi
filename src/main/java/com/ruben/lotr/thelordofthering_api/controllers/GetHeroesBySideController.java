package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.core.character.application.response.presenter.HeroResponsePresenter;
import com.ruben.lotr.core.character.application.usecase.GetHeroesBySideUseCase;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/sides/{sideId}/heroes")
public class GetHeroesBySideController {

    private final GetHeroesBySideUseCase getHeroesBySideUseCase;
    private final HeroResponsePresenter presenter;

    public GetHeroesBySideController(
            GetHeroesBySideUseCase getHeroesBySideUseCase,
            HeroResponsePresenter presenter) {
        this.getHeroesBySideUseCase = getHeroesBySideUseCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String sideId) {
        List<HeroDTO> dtos = getHeroesBySideUseCase.execute(sideId);
        return ApiResponse.success(
                HttpStatusEnum.OK,
                presenter.toCollection(dtos),
                "Heroes successfully retrieved.");
    }

}
