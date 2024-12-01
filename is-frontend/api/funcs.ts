import {useUserStore} from "@/app/context/user-store";
import axios, {AxiosError} from "axios";
import {BACK_URL} from "@/api/auth";
import {MusicGenre} from "@/app/context/music-store";
import {headers} from "next/headers";


export async function TotalAlbumsCount(): Promise<number> {
    try {
        const res = await axios.get(BACK_URL + '/domain/function/calculateTotalAlbumsCount', {headers: {
                'Authorization': 'Bearer ' + useUserStore.getState().token
            }});
        return res.data;
    }catch (e) {
        console.warn(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return 0;
    }
}

export async function AlbumsGreaterThan(count: number): Promise<number> {
    try {
        const res = await axios.get(BACK_URL + '/domain/function/countAlbumsGreaterThan?count=' + count, {headers: {
                'Authorization': 'Bearer ' + useUserStore.getState().token
            }});
        return res.data;
    }catch (e) {
        console.warn(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return 0;
    }
}

export async function BandsGenreCount(genre: MusicGenre): Promise<number> {
    try {
        const res = await axios.get(BACK_URL + '/domain/function/countMusicBandsByGenre?genre=' + genre, {headers: {
                'Authorization': 'Bearer ' + useUserStore.getState().token
            }});
        return res.data;
    }catch (e) {
        console.warn(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return 0;
    }
}

export async function IncreaseParticipants(id: number): Promise<number> {
    try {
        const res = await axios.patch(BACK_URL + '/domain/function/increaseParticipants?id=' + id, undefined, {headers: {
                'Authorization': 'Bearer ' + useUserStore.getState().token
            }});
        return res.data || 'успех';
    }catch (e) {
        console.warn(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        throw e;
    }
}