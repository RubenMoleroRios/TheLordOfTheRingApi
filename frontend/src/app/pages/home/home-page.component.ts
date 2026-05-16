import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, DestroyRef, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { forkJoin, finalize } from 'rxjs';

import { HeroCardComponent } from '../../components/hero/hero-card.component';
import { HeroDetailComponent } from '../../components/hero/hero-detail.component';
import { HeroFiltersComponent } from '../../components/hero/hero-filters.component';
import { CatalogItem, Hero } from '../../models/hero.models';
import { HeroService } from '../../services/hero.service';
import { EmptyStateComponent } from '../../shared/components/empty-state.component';
import { ErrorAlertComponent } from '../../shared/components/error-alert.component';
import { LoadingSpinnerComponent } from '../../shared/components/loading-spinner.component';
import { withHeroImage } from '../../shared/utils/hero-image.util';
import { extractApiError } from '../../shared/utils/http-error.util';

@Component({
    selector: 'app-home-page',
    standalone: true,
    imports: [
        CommonModule,
        HeroFiltersComponent,
        HeroDetailComponent,
        HeroCardComponent,
        LoadingSpinnerComponent,
        ErrorAlertComponent,
        EmptyStateComponent
    ],
    template: `
    <section class="home-page">
      <header class="home-hero">
        <div>
          <p class="home-hero__eyebrow">Spring Boot + Angular</p>
          <h2>Conoce a los héroes de la tierra media.</h2>
        </div>

        <div class="home-hero__stats">
          <article>
            <span>Heroes visibles</span>
            <strong>{{ displayedHeroes().length }}</strong>
          </article>
          <article>
            <span>Razas</span>
            <strong>{{ breeds().length }}</strong>
          </article>
          <article>
            <span>Bandos</span>
            <strong>{{ sides().length }}</strong>
          </article>
        </div>
      </header>

      <app-hero-filters
        [heroes]="allHeroes()"
        [breeds]="breeds()"
        [sides]="sides()"
        [selectedHeroId]="selectedHeroId()"
        [selectedBreedId]="selectedBreedId()"
        [selectedSideId]="selectedSideId()"
        (heroChange)="filterByHero($event)"
        (breedChange)="filterByBreed($event)"
        (sideChange)="filterBySide($event)"
        (reset)="resetFilters()"
      />

      <app-loading-spinner *ngIf="isLoading()" label="Consultando heroes, razas y bandos..." />
      <app-error-alert *ngIf="errorMessage()" [message]="errorMessage()!" />

      <app-hero-detail *ngIf="featuredHero() && !isLoading()" [hero]="featuredHero()!" />

      <section class="home-grid" *ngIf="!isLoading() && !errorMessage() && displayedHeroes().length && !featuredHero()">
        <app-hero-card *ngFor="let hero of displayedHeroes()" [hero]="hero" (selected)="filterByHero($event)" />
      </section>

      <app-empty-state
        *ngIf="!isLoading() && !errorMessage() && !displayedHeroes().length"
        title="No se han encontrado heroes"
        description="Prueba con otro filtro o vuelve a la lista completa para seguir explorando la API."
      />
    </section>
  `,
    styles: `
    .home-page {
      display: grid;
      gap: 1.25rem;
      padding-bottom: 2rem;
    }

    .home-hero {
      display: grid;
      grid-template-columns: minmax(0, 1.4fr) minmax(31.5rem, 1fr);
      gap: 1rem;
      padding: 1.5rem;
      border-radius: 1.75rem;
      background:
        radial-gradient(circle at top left, rgba(227, 186, 105, 0.2), transparent 25%),
        linear-gradient(135deg, rgba(17, 28, 32, 0.98), rgba(9, 14, 16, 0.98));
      border: 1px solid rgba(229, 197, 138, 0.16);
    }

    .home-hero__eyebrow {
      margin: 0 0 0.45rem;
      text-transform: uppercase;
      letter-spacing: 0.16em;
      color: var(--accent-soft);
      font-size: 0.72rem;
    }

    .home-hero h2 {
      margin: 0;
      font-size: clamp(1.8rem, 4vw, 3rem);
      max-width: 16ch;
      line-height: 1.04;
    }

    .home-hero__stats {
      display: grid;
      grid-template-columns: repeat(3, minmax(10.25rem, 1fr));
      gap: 0.75rem;
      align-self: end;
    }

    .home-hero__stats article {
      padding: 1rem;
      border-radius: 1.1rem;
      background: rgba(255, 255, 255, 0.03);
      border: 1px solid rgba(229, 197, 138, 0.08);
    }

    .home-hero__stats span {
      display: block;
      color: var(--text-muted);
      text-transform: uppercase;
      letter-spacing: 0.08em;
      font-size: 0.72rem;
      margin-bottom: 0.35rem;
      white-space: nowrap;
    }

    .home-hero__stats strong {
      font-size: 1.7rem;
    }

    .home-grid {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 1rem;
    }

    @media (max-width: 980px) {
      .home-hero {
        grid-template-columns: 1fr;
      }

      .home-grid {
        grid-template-columns: repeat(2, minmax(0, 1fr));
      }
    }

    @media (max-width: 700px) {
      .home-hero__stats {
        grid-template-columns: 1fr;
      }

      .home-grid {
        grid-template-columns: 1fr;
      }
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomePageComponent {
    private readonly heroService = inject(HeroService);
    private readonly destroyRef = inject(DestroyRef);

    readonly isLoading = signal(true);
    readonly errorMessage = signal<string | null>(null);
    readonly allHeroes = signal<Hero[]>([]);
    readonly displayedHeroes = signal<Hero[]>([]);
    readonly breeds = signal<CatalogItem[]>([]);
    readonly sides = signal<CatalogItem[]>([]);
    readonly featuredHero = signal<Hero | null>(null);
    readonly selectedHeroId = signal('');
    readonly selectedBreedId = signal('');
    readonly selectedSideId = signal('');

    constructor() {
        this.loadInitialData();
    }

    filterByHero(heroId: string): void {
        if (!heroId) {
            this.resetFilters();
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set(null);

        this.heroService
            .getHeroById(heroId)
            .pipe(
                takeUntilDestroyed(this.destroyRef),
                finalize(() => this.isLoading.set(false))
            )
            .subscribe({
                next: (hero) => {
                    const heroWithImage = withHeroImage(hero);
                    this.selectedHeroId.set(heroId);
                    this.selectedBreedId.set('');
                    this.selectedSideId.set('');
                    this.displayedHeroes.set([heroWithImage]);
                    this.featuredHero.set(heroWithImage);
                },
                error: (error) => {
                    this.errorMessage.set(extractApiError(error, 'No se pudo cargar el detalle del heroe seleccionado.'));
                }
            });
    }

    filterByBreed(breedId: string): void {
        if (!breedId) {
            this.resetFilters();
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set(null);

        this.heroService
            .getHeroesByBreedId(breedId)
            .pipe(
                takeUntilDestroyed(this.destroyRef),
                finalize(() => this.isLoading.set(false))
            )
            .subscribe({
                next: (heroes) => {
                    const heroesWithImages = heroes.map(withHeroImage);
                    this.selectedHeroId.set('');
                    this.selectedBreedId.set(breedId);
                    this.selectedSideId.set('');
                    this.displayedHeroes.set(heroesWithImages);
                    this.featuredHero.set(null);
                },
                error: (error) => {
                    this.errorMessage.set(extractApiError(error, 'No se pudieron cargar los heroes de la raza seleccionada.'));
                }
            });
    }

    filterBySide(sideId: string): void {
        if (!sideId) {
            this.resetFilters();
            return;
        }

        this.isLoading.set(true);
        this.errorMessage.set(null);

        this.heroService
            .getHeroesBySideId(sideId)
            .pipe(
                takeUntilDestroyed(this.destroyRef),
                finalize(() => this.isLoading.set(false))
            )
            .subscribe({
                next: (heroes) => {
                    const heroesWithImages = heroes.map(withHeroImage);
                    this.selectedHeroId.set('');
                    this.selectedBreedId.set('');
                    this.selectedSideId.set(sideId);
                    this.displayedHeroes.set(heroesWithImages);
                    this.featuredHero.set(null);
                },
                error: (error) => {
                    this.errorMessage.set(extractApiError(error, 'No se pudieron cargar los heroes del bando seleccionado.'));
                }
            });
    }

    resetFilters(): void {
        this.selectedHeroId.set('');
        this.selectedBreedId.set('');
        this.selectedSideId.set('');
        this.featuredHero.set(null);
        this.displayedHeroes.set(this.allHeroes());
        this.errorMessage.set(null);
        this.isLoading.set(false);
    }

    private loadInitialData(): void {
        forkJoin({
            heroes: this.heroService.getAllHeroes(),
            breeds: this.heroService.getBreeds(),
            sides: this.heroService.getSides()
        })
            .pipe(
                takeUntilDestroyed(this.destroyRef),
                finalize(() => this.isLoading.set(false))
            )
            .subscribe({
                next: ({ heroes, breeds, sides }) => {
                    const heroesWithImages = heroes.map(withHeroImage);
                    this.allHeroes.set(heroesWithImages);
                    this.displayedHeroes.set(heroesWithImages);
                    this.breeds.set(breeds);
                    this.sides.set(sides);
                },
                error: (error) => {
                    this.errorMessage.set(extractApiError(error, 'No se pudo cargar la información inicial del dashboard.'));
                }
            });
    }
}