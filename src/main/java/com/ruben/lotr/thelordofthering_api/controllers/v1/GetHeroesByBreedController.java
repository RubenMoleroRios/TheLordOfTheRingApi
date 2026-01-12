package com.ruben.lotr.thelordofthering_api.controllers.v1;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.core.character.application.response.presenter.HeroResponsePresenter;
import com.ruben.lotr.core.character.application.usecase.GetHeroesByBreedUseCase;
import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/v1/breeds/{breedId}/heroes")
public class GetHeroesByBreedController {

    private final GetHeroesByBreedUseCase getHeroesByBreedUseCase;
    private final HeroResponsePresenter presenter;

    public GetHeroesByBreedController(
            GetHeroesByBreedUseCase getHeroesByBreedUseCase,
            HeroResponsePresenter presenter) {
        this.getHeroesByBreedUseCase = getHeroesByBreedUseCase;
        this.presenter = presenter;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String breedId) {
        List<HeroDTO> dtos = getHeroesByBreedUseCase.execute(breedId);
        return ApiResponse.success(
                HttpStatusEnum.OK,
                presenter.toCollection(dtos),
                "Heroes successfully retrieved.");
    }
}
