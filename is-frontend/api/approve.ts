import axios, {AxiosError} from "axios";
import {useUserStore} from "@/app/context/user-store";
import {BACK_URL} from "@/api/auth";


export async function GetClaims(): Promise<string[]> {
    try{
        const res = await axios.get(BACK_URL + '/user/signup/request', {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return res.data.map((e: {login: string}) => e.login);
    }catch (e){
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return [];
    }
}

export async function ApproveClaim(login: string) {
    try {
        axios.patch(BACK_URL + '/user/approve?login=' + login, null, {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
    }catch (e){
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
    }
}

export async function RejectClaim(login: string) {
    try {
        axios.delete(BACK_URL + '/user/reject?login=' + login, {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
    }catch (e){
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
    }
}

