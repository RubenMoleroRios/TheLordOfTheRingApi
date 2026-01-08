package com.ruben.lotr.thelordofthering_api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.core.character.application.response.presenter.HeroResponsePresenter;
import com.ruben.lotr.core.character.application.usecase.GetHeroesUseCase;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/heroes")
public class GetHeroesController {

    private final GetHeroesUseCase getHeroesUseCase;
    private final HeroResponsePresenter presenter;

    public GetHeroesController(
            GetHeroesUseCase getHeroesUseCase,
            HeroResponsePresenter presenter) {
        this.getHeroesUseCase = getHeroesUseCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute() {
        List<HeroDTO> dtos = getHeroesUseCase.execute();
        return ApiResponse.success(
                HttpStatusEnum.OK,
                presenter.toCollection(dtos),
                "Heroes successfully retrieved.");
    }
}
