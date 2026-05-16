import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { RegistrationSuccessState } from '../../models/auth.models';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-register-success-page',
    standalone: true,
    imports: [CommonModule, RouterLink],
    template: `
    <section class="success-card" *ngIf="successState as state; else missingState">
      <p class="success-card__eyebrow">Registro completado</p>
      <h2>Cuenta USER creada correctamente.</h2>
      <p class="success-card__lead">Ya puedes volver al login e iniciar sesión con tu email y contraseña.</p>

      <div class="success-card__summary">
        <article>
          <span>Nombre</span>
          <strong>{{ state.name }}</strong>
        </article>
        <article>
          <span>Email</span>
          <strong>{{ state.email }}</strong>
        </article>
      </div>

      <div class="success-card__actions">
        <button type="button" (click)="clearAndReturn()">Volver al login</button>
      </div>
    </section>

    <ng-template #missingState>
      <section class="success-card success-card--empty">
        <h2>No hay un registro reciente para mostrar.</h2>
        <a routerLink="/auth">Ir a autenticación</a>
      </section>
    </ng-template>
  `,
    styles: `
    .success-card {
      width: 100%;
      display: grid;
      gap: 1.25rem;
      padding: 1.8rem;
      border-radius: 1.8rem;
      background:
        radial-gradient(circle at top right, rgba(227, 186, 105, 0.22), transparent 24%),
        linear-gradient(135deg, rgba(12, 20, 22, 0.98), rgba(8, 12, 14, 0.98));
      border: 1px solid rgba(229, 197, 138, 0.16);
      box-shadow: 0 24px 64px rgba(4, 8, 10, 0.34);
    }

    .success-card__eyebrow {
      margin: 0;
      color: var(--accent-soft);
      text-transform: uppercase;
      letter-spacing: 0.14em;
      font-size: 0.74rem;
    }

    h2,
    .success-card__lead {
      margin: 0;
    }

    .success-card__lead {
      color: #ffe4b6;
      line-height: 1.6;
    }

    .success-card__summary {
      display: grid;
      grid-template-columns: 1fr;
      gap: 0.9rem;
    }

    .success-card__summary article {
      padding: 1rem;
      border-radius: 1.1rem;
      background: rgba(255, 255, 255, 0.03);
      border: 1px solid rgba(229, 197, 138, 0.1);
    }

    span {
      display: block;
      color: var(--text-muted);
      text-transform: uppercase;
      letter-spacing: 0.08em;
      font-size: 0.72rem;
      margin-bottom: 0.4rem;
    }

    strong {
      word-break: break-word;
      color: var(--text-primary);
    }

    .success-card__actions {
      display: grid;
    }

    button {
      border-radius: 999px;
      padding: 0.85rem 1.15rem;
      font-weight: 700;
    }

    button {
      border: 0;
      background: linear-gradient(135deg, var(--accent), var(--accent-strong));
      color: #20160b;
      cursor: pointer;
    }

    .success-card--empty {
      justify-items: start;
    }

  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterSuccessPageComponent {
    private readonly router = inject(Router);
    private readonly authService = inject(AuthService);

    readonly successState = (history.state as RegistrationSuccessState | undefined)?.email
        ? (history.state as RegistrationSuccessState)
        : this.authService.getRegistrationSuccess();

    clearAndReturn(): void {
        this.authService.clearRegistrationSuccess();
        this.router.navigateByUrl('/auth');
    }
}