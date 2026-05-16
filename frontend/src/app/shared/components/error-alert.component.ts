import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
    selector: 'app-error-alert',
    standalone: true,
    template: `
    <section class="error-alert" role="alert">
      <p class="error-alert__eyebrow">Algo salió mal</p>
      <p class="error-alert__message">{{ message() }}</p>
    </section>
  `,
    styles: `
    .error-alert {
      border: 1px solid rgba(153, 43, 43, 0.28);
      background: linear-gradient(135deg, rgba(153, 43, 43, 0.12), rgba(38, 11, 11, 0.32));
      border-radius: 1.25rem;
      padding: 1rem 1.25rem;
      color: #ffd9d9;
      box-shadow: 0 14px 28px rgba(41, 10, 10, 0.22);
    }

    .error-alert__eyebrow {
      margin: 0 0 0.35rem;
      text-transform: uppercase;
      letter-spacing: 0.15em;
      font-size: 0.72rem;
      color: #ffb8b8;
    }

    .error-alert__message {
      margin: 0;
      line-height: 1.5;
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ErrorAlertComponent {
    readonly message = input.required<string>();
}