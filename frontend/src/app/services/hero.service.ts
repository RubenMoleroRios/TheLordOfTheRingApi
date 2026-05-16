import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

import { ApiResponse } from '../models/api-response.model';
import { CatalogItem, Hero } from '../models/hero.models';

function resolveApiBaseUrl(): string {
    const configured = window.__appConfig?.apiBaseUrl?.trim() || 'http://localhost:9525/api';
    return configured.endsWith('/') ? configured.slice(0, -1) : configured;
}

@Injectable({ providedIn: 'root' })
export class HeroService {
    private readonly http = inject(HttpClient);
    private readonly apiBaseUrl = resolveApiBaseUrl();

    getAllHeroes(): Observable<Hero[]> {
        return this.http
            .get<ApiResponse<Hero[]>>(`${this.apiBaseUrl}/v1/heroes`)
            .pipe(map((response) => response.data));
    }

    getHeroById(heroId: string): Observable<Hero> {
        return this.http
            .get<ApiResponse<Hero>>(`${this.apiBaseUrl}/v1/heroes/${heroId}`)
            .pipe(map((response) => response.data));
    }

    getHeroesByBreedId(breedId: string): Observable<Hero[]> {
        return this.http
            .get<ApiResponse<Hero[]>>(`${this.apiBaseUrl}/v1/breeds/${breedId}/heroes`)
            .pipe(map((response) => response.data));
    }

    getHeroesBySideId(sideId: string): Observable<Hero[]> {
        return this.http
            .get<ApiResponse<Hero[]>>(`${this.apiBaseUrl}/v1/sides/${sideId}/heroes`)
            .pipe(map((response) => response.data));
    }

    getBreeds(): Observable<CatalogItem[]> {
        return this.http
            .get<ApiResponse<CatalogItem[]>>(`${this.apiBaseUrl}/v1/breeds`)
            .pipe(map((response) => response.data));
    }

    getSides(): Observable<CatalogItem[]> {
        return this.http
            .get<ApiResponse<CatalogItem[]>>(`${this.apiBaseUrl}/v1/sides`)
            .pipe(map((response) => response.data));
    }
}