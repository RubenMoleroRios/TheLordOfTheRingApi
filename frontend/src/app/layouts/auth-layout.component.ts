import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
    selector: 'app-auth-layout',
    standalone: true,
    imports: [RouterOutlet],
    template: `
    <div class="auth-layout">
      <section class="auth-layout__content">
        <router-outlet></router-outlet>
      </section>
    </div>
  `,
    styles: `
    .auth-layout {
      min-height: 100vh;
      display: grid;
      place-items: center;
      padding: 1.5rem;
    }

    .auth-layout__content {
      width: min(100%, 36rem);
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AuthLayoutComponent { }