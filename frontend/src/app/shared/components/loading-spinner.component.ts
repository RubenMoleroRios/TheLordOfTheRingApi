import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
    selector: 'app-loading-spinner',
    standalone: true,
    template: `
    <div class="spinner-shell" [class.spinner-shell--inline]="inline()">
      <span class="spinner-orbit"></span>
      <span class="spinner-label">{{ label() }}</span>
    </div>
  `,
    styles: `
    .spinner-shell {
      display: grid;
      place-items: center;
      gap: 0.85rem;
      padding: 2rem;
      color: var(--text-secondary);
    }

    .spinner-shell--inline {
      padding: 0;
    }

    .spinner-orbit {
      width: 2.75rem;
      height: 2.75rem;
      border-radius: 999px;
      border: 4px solid rgba(173, 123, 52, 0.18);
      border-top-color: var(--accent);
      animation: spin 0.8s linear infinite;
    }

    .spinner-label {
      font-size: 0.95rem;
      letter-spacing: 0.03em;
    }

    @keyframes spin {
      to {
        transform: rotate(360deg);
      }
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadingSpinnerComponent {
    readonly label = input('Cargando datos de la Tierra Media...');
    readonly inline = input(false);
}