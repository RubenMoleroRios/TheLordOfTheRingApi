import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CatalogItem, Hero } from '../../models/hero.models';

@Component({
    selector: 'app-hero-filters',
    standalone: true,
    imports: [CommonModule, FormsModule],
    template: `
    <section class="filters">
      <div class="filters__header">
        <div>
          <p class="filters__eyebrow">Explorador</p>
          <h2>Filtra la comunidad de heroes</h2>
        </div>

        <button type="button" class="filters__reset" (click)="reset.emit()">Ver todos</button>
      </div>

      <div class="filters__grid">
        <label>
          <span>Heroes</span>
          <select [ngModel]="selectedHeroId()" (ngModelChange)="heroChange.emit($event)">
            <option value="">Todos los heroes</option>
            <option *ngFor="let hero of heroes()" [value]="hero.id">{{ hero.name }} {{ hero.lastName }}</option>
          </select>
        </label>

        <label>
          <span>Bando</span>
          <select [ngModel]="selectedSideId()" (ngModelChange)="sideChange.emit($event)">
            <option value="">Todos los bandos</option>
            <option *ngFor="let side of sides()" [value]="side.id">{{ side.name }}</option>
          </select>
        </label>

        <label>
          <span>Raza</span>
          <select [ngModel]="selectedBreedId()" (ngModelChange)="breedChange.emit($event)">
            <option value="">Todas las razas</option>
            <option *ngFor="let breed of breeds()" [value]="breed.id">{{ breed.name }}</option>
          </select>
        </label>
      </div>
    </section>
  `,
    styles: `
    .filters {
      display: grid;
      gap: 1.25rem;
      padding: 1.4rem;
      border-radius: 1.6rem;
      background: rgba(10, 17, 19, 0.9);
      border: 1px solid rgba(229, 197, 138, 0.14);
    }

    .filters__header {
      display: flex;
      justify-content: space-between;
      align-items: end;
      gap: 1rem;
    }

    .filters__eyebrow {
      margin: 0 0 0.35rem;
      color: var(--accent-soft);
      text-transform: uppercase;
      letter-spacing: 0.14em;
      font-size: 0.72rem;
    }

    .filters__header h2 {
      margin: 0;
      font-size: clamp(1.2rem, 2.2vw, 1.8rem);
    }

    .filters__reset {
      border: 0;
      border-radius: 999px;
      padding: 0.8rem 1.15rem;
      background: rgba(227, 186, 105, 0.16);
      color: var(--text-primary);
      font-weight: 600;
      cursor: pointer;
      transition: background 180ms ease, transform 180ms ease;
    }

    .filters__reset:hover {
      background: rgba(227, 186, 105, 0.28);
      transform: translateY(-1px);
    }

    .filters__grid {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 1rem;
    }

    label {
      display: grid;
      gap: 0.5rem;
    }

    span {
      color: var(--text-muted);
      text-transform: uppercase;
      letter-spacing: 0.08em;
      font-size: 0.74rem;
    }

    select {
      width: 100%;
      border-radius: 1rem;
      border: 1px solid rgba(229, 197, 138, 0.18);
      background: rgba(12, 20, 22, 0.98);
      color: var(--text-primary);
      padding: 0.9rem 1rem;
      outline: none;
      appearance: none;
      box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.04);
    }

    select option {
      background: #0d171a;
      color: var(--text-primary);
    }

    select:focus {
      border-color: rgba(227, 186, 105, 0.42);
      box-shadow: 0 0 0 4px rgba(227, 186, 105, 0.08);
    }

    @media (max-width: 820px) {
      .filters__grid {
        grid-template-columns: 1fr;
      }

      .filters__header {
        align-items: start;
        flex-direction: column;
      }
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeroFiltersComponent {
    readonly heroes = input.required<Hero[]>();
    readonly breeds = input.required<CatalogItem[]>();
    readonly sides = input.required<CatalogItem[]>();
    readonly selectedHeroId = input('');
    readonly selectedBreedId = input('');
    readonly selectedSideId = input('');

    readonly heroChange = output<string>();
    readonly breedChange = output<string>();
    readonly sideChange = output<string>();
    readonly reset = output<void>();
}