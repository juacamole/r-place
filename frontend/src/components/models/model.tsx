import {Role} from "../pages/Settings.tsx";

export type UserDataType = {
    "username": string;
    "password": string;
    "email": string;
    "role": Role;
    "biography": string;
    "placedPixels": number;
    "cpx": number;
    "cpy": number;
}

export type jwtResponseType = {
    "token": string;
}
