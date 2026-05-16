export interface ApiResponse<T> {
    timestamp: string;
    status: 'success' | 'error';
    message: string;
    data: T;
    error: string;
    code?: string;
}