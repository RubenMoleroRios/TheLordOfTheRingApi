package com.ruben.lotr.api.controllers.v1.admin.hero;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruben.lotr.api.dto.response.HeroResponseDTO;
import com.ruben.lotr.api.http.ApiResponse;
import com.ruben.lotr.api.http.HttpStatusEnum;
import com.ruben.lotr.core.hero.application.usecase.AdminCreateHeroCommand;
import com.ruben.lotr.core.hero.application.usecase.AdminUpdateHeroCommand;
import com.ruben.lotr.core.hero.application.usecase.CreateHeroUseCase;
import com.ruben.lotr.core.hero.application.usecase.DeleteHeroUseCase;
import com.ruben.lotr.core.hero.application.usecase.UpdateHeroUseCase;
import com.ruben.lotr.core.hero.domain.model.Hero;

@RestController
@RequestMapping("/v1/admin/heroes")
public class AdminHeroesController {

    private final CreateHeroUseCase createHeroUseCase;
    private final UpdateHeroUseCase updateHeroUseCase;
    private final DeleteHeroUseCase deleteHeroUseCase;

    public AdminHeroesController(
            CreateHeroUseCase createHeroUseCase,
            UpdateHeroUseCase updateHeroUseCase,
            DeleteHeroUseCase deleteHeroUseCase) {
        this.createHeroUseCase = createHeroUseCase;
        this.updateHeroUseCase = updateHeroUseCase;
        this.deleteHeroUseCase = deleteHeroUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody AdminHeroUpsertRequest request) {
        Hero hero = createHeroUseCase.execute(new AdminCreateHeroCommand(
                request.name(),
                request.lastName(),
                request.eyesColor(),
                request.hairColor(),
                request.height(),
                request.description(),
                request.breedId(),
                request.sideId()));

        return ApiResponse.success(HttpStatusEnum.CREATED, HeroResponseDTO.from(hero), "Hero successfully created.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable String id,
            @RequestBody AdminHeroUpsertRequest request) {
        Hero hero = updateHeroUseCase.execute(new AdminUpdateHeroCommand(
                id,
                request.name(),
                request.lastName(),
                request.eyesColor(),
                request.hairColor(),
                request.height(),
                request.description(),
                request.breedId(),
                request.sideId()));

        return ApiResponse.success(HttpStatusEnum.OK, HeroResponseDTO.from(hero), "Hero successfully updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        deleteHeroUseCase.execute(id);
        return ApiResponse.success(HttpStatusEnum.OK, "", "Hero successfully deleted.");
    }
}