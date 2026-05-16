import { ChangeDetectionStrategy, Component, input } from '@angular/core';

@Component({
    selector: 'app-empty-state',
    standalone: true,
    template: `
    <section class="empty-state">
      <p class="empty-state__title">{{ title() }}</p>
      <p class="empty-state__description">{{ description() }}</p>
    </section>
  `,
    styles: `
    .empty-state {
      padding: 2rem;
      border-radius: 1.5rem;
      border: 1px dashed rgba(218, 189, 140, 0.3);
      background: rgba(14, 23, 26, 0.55);
      text-align: center;
      color: var(--text-secondary);
    }

    .empty-state__title {
      margin: 0 0 0.45rem;
      font-size: 1.1rem;
      color: var(--text-primary);
      font-weight: 700;
    }

    .empty-state__description {
      margin: 0;
      line-height: 1.6;
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class EmptyStateComponent {
    readonly title = input.required<string>();
    readonly description = input.required<string>();
}