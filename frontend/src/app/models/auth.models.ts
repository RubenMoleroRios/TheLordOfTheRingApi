export interface AuthHttpResponse {
    token: string;
    userName: string;
}

export interface LoginFormValue {
    email: string;
    password: string;
}

export interface RegisterFormValue {
    name: string;
    email: string;
    password: string;
}

export interface SessionState {
    accessToken: string;
    userName: string;
    email: string;
}

export interface RegistrationSuccessState {
    name: string;
    email: string;
}