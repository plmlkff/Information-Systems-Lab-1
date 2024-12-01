'use client'
import styles from "./page.module.css";
import React, {useEffect, useRef, useState} from "react";
import {MusicBand} from "@/app/context/music-store";
import {useUserStore} from "@/app/context/user-store";
import useAnimatedRouter from "@/app/components/use-animated-router";
import {BandColumn, BandsSorting, GetBandsCount, GetHistory, GetMusicBands, HistoryRecord} from "@/api/bands";
import CoordinateAxes from "@/app/components/axes-svg";
import ClaimPopup from "@/app/components/claim-popup";
import MusicBandPopup from "@/app/components/music-band-popup";
import useWebsocket from "@/app/components/use-websocket";

const DATE_FORMAT = new Intl.DateTimeFormat('ru-RU', {
    dateStyle: 'short',
    timeZone: 'Europe/Minsk',
});
export default function Home() {
    const {animatedRoute} = useAnimatedRouter();
    const [bands, setBands] = useState<MusicBand[]>([]);
    const [history, setHistory] = useState<HistoryRecord[]>([]);
    const [coordsPerPixel, setCoordsPerPixel] = useState(1);
    const [center, setCenter] = useState({x: 0, y: 0});
    const [tipPos, setTipPos] = useState({x: -10000, y: -10000, id: 0});

    const musicBandPopup = useRef<HTMLDialogElement>();
    const user = useUserStore((state) => state);
    const [toEdit, setToEdit] = useState<number>(-2);
    const pane = useRef<HTMLDivElement>(null);
    const tip = useRef<HTMLDivElement>(null);

    const [loadHistory, setLoadHistory] = useState<number>(-1);
    const {messages} = useWebsocket('http://localhost:8080/IS_Lab1-1.0-SNAPSHOT/api/domain/changed');


    useEffect(() => {
        console.log(user);
        if (user.inited && !user.login) animatedRoute("/login.html");
    }, [user]);

    useEffect(() => {
        if (!user.inited) return;
        const filter: BandsSorting = {
            sortDirection: "ASC",
            sortColumn: BandColumn.ID,
            pageSize: 50,
            criteria: []
        };
        GetBandsCount(filter).then(count => {
            const newBands: MusicBand[] = [];
            for(let i = 0; i < Math.ceil(count / 50); i++) {
                filter.pageNumber = i + 1;
                GetMusicBands(filter).then(bands => {
                    newBands.push(...bands);
                    setBands(newBands);
                });
            }
        })
    }, [user.inited, messages]);

    useEffect(() => {
        if (!pane.current) return;
        setCenter({x: pane.current.offsetWidth / 2, y: pane.current.offsetHeight / 2});

        const maxX = bands
            .reduce(((acc, e) => Math.max(acc, Math.abs(e.coordinates.x))), -1);
        const maxY = bands
            .reduce(((acc, e) => Math.max(acc, Math.abs(e.coordinates.y))), -1);
        if (maxX === -1 && maxY === -1) return;
        setCoordsPerPixel( 1 / Math.min(pane.current.offsetWidth / maxX / 2.2, pane.current.offsetHeight / maxY / 2.2));
    }, [bands, pane]);

    useEffect(() => {
        if (!musicBandPopup.current || toEdit < -1) return;
        musicBandPopup.current.showModal();
    }, [musicBandPopup, toEdit]);

    useEffect(() => {
        if(loadHistory < 0) return;
        setHistory([]);
        GetHistory(loadHistory).then(setHistory);
    }, [loadHistory]);

    const chosenBand = bands.find(e => e.id === tipPos.id);

    return (
        <main className={styles.main}>
            <header>
                <h1 className={styles.title}>Визуализация</h1>
                <div style={{position: 'absolute', top: '0', right: '0'}}>
                    <span>{user.login}</span>
                    <button onClick={() => animatedRoute('/main.html')} className={styles.claims}>Главная</button>
                    {user.isAdmin && <ClaimPopup/>}
                </div>
            </header>
            <div style={{display: 'grid', gridTemplateColumns: '1fr 1fr', margin: '2vh 3vw'}}>
                <div className={styles.pane} ref={pane}
                     onClick={e =>
                         !(e.target as HTMLElement).classList.contains(styles.point) && setTipPos({x: -10000, y: -10000, id: 0})}>
                    <CoordinateAxes width={center.x * 2} height={center.y * 2} margin={20} scaleStep={50}
                                    scale={coordsPerPixel}/>
                    {bands.map((e, i) =>
                        <div className={styles.point} key={e.id}
                             onClick={ev => setTipPos({x: ev.clientX, y: ev.clientY - 30, id: e.id})}
                             style={{
                                 left: center.x + e.coordinates.x / coordsPerPixel,
                                 top: center.y - e.coordinates.y / coordsPerPixel,
                                 background: (user.isAdmin || e.ownerLogin === user.login) ? 'lightgreen' : 'red',
                             }}>
                        </div>
                    )}
                </div>
                <div className={styles.data_table_container}>
                    <table className={styles.data_table}>
                        <thead>
                        <tr>
                            <td>логин</td>
                            <td>операция</td>
                            <td>id</td>
                            <td>время</td>
                        </tr>
                        </thead>
                        <tbody>
                        {history.map((e, i) =>
                            <tr key={i}>
                                <td>{e.login}</td>
                                <td>{e.operation}</td>
                                <td>{e.musicBandId}</td>
                                <td>{DATE_FORMAT.format(e.time)}</td>
                            </tr>
                        )}
                        </tbody>
                    </table>
                </div>
            </div>
            <div ref={tip} className={styles.tip} style={{
                left: tipPos.x,
                top: tipPos.y,
            }}>
                {(user.isAdmin || (chosenBand && (chosenBand.ownerLogin === user.login || user.isAdmin))) && <button onClick={() => setToEdit(tipPos.id)}>Изменить</button>}
                <button onClick={() => setLoadHistory(tipPos.id)}>История</button>
            </div>
            {toEdit > -2 &&
                <MusicBandPopup ref={musicBandPopup}
                                onClose={() => {
                                    setToEdit(-2);
                                    setTipPos({x: -10000, y: -10000, id: 0});
                                }}
                                musicBand={toEdit !== -1 ? chosenBand : undefined}
                                setMusicBand={toEdit !== -1 ? e => {
                                    const bandIndex = bands.findIndex(e => e.id === toEdit);
                                    if(bandIndex < 0) return;
                                    bands[bandIndex] = e;
                                    setBands(bands);
                                } : undefined}
                />}
        </main>
    );
}