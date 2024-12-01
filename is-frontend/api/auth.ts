import axios, {AxiosError} from "axios";
import {useUserStore} from "@/app/context/user-store";

export const BACK_URL = 'http://localhost:8080/IS_Lab1-1.0-SNAPSHOT/api';

export interface Message {
    message: string;
    isError: boolean;
}

export async function Login(login: string, password: string): Promise<Message> {
    try {
        const res = await axios.post(BACK_URL + '/user/auth', {
            login: login,
            password: password
        })
        useUserStore.getState().Login(login, res.data.token, res.data.role === 'ADMIN');
        return {isError: false, message: JSON.stringify(res.data)};
    } catch (e: any) {
        const message = ((e as AxiosError)?.response?.data as string) || (e as AxiosError)?.message;
        return {isError: true, message};
    }
}

export async function SignUp(login: string, password: string, isAdmin: boolean): Promise<Message> {
    try {
        const res = await axios.post(BACK_URL + '/user/signup', {
            login: login,
            password: password,
            role: isAdmin ? 'ADMIN' : 'USER'
        })
        useUserStore.getState().Login(login, res.data.token, res.data.role === 'ADMIN');
        return {isError: false, message: JSON.stringify(res.data)};
    } catch (e: any) {
        const message = ((e as AxiosError)?.response?.data as string) || (e as AxiosError)?.message;
        if(+((e as AxiosError)?.code || 0) === 401 && message === 'Wait another admin approve!')
            return {isError: false, message: message};
        return {isError: true, message};
    }
}
