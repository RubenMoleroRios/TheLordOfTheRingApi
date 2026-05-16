import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';

import { Hero } from '../../models/hero.models';

@Component({
    selector: 'app-hero-detail',
    standalone: true,
    imports: [CommonModule],
    template: `
    <section class="hero-detail">
      <div class="hero-detail__top">
        <div class="hero-detail__visual">
          <img *ngIf="hero().imageUrl; else detailPlaceholder" [src]="hero().imageUrl!" [alt]="fullName()" />
          <ng-template #detailPlaceholder>
            <span>Imagen próximamente</span>
          </ng-template>
        </div>

        <div class="hero-detail__headline">
          <p class="hero-detail__eyebrow">Ficha destacada</p>
          <h2>{{ fullName() }}</h2>
          <p>{{ hero().description }}</p>
        </div>
      </div>

      <div class="hero-detail__grid">
        <article>
          <span>Raza</span>
          <strong>{{ hero().breedName }}</strong>
        </article>
        <article>
          <span>Bando</span>
          <strong>{{ hero().sideName }}</strong>
        </article>
        <article>
          <span>Altura</span>
          <strong>{{ hero().height }} m</strong>
        </article>
        <article>
          <span>Color de ojos</span>
          <strong>{{ hero().eyesColor }}</strong>
        </article>
        <article>
          <span>Color de pelo</span>
          <strong>{{ hero().hairColor }}</strong>
        </article>
      </div>
    </section>
  `,
    styles: `
    .hero-detail {
      display: grid;
      gap: 1.5rem;
      padding: 1.8rem;
      border-radius: 1.75rem;
      background:
        radial-gradient(circle at top right, rgba(227, 186, 105, 0.24), transparent 28%),
        linear-gradient(135deg, rgba(17, 28, 32, 0.98), rgba(9, 14, 16, 0.98));
      border: 1px solid rgba(229, 197, 138, 0.18);
      box-shadow: 0 18px 44px rgba(4, 10, 11, 0.32);
    }

    .hero-detail__top {
      display: grid;
      grid-template-columns: minmax(14rem, 20rem) minmax(0, 1fr);
      gap: 1.5rem;
      align-items: stretch;
    }

    .hero-detail__visual {
      min-height: 18rem;
      border-radius: 1.4rem;
      overflow: hidden;
      background:
        radial-gradient(circle at top, rgba(227, 186, 105, 0.26), transparent 45%),
        linear-gradient(135deg, rgba(142, 99, 31, 0.3), rgba(29, 40, 43, 0.72));
      display: grid;
      place-items: center;
      color: rgba(255, 240, 210, 0.72);
      text-transform: uppercase;
      letter-spacing: 0.08em;
      font-size: 0.85rem;
    }

    .hero-detail__visual img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      display: block;
    }

    .hero-detail__eyebrow {
      margin: 0 0 0.45rem;
      color: var(--accent-soft);
      text-transform: uppercase;
      letter-spacing: 0.14em;
      font-size: 0.72rem;
    }

    .hero-detail__headline h2 {
      margin: 0;
      font-size: clamp(1.8rem, 4vw, 2.8rem);
    }

    .hero-detail__headline p:last-child {
      margin: 0.85rem 0 0;
      color: var(--text-secondary);
      line-height: 1.7;
    }

    .hero-detail__grid {
      display: grid;
      grid-template-columns: repeat(3, minmax(0, 1fr));
      gap: 1rem;
    }

    .hero-detail__grid article {
      padding: 1rem;
      border-radius: 1.2rem;
      background: rgba(255, 255, 255, 0.03);
      border: 1px solid rgba(229, 197, 138, 0.08);
    }

    .hero-detail__grid span {
      display: block;
      color: var(--text-muted);
      text-transform: uppercase;
      letter-spacing: 0.08em;
      font-size: 0.72rem;
      margin-bottom: 0.35rem;
    }

    .hero-detail__grid strong {
      word-break: break-word;
      font-size: 1rem;
    }

    @media (max-width: 900px) {
      .hero-detail__top {
        grid-template-columns: 1fr;
      }

      .hero-detail__grid {
        grid-template-columns: repeat(2, minmax(0, 1fr));
      }
    }

    @media (max-width: 640px) {
      .hero-detail {
        padding: 1.25rem;
      }

      .hero-detail__grid {
        grid-template-columns: 1fr;
      }
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeroDetailComponent {
    readonly hero = input.required<Hero>();
    readonly fullName = computed(() => `${this.hero().name} ${this.hero().lastName}`.trim());
}