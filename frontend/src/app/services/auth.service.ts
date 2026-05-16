import { HttpClient } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { Observable, tap } from 'rxjs';

import { ApiResponse } from '../models/api-response.model';
import {
    AuthHttpResponse,
    LoginFormValue,
    RegisterFormValue,
    RegistrationSuccessState,
    SessionState
} from '../models/auth.models';

const SESSION_STORAGE_KEY = 'lotr.session';
const REGISTER_SUCCESS_KEY = 'lotr.register-success';

function resolveApiBaseUrl(): string {
    const configured = window.__appConfig?.apiBaseUrl?.trim() || 'http://localhost:9525/api';
    return configured.endsWith('/') ? configured.slice(0, -1) : configured;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
    private readonly http = inject(HttpClient);
    private readonly apiBaseUrl = resolveApiBaseUrl();
    private readonly sessionState = signal<SessionState | null>(this.readSession());

    readonly session = computed(() => this.sessionState());
    readonly isAuthenticated = computed(() => Boolean(this.sessionState()?.accessToken));

    login(payload: LoginFormValue): Observable<ApiResponse<AuthHttpResponse>> {
        return this.http
            .post<ApiResponse<AuthHttpResponse>>(`${this.apiBaseUrl}/v1/auth/login`, {
                email: payload.email,
                password: payload.password
            })
            .pipe(
                tap((response) => {
                    this.persistSession({
                        accessToken: response.data.token,
                        userName: response.data.userName,
                        email: payload.email
                    });
                })
            );
    }

    register(payload: RegisterFormValue): Observable<ApiResponse<AuthHttpResponse>> {
        return this.http.post<ApiResponse<AuthHttpResponse>>(`${this.apiBaseUrl}/v1/auth/register`, payload);
    }

    logout(): void {
        localStorage.removeItem(SESSION_STORAGE_KEY);
        this.sessionState.set(null);
    }

    rememberRegistrationSuccess(payload: RegistrationSuccessState): void {
        sessionStorage.setItem(REGISTER_SUCCESS_KEY, JSON.stringify(payload));
    }

    getRegistrationSuccess(): RegistrationSuccessState | null {
        const raw = sessionStorage.getItem(REGISTER_SUCCESS_KEY);

        if (!raw) {
            return null;
        }

        try {
            return JSON.parse(raw) as RegistrationSuccessState;
        } catch {
            return null;
        }
    }

    clearRegistrationSuccess(): void {
        sessionStorage.removeItem(REGISTER_SUCCESS_KEY);
    }

    getAccessToken(): string | null {
        return this.sessionState()?.accessToken ?? null;
    }

    private persistSession(session: SessionState): void {
        localStorage.setItem(SESSION_STORAGE_KEY, JSON.stringify(session));
        this.sessionState.set(session);
    }

    private readSession(): SessionState | null {
        const raw = localStorage.getItem(SESSION_STORAGE_KEY);

        if (!raw) {
            return null;
        }

        try {
            return JSON.parse(raw) as SessionState;
        } catch {
            return null;
        }
    }
}