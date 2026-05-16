import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { AuthService } from '../services/auth.service';

function resolveApiBaseUrl(): string {
    const configured = window.__appConfig?.apiBaseUrl?.trim() || 'http://localhost:9525/api';
    return configured.endsWith('/') ? configured.slice(0, -1) : configured;
}

export const authTokenInterceptor: HttpInterceptorFn = (request, next) => {
    const authService = inject(AuthService);
    const accessToken = authService.getAccessToken();
    const apiBaseUrl = resolveApiBaseUrl();

    if (!accessToken || !request.url.startsWith(apiBaseUrl)) {
        return next(request);
    }

    const isPublicAuthRoute = request.url.endsWith('/v1/auth/login') || request.url.endsWith('/v1/auth/register');

    if (isPublicAuthRoute) {
        return next(request);
    }

    return next(
        request.clone({
            setHeaders: {
                Authorization: `Bearer ${accessToken}`
            }
        })
    );
};