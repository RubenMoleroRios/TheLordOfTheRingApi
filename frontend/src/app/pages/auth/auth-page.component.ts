import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { finalize } from 'rxjs';

import { LoginFormValue, RegisterFormValue, RegistrationSuccessState } from '../../models/auth.models';
import { AuthService } from '../../services/auth.service';
import { ErrorAlertComponent } from '../../shared/components/error-alert.component';
import { LoadingSpinnerComponent } from '../../shared/components/loading-spinner.component';
import { extractApiError } from '../../shared/utils/http-error.util';

@Component({
    selector: 'app-auth-page',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule, ErrorAlertComponent, LoadingSpinnerComponent],
    template: `
    <section class="auth-card">
      <div class="auth-card__tabs">
        <button type="button" [class.active]="activeTab() === 'login'" (click)="activeTab.set('login')">Login</button>
        <button type="button" [class.active]="activeTab() === 'register'" (click)="activeTab.set('register')">Register</button>
      </div>

      <header class="auth-card__header">
        <p class="auth-card__eyebrow">Acceso</p>
        <h2>{{ activeTab() === 'login' ? 'Inicia sesión para explorar héroes' : 'Crea tu cuenta de tipo USER' }}</h2>
        <p>
          {{
            activeTab() === 'login'
              ? 'Accede con tu email y contraseña. El frontend guarda automáticamente el token JWT de la API.'
              : 'El registro público del backend asigna automáticamente el rol USER. No existe selector de roles.'
          }}
        </p>
      </header>

      <form *ngIf="activeTab() === 'login'" [formGroup]="loginForm" (ngSubmit)="submitLogin()" class="auth-form">
        <label>
          <span>Email</span>
          <input type="email" formControlName="email" placeholder="frodo@shire.me" />
        </label>

        <label>
          <span>Contraseña</span>
          <input type="password" formControlName="password" placeholder="Introduce tu contraseña" />
        </label>

        <app-error-alert *ngIf="loginError()" [message]="loginError()!" />
        <app-loading-spinner *ngIf="isLoginLoading()" [inline]="true" label="Iniciando sesión..." />

        <button type="submit" [disabled]="isLoginLoading()">Entrar</button>
      </form>

      <form *ngIf="activeTab() === 'register'" [formGroup]="registerForm" (ngSubmit)="submitRegister()" class="auth-form">
        <label>
          <span>Nombre</span>
          <input type="text" formControlName="name" placeholder="Samwise Gamgee" />
        </label>

        <label>
          <span>Email</span>
          <input type="email" formControlName="email" placeholder="samwise@lotr.local" />
        </label>

        <label>
          <span>Contraseña</span>
          <input type="password" formControlName="password" placeholder="Mínimo 9 caracteres" />
          <small>La contraseña debe tener al menos 9 caracteres.</small>
        </label>

        <app-error-alert *ngIf="registerError()" [message]="registerError()!" />
        <app-loading-spinner *ngIf="isRegisterLoading()" [inline]="true" label="Registrando usuario USER..." />

        <button type="submit" [disabled]="isRegisterLoading()">Crear cuenta USER</button>
      </form>
    </section>
  `,
    styles: `
    .auth-card {
      width: 100%;
      display: grid;
      gap: 1.35rem;
      padding: 1.65rem;
      border-radius: 1.75rem;
      background: rgba(6, 10, 11, 0.82);
      border: 1px solid rgba(229, 197, 138, 0.12);
      box-shadow: 0 24px 60px rgba(0, 0, 0, 0.32);
    }

    .auth-card__tabs {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 0.75rem;
    }

    .auth-card__tabs button,
    .auth-form button {
      border: 0;
      border-radius: 1rem;
      cursor: pointer;
    }

    .auth-card__tabs button {
      padding: 0.95rem;
      background: rgba(255, 255, 255, 0.04);
      color: var(--text-secondary);
      font-weight: 700;
      transition: background 180ms ease, color 180ms ease;
    }

    .auth-card__tabs button.active {
      background: linear-gradient(135deg, rgba(227, 186, 105, 0.28), rgba(173, 123, 52, 0.32));
      color: var(--text-primary);
    }

    .auth-card__eyebrow {
      margin: 0 0 0.45rem;
      text-transform: uppercase;
      letter-spacing: 0.15em;
      color: var(--accent-soft);
      font-size: 0.72rem;
    }

    .auth-card__header h2 {
      margin: 0;
      font-size: 1.9rem;
      line-height: 1.1;
    }

    .auth-card__header p:last-child {
      margin: 0.75rem 0 0;
      color: var(--text-secondary);
      line-height: 1.65;
    }

    .auth-form {
      display: grid;
      gap: 1rem;
    }

    label {
      display: grid;
      gap: 0.45rem;
    }

    span {
      font-size: 0.75rem;
      color: var(--text-muted);
      letter-spacing: 0.08em;
      text-transform: uppercase;
    }

    input {
      width: 100%;
      border: 1px solid rgba(229, 197, 138, 0.16);
      border-radius: 1rem;
      padding: 0.95rem 1rem;
      background: rgba(255, 255, 255, 0.04);
      color: var(--text-primary);
      outline: none;
      transition: border-color 180ms ease, box-shadow 180ms ease;
    }

    input:focus {
      border-color: rgba(227, 186, 105, 0.4);
      box-shadow: 0 0 0 4px rgba(227, 186, 105, 0.08);
    }

    small {
      color: var(--text-muted);
      line-height: 1.5;
    }

    .auth-form > button {
      padding: 1rem 1.2rem;
      background: linear-gradient(135deg, var(--accent), var(--accent-strong));
      color: #20160b;
      font-size: 0.98rem;
      font-weight: 800;
      box-shadow: 0 16px 28px rgba(173, 123, 52, 0.24);
      transition: transform 180ms ease, box-shadow 180ms ease, opacity 180ms ease;
    }

    .auth-form > button:hover:not(:disabled) {
      transform: translateY(-1px);
    }

    .auth-form > button:disabled {
      opacity: 0.7;
      cursor: wait;
    }
  `,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AuthPageComponent {
    private readonly formBuilder = inject(FormBuilder);
    private readonly authService = inject(AuthService);
    private readonly router = inject(Router);

    readonly activeTab = signal<'login' | 'register'>('login');
    readonly isLoginLoading = signal(false);
    readonly isRegisterLoading = signal(false);
    readonly loginError = signal<string | null>(null);
    readonly registerError = signal<string | null>(null);

    readonly loginForm = this.formBuilder.nonNullable.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]]
    });

    readonly registerForm = this.formBuilder.nonNullable.group({
        name: ['', [Validators.required, Validators.minLength(2)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(9)]]
    });

    submitLogin(): void {
        if (this.loginForm.invalid) {
            this.loginForm.markAllAsTouched();
            return;
        }

        this.isLoginLoading.set(true);
        this.loginError.set(null);

        const value = this.loginForm.getRawValue() as LoginFormValue;

        this.authService
            .login(value)
            .pipe(finalize(() => this.isLoginLoading.set(false)))
            .subscribe({
                next: () => {
                    this.loginForm.reset({ email: '', password: '' });
                    this.router.navigateByUrl('/app');
                },
                error: (error) => {
                    this.loginError.set(extractApiError(error, 'No se pudo iniciar sesión. Verifica tus credenciales.'));
                }
            });
    }

    submitRegister(): void {
        if (this.registerForm.invalid) {
            this.registerForm.markAllAsTouched();
            return;
        }

        this.isRegisterLoading.set(true);
        this.registerError.set(null);

        const value = this.registerForm.getRawValue() as RegisterFormValue;

        this.authService
            .register(value)
            .pipe(finalize(() => this.isRegisterLoading.set(false)))
            .subscribe({
                next: (response) => {
                    const successState: RegistrationSuccessState = {
                        name: value.name,
                        email: value.email
                    };

                    this.authService.rememberRegistrationSuccess(successState);
                    this.registerForm.reset({ name: '', email: '', password: '' });
                    this.router.navigateByUrl('/register-success', { state: successState });
                },
                error: (error) => {
                    this.registerError.set(
                        extractApiError(error, 'No se pudo registrar el usuario. Revisa el email o intenta de nuevo.')
                    );
                }
            });
    }
}