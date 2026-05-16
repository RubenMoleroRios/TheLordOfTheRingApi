import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, computed, input, output } from '@angular/core';

import { Hero } from '../../models/hero.models';

@Component({
    selector: 'app-hero-card',
    standalone: true,
    imports: [CommonModule],
    template: `
		<article class="hero-card" (click)="selected.emit(hero().id)">
			<div class="hero-card__visual">
				<img *ngIf="hero().imageUrl; else imagePlaceholder" [src]="hero().imageUrl!" [alt]="fullName()" />
				<ng-template #imagePlaceholder>
					<span>Imagen próximamente</span>
				</ng-template>
			</div>

			<div class="hero-card__body">
				<div class="hero-card__meta">
					<span>{{ hero().breedName }}</span>
					<span>{{ hero().sideName }}</span>
				</div>

				<h3>{{ fullName() }}</h3>
				<p>{{ hero().description }}</p>

				<dl>
					<div>
						<dt>Altura</dt>
						<dd>{{ hero().height }} m</dd>
					</div>
					<div>
						<dt>Ojos</dt>
						<dd>{{ hero().eyesColor }}</dd>
					</div>
					<div>
						<dt>Pelo</dt>
						<dd>{{ hero().hairColor }}</dd>
					</div>
				</dl>
			</div>
		</article>
	`,
    styles: `
		:host {
			display: block;
			height: 100%;
		}

		.hero-card {
			display: grid;
			grid-template-rows: 12rem 1fr;
			gap: 1rem;
			padding: 1rem;
			height: 100%;
			border-radius: 1.6rem;
			border: 1px solid rgba(229, 197, 138, 0.16);
			background: linear-gradient(180deg, rgba(18, 28, 31, 0.96), rgba(10, 16, 18, 0.98));
			box-shadow: 0 18px 40px rgba(6, 10, 12, 0.28);
			cursor: pointer;
			transition: transform 180ms ease, border-color 180ms ease, box-shadow 180ms ease;
			animation: float-up 380ms ease both;
		}

		.hero-card:hover {
			transform: translateY(-4px);
			border-color: rgba(229, 197, 138, 0.34);
			box-shadow: 0 22px 44px rgba(6, 10, 12, 0.36);
		}

		.hero-card__visual {
			height: 100%;
			border-radius: 1.2rem;
			overflow: hidden;
			background:
				radial-gradient(circle at top, rgba(227, 186, 105, 0.26), transparent 45%),
				linear-gradient(135deg, rgba(142, 99, 31, 0.3), rgba(29, 40, 43, 0.72));
			display: grid;
			place-items: center;
			color: rgba(255, 240, 210, 0.72);
			font-size: 0.88rem;
			letter-spacing: 0.08em;
			text-transform: uppercase;
		}

		.hero-card__visual img {
			width: 100%;
			height: 100%;
			object-fit: cover;
			display: block;
		}

		.hero-card__body {
			display: grid;
			grid-template-rows: auto auto 1fr auto;
		}

		.hero-card__body h3 {
			margin: 0;
			font-size: 1.35rem;
		}

		.hero-card__body p {
			margin: 0.85rem 0 1rem;
			color: var(--text-secondary);
			line-height: 1.6;
			display: -webkit-box;
			-webkit-line-clamp: 4;
			-webkit-box-orient: vertical;
			overflow: hidden;
		}

		.hero-card__meta {
			display: flex;
			gap: 0.6rem;
			flex-wrap: wrap;
		}

		.hero-card__meta span {
			padding: 0.35rem 0.75rem;
			border-radius: 999px;
			background: rgba(227, 186, 105, 0.14);
			color: var(--accent-soft);
			font-size: 0.76rem;
			letter-spacing: 0.06em;
			text-transform: uppercase;
		}

		dl {
			display: grid;
			grid-template-columns: repeat(3, minmax(0, 1fr));
			gap: 0.75rem;
			margin: 0;
		}

		dl div {
			min-width: 0;
		}

		dt {
			color: var(--text-muted);
			font-size: 0.75rem;
			text-transform: uppercase;
			letter-spacing: 0.08em;
			margin-bottom: 0.2rem;
		}

		dd {
			margin: 0;
			color: var(--text-primary);
			font-weight: 600;
			line-height: 1.35;
			overflow-wrap: anywhere;
		}

		@media (max-width: 640px) {
			dl {
				grid-template-columns: 1fr;
			}
		}

		@keyframes float-up {
			from {
				opacity: 0;
				transform: translateY(14px);
			}

			to {
				opacity: 1;
				transform: translateY(0);
			}
		}
	`,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeroCardComponent {
    readonly hero = input.required<Hero>();
    readonly selected = output<string>();
    readonly fullName = computed(() => `${this.hero().name} ${this.hero().lastName}`.trim());
}