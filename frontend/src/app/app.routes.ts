import { Routes } from '@angular/router';

import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    {
        path: '',
        loadComponent: () => import('./layouts/auth-layout.component').then((m) => m.AuthLayoutComponent),
        children: [
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'auth'
            },
            {
                path: 'auth',
                loadComponent: () => import('./pages/auth/auth-page.component').then((m) => m.AuthPageComponent)
            },
            {
                path: 'register-success',
                loadComponent: () =>
                    import('./pages/register-success/register-success-page.component').then(
                        (m) => m.RegisterSuccessPageComponent
                    )
            }
        ]
    },
    {
        path: 'app',
        canActivate: [authGuard],
        loadComponent: () => import('./layouts/shell-layout.component').then((m) => m.ShellLayoutComponent),
        children: [
            {
                path: '',
                loadComponent: () => import('./pages/home/home-page.component').then((m) => m.HomePageComponent)
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'auth'
    }
];
