import { HttpErrorResponse } from '@angular/common/http';

export function extractApiError(error: unknown, fallbackMessage: string): string {
    if (error instanceof HttpErrorResponse) {
        const apiMessage = error.error?.message;
        const apiCode = error.error?.code;

        if (typeof apiMessage === 'string' && apiMessage.trim()) {
            return apiCode ? `${apiMessage} (${apiCode})` : apiMessage;
        }

        if (error.status === 0) {
            return 'No se pudo contactar con la API. Revisa que el backend esté levantado y accesible.';
        }
    }

    return fallbackMessage;
}