import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

import { AuthService } from '../services/auth.service';

@Component({
    selector: 'app-shell-layout',
    standalone: true,
    imports: [RouterOutlet],
    template: `
    <div class="shell-layout">
      <header class="shell-header">
        <div>
          <p class="shell-header__eyebrow">Dashboard</p>
          <h1>Heroes de la Tierra Media</h1>
        </div>

        <div class="shell-header__actions">
          <div class="shell-header__user">
            <span>{{ userName() }}</span>
            <small>{{ email() }}</small>
          </div>

          <button type="button" (click)="logout()">Cerrar sesión</button>
        </div>
      </header>

      <main class="shell-main">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
    styles: `
    .shell-layout {
      min-height: 100vh;
      padding: 1.25rem;
    }

    .shell-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 1rem;
      padding: 1rem 1.25rem;
      margin-bottom: 1.25rem;
      border-radius: 1.5rem;
      background: rgba(8, 14, 16, 0.78);
      border: 1px solid rgba(229, 197, 138, 0.1);
      position: sticky;
      top: 1rem;
      z-index: 10;
      backdrop-filter: blur(18px);
    }

    .shell-header__eyebrow {
      margin: 0 0 0.25rem;
      text-transform: uppercase;
      letter-spacing: 0.14em;
      color: var(--accent-soft);
      font-size: 0.72rem;
    }

    .shell-header h1 {
      margin: 0;
      font-size: clamp(1.4rem, 3vw, 2rem);
    }

    .shell-header__actions {
      display: flex;
      gap: 0.85rem;
      align-items: center;
    }

    .shell-header__user {
      display: grid;
      justify-items: end;
    }

    .shell-header__user span {
      font-weight: 700;
    }

    .shell-header__user small {
      color: var(--text-muted);
    }

    button {
      border: 0;
      border-radius: 999px;
      padding: 0.85rem 1.15rem;
      background: linear-gradient(135deg, var(--accent), var(--accent-strong));
      color: #1f1407;
      font-weight: 800;
      cursor: pointer;
      transition: transform 180ms ease, box-shadow 180ms ease;
      box-shadow: 0 12px 24px rgba(173, 123, 52, 0.22);
    }

    button:hover {
      transform: translateY(-1px);
    }

    .shell-main {
      width: min(1280px, 100%);
      margin: 0 auto;
    }

    @media (max-width: 780px) {
      .shell-header,
      .shell-header__actions {
        flex-direction: column;
        align-items: stretch;
      }

      .shell-header__user {
        justify-items: start;
      }
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ShellLayoutComponent {
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router);

    readonly userName = computed(() => this.authService.session()?.userName ?? 'Compañero de la Comunidad');
    readonly email = computed(() => this.authService.session()?.email ?? '');

    logout(): void {
        this.authService.logout();
        this.router.navigateByUrl('/auth');
    }
}