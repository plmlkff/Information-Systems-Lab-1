import {MusicBand, MusicGenre} from "@/app/context/music-store";
import axios, {AxiosError} from "axios";
import {BACK_URL, Message} from "@/api/auth";
import {useUserStore} from "@/app/context/user-store";

export enum BandColumn {
    ID = "ID",
    MUSIC_BAND_NAME = "MUSIC_BAND_NAME",
    MUSIC_BAND_CREATION_DATE = "MUSIC_BAND_CREATION_DATE",
    MUSIC_BAND_MUSIC_GENRE = "MUSIC_BAND_MUSIC_GENRE",
    MUSIC_BAND_NUMBER_OF_PARTICIPANTS = "MUSIC_BAND_NUMBER_OF_PARTICIPANTS",
    MUSIC_BAND_SINGLES_COUNT = "MUSIC_BAND_SINGLES_COUNT",
    MUSIC_BAND_DESCRIPTION = "MUSIC_BAND_DESCRIPTION",
    MUSIC_BAND_BEST_ALBUM_NAME = "MUSIC_BAND_BEST_ALBUM_NAME",
    MUSIC_BAND_BEST_ALBUM_SALES = "MUSIC_BAND_BEST_ALBUM_SALES",
    MUSIC_BAND_ALBUMS_COUNT = "MUSIC_BAND_ALBUMS_COUNT",
    MUSIC_BAND_ESTABLISHMENT_DATE = "MUSIC_BAND_ESTABLISHMENT_DATE",
    MUSIC_BAND_STUDIO_ADDRESS = "MUSIC_BAND_STUDIO_ADDRESS",
    MUSIC_BAND_COORDINATE_X = "MUSIC_BAND_COORDINATE_X",
    MUSIC_BAND_COORDINATE_Y = "MUSIC_BAND_COORDINATE_Y",
    MUSIC_BAND_USER_LOGIN = "MUSIC_BAND_USER_LOGIN",
    ENTITY_CHANGE_HISTORY_USER_LOGIN = "ENTITY_CHANGE_HISTORY_USER_LOGIN",
    ENTITY_CHANGE_HISTORY_OPERATION = "ENTITY_CHANGE_HISTORY_OPERATION",
    ENTITY_CHANGE_HISTORY_ENTITY_ID = "ENTITY_CHANGE_HISTORY_ENTITY_ID",
    ENTITY_CHANGE_HISTORY_TIME = "ENTITY_CHANGE_HISTORY_TIME",

}

export interface BandsSorting {
    sortDirection: "ASC" | "DESC";
    sortColumn: BandColumn;
    pageNumber?: number;
    pageSize?: number;
    criteria: {
        filteringColumn: BandColumn;
        filteringValue: string;
        andCriteria: boolean;
    }[];
}

export async function GetMusicBands(filter: BandsSorting): Promise<MusicBand[]> {
    try {
        const res = await axios.post(BACK_URL + '/domain/musicband/', filter,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return res.data.map((e: any) => {
            e.creationDate = new Date(+e.creationDate * 24 * 3600 * 1000);
            e.establishmentDate = e.establishmentDate != null ? new Date(e.establishmentDate) : null;
            return e
        });
    } catch (e) {
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return [];
    }
}

export async function CreateMusicBand(band: MusicBand): Promise<Message> {
    try {
        const payload: any = {...band};
        payload.establishmentDate = band.establishmentDate?.getTime();
        const res = await axios.post(BACK_URL + '/domain/musicband/create', payload,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return {isError: false, message: "success"};
    }catch (e){
        console.error(e)
        return {isError: true, message: 'Error while creating band'}
    }
}

export async function UpdateMusicBand(band: MusicBand): Promise<Message> {
    try {
        const payload: any = {...band};
        payload.establishmentDate = band.establishmentDate?.getTime();
        delete payload.creationDate;
        const res = await axios.patch(BACK_URL + '/domain/musicband/' + band.id, payload,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return {isError: false, message: "success"};
    }catch (e){
        return {isError: true, message: 'Error while updating band'}
    }
}

export interface HistoryRecord {
    "login": string,
    "operation": "UPDATE",
    "musicBandId": number,
    "time": Date
}

export async function GetHistory(id: number): Promise<HistoryRecord[]> {
    try {
        const payload: any = {
            criteria: [
                {
                    "filteringColumn": BandColumn.ENTITY_CHANGE_HISTORY_ENTITY_ID,
                    "filteringValue": id,
                    "andCriteria": true
                }
            ]
        }
        const res = await axios.post(BACK_URL + '/domain/musicband/history/', payload,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return res.data.map((e: any) => {
            e.time = new Date(e.time);
            return e;
        });
    }catch (e){
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return [];
    }
}

export async function GetBandsCount(filter: BandsSorting): Promise<number> {
    try {
        const res = await axios.post(BACK_URL + '/domain/musicband/count', filter,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return res.data;
    } catch (e) {
        console.error(e);
        if ((e as AxiosError).status == 403)
            useUserStore.getState().Logout();
        return 0;
    }
}

export async function DeleteMusicBand(id: number): Promise<Message> {
    try {
        const res = await axios.delete(BACK_URL + '/domain/musicband/' + id,
            {headers: {Authorization: 'Bearer ' + useUserStore.getState().token}});
        return {isError: false, message: "Success"};
    }catch (e){
        const message = ((e as AxiosError)?.response?.data as string) || (e as AxiosError)?.message;
        return {isError: true, message};
    }
}