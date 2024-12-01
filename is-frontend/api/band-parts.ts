import axios from "axios";
import {BACK_URL} from "@/api/auth";

export async function GetBandCoordinates(id: number): Promise<{x: number, y: number}> {
    try {
        const res = await axios.get(BACK_URL + '/domain/coordinates/' + id, {
            headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}
        })
        delete res.data.id;
        return res.data;
    } catch (e: any) {
        return {x: 0, y: 0};
    }
}

export async function GetBandBestAlbum(id: number): Promise<{name: string, sales: number}> {
    try {
        const res = await axios.get(BACK_URL + '/domain/album/' + id, {
            headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}
        })
        delete res.data.id;
        return res.data;
    } catch (e: any) {
        return {name: '', sales: 0};
    }
}

export async function GetBandStudio(id: number): Promise<{address: string}> {
    try {
        const res = await axios.get(BACK_URL + '/domain/studio/' + id, {
            headers: {Authorization: 'Bearer ' + localStorage.getItem('token')}
        })
        delete res.data.id;
        return res.data;
    } catch (e: any) {
        return {address: ''};
    }
}