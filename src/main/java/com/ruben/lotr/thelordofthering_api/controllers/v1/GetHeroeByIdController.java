package com.ruben.lotr.thelordofthering_api.controllers.v1;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.core.character.application.response.dto.HeroDTO;
import com.ruben.lotr.core.character.application.response.presenter.HeroResponsePresenter;
import com.ruben.lotr.core.character.application.usecase.GetHeroeByIdUseCase;
import com.ruben.lotr.thelordofthering_api.http.ApiResponse;
import com.ruben.lotr.thelordofthering_api.http.HttpStatusEnum;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/heroes")
public class GetHeroeByIdController {

    private final GetHeroeByIdUseCase getHeroeByIdUseCase;
    private final HeroResponsePresenter presenter;

    public GetHeroeByIdController(
            GetHeroeByIdUseCase getHeroeByIdUseCase,
            HeroResponsePresenter presenter) {
        this.getHeroeByIdUseCase = getHeroeByIdUseCase;
        this.presenter = presenter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> execute(@PathVariable String id) {
        HeroDTO heroe = getHeroeByIdUseCase.execute(id);

        return ApiResponse.success(
                HttpStatusEnum.OK,
                presenter.toMap(heroe),
                "Hero found retrieved.");
    }

}
