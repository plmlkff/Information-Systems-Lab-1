'use client'
import styles from "./page.module.css";
import Image from "next/image";
import edit from "@/public/edit.svg";
import new_band from "@/public/new.svg";
import trash from "@/public/trash.svg";
import React, {Fragment, useEffect, useRef, useState} from "react";
import {BandColumn, BandsSorting, DeleteMusicBand, GetBandsCount, GetMusicBands} from "@/api/bands";
import ClaimPopup from "@/app/components/claim-popup";
import MusicBandPopup from "@/app/components/music-band-popup";
import {MusicBand} from "@/app/context/music-store";
import {useUserStore} from "@/app/context/user-store";
import useAnimatedRouter from "@/app/components/use-animated-router";
import useWebsocket from "@/app/components/use-websocket";
import {BACK_URL} from "@/api/auth";

const FILTERING_COLUMNS = [BandColumn.ID, BandColumn.MUSIC_BAND_NAME, BandColumn.MUSIC_BAND_COORDINATE_X, BandColumn.MUSIC_BAND_COORDINATE_Y, BandColumn.MUSIC_BAND_CREATION_DATE, BandColumn.MUSIC_BAND_MUSIC_GENRE, BandColumn.MUSIC_BAND_NUMBER_OF_PARTICIPANTS, BandColumn.MUSIC_BAND_SINGLES_COUNT, BandColumn.MUSIC_BAND_DESCRIPTION, BandColumn.MUSIC_BAND_BEST_ALBUM_NAME, BandColumn.MUSIC_BAND_BEST_ALBUM_SALES, BandColumn.MUSIC_BAND_ALBUMS_COUNT, BandColumn.MUSIC_BAND_ESTABLISHMENT_DATE, BandColumn.MUSIC_BAND_STUDIO_ADDRESS, BandColumn.MUSIC_BAND_USER_LOGIN];
const TABLE_HEADERS = ['ид', 'название', 'x', 'y', 'дата создания', 'жанр', 'кол-во участников', 'кол-во синглов', 'описание', 'лучший альбом', 'продажи лучшего альбома', 'кол-во альбомов', 'дата основания', 'студия', 'юзер'];
export default function Home() {
    const {animatedRoute} = useAnimatedRouter();
    const [bands, setBands] = useState<MusicBand[][]>([]);
    const [page, setPage] = useState(0);
    const [filter, setFilter] = useState<BandsSorting>({
        pageSize: 50,
        sortDirection: "ASC",
        sortColumn: BandColumn.ID,
        criteria: []
    });

    const pageContainer = React.createRef<HTMLDivElement>();
    const musicBandPopup = useRef<HTMLDialogElement>();
    const user = useUserStore((state) => state);

    const [toEdit, setToEdit] = useState<number>(-2);
    const [loaded, setLoaded] = useState(false);
    const [pageCount, setPageCount] = useState(0);

    const {messages} = useWebsocket('http://localhost:8080/IS_Lab1-1.0-SNAPSHOT/api/domain/changed');

    useEffect(() => {
        animatedRoute("/main.html");
    }, []);

    // useEffect(() => {
    //     console.log(messages);
    // }, [messages]);
    //
    // useEffect(() => {
    //     console.log(user);
    //     if (user.inited && !user.login) animatedRoute("/login.html");
    // }, [user]);
    //
    // useEffect(() => {
    //     if (!user.inited) return;
    //     const pageReq = page + 1;
    //     setLoaded(false);
    //     GetMusicBands({...filter, pageNumber: pageReq}).then(data => {
    //         setBands(bands => {
    //             const newBands = [...bands];
    //             newBands[pageReq - 1] = data;
    //             setLoaded(true);
    //             return newBands;
    //         })
    //     });
    // }, [user.inited, page, filter, messages]);
    //
    // useEffect(() => {
    //     if (!user.inited) return;
    //     GetBandsCount(filter).then(count => setPageCount(Math.ceil(count / (filter.pageSize || 50))));
    // }, [user.inited, filter, messages]);
    //
    // useEffect(() => {
    //     if (!pageContainer.current) return;
    //     const child = pageContainer.current.children[0];
    //     if (!child) return;
    //     pageContainer.current.style.marginLeft = `calc(15vw - ${child.getBoundingClientRect().width * (page * 2 + 0.5)}px)`;
    // }, [page, bands, pageCount])
    //
    // useEffect(() => {
    //     console.log(musicBandPopup, toEdit)
    //     if (!musicBandPopup.current || toEdit < -1) return;
    //     musicBandPopup.current.showModal();
    // }, [musicBandPopup, toEdit]);

    function setSortColumn(i: number) {
        setFilter({
            sortColumn: FILTERING_COLUMNS[i],
            sortDirection: filter.sortColumn === FILTERING_COLUMNS[i] ? (filter.sortDirection === "ASC" ? "DESC" : "ASC") : "ASC",
            criteria: filter.criteria
        });
    }

    return (
        <main className={styles.main}>
            <header>
                <h1 className={styles.title}>Главная</h1>
                <div style={{position: 'absolute', top: '0', right: '0'}}>
                    <span>{user.login}</span>
                    <button onClick={() => animatedRoute('/visualise.html')} className={styles.claims}>Визуализация
                    </button>
                    {user.isAdmin && <ClaimPopup/>}
                </div>
            </header>
            <form className={styles.filters_pane} onSubmit={e => {
                e.preventDefault();
                const formData = new FormData(e.currentTarget);
                setFilter({
                    ...filter, criteria: [...filter.criteria, {
                        filteringColumn: formData.get('filteringColumn') as BandColumn,
                        filteringValue: formData.get('filteringValue') as string,
                        andCriteria: formData.get('andCriteria') == 'И'
                    }]
                })
                e.currentTarget.reset();
            }}>
                <select name='filteringColumn'>{FILTERING_COLUMNS.map((e, i) => {
                    return <option key={e}>{TABLE_HEADERS[i]}</option>
                })}</select>
                <span> = </span>
                <input type='text' placeholder='значение'
                       name='filteringValue' required/>
                <label>тип<select name='andCriteria' style={{marginLeft: '5px'}}>
                    <option>И</option>
                    <option>ИЛИ</option>
                </select></label>
                <button>Добавить</button>
            </form>
            <div className={styles.filters}>
                {filter.criteria.map((e, i) =>
                    <span key={i} onClick={() => {
                        setFilter({...filter, criteria: filter.criteria.filter((_, j) => j !== i)})
                    }}
                          style={{marginRight: '5px'}}>
                        {e.filteringColumn} = {e.filteringValue} {e.andCriteria ? 'ИЛИ' : 'И'}
                    </span>
                )}
            </div>
            <div className={styles.data_table_container}>
                <table className={styles.data_table}>
                    <thead>
                    <tr>
                        {TABLE_HEADERS.map((e, i) =>
                            <th key={i}
                                onClick={() => setSortColumn(i)}
                                className={filter.sortColumn === FILTERING_COLUMNS[i] ? filter.sortDirection === "ASC" ? styles.ascending : styles.descending : ''}>
                                {e}
                            </th>)}
                        <th style={{width: '25px'}}>
                            <Image src={new_band} width={20} height={20} alt=""
                                   style={{margin: 'auto', display: 'block', cursor: 'pointer'}}
                                   onClick={() => {
                                       setToEdit(-1);
                                   }}
                            />
                        </th>
                        <th>
                        </th>
                    </tr>
                    </thead>
                    <tbody className={loaded ? '' : styles.loading}>
                    {bands[page] && bands[page].map((e, i) =>
                        <tr key={i}>
                            <td>{e.id}</td>
                            <td>{e.name}</td>
                            <td>{e.coordinates.x}</td>
                            <td>{e.coordinates.y}</td>
                            <td>{e.creationDate?.toISOString().split('T')[0]}</td>
                            <td className={e.genre == null ? styles.empty : ''}>{e.genre || 'пусто'}</td>
                            <td className={e.numberOfParticipants == null ? styles.empty : ''}>{e.numberOfParticipants || 'пусто'}</td>
                            <td>{e.singlesCount}</td>
                            <td className={e.description == null ? styles.empty : ''}>{e.description || 'пусто'}</td>
                            <td>{e.bestAlbum.name}</td>
                            <td>{e.bestAlbum.sales}</td>
                            <td>{e.albumsCount}</td>
                            <td className={e.establishmentDate == null ? styles.empty : ''}>{e.establishmentDate?.toISOString().split('T')[0]}</td>
                            <td>{e.studio.address}</td>
                            <td>{e.ownerLogin}</td>
                            <td>
                                {(user.isAdmin || user.login === e.ownerLogin) &&
                                    <Image src={edit} width={20} height={20} alt=""
                                           style={{margin: 'auto', display: 'block', cursor: 'pointer'}}
                                           onClick={() => {
                                               setToEdit(i);
                                           }}
                                    />}
                            </td>
                            <td>
                                {(user.isAdmin || user.login === e.ownerLogin) &&
                                    <Image src={trash} width={20} height={20} alt=""
                                           style={{margin: 'auto', display: 'block', cursor: 'pointer'}}
                                           onClick={() => {
                                               DeleteMusicBand(i);
                                           }}
                                    />}
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </div>
            <div className={styles.controls}>
                <div>
                    <span>Элементов на странице:</span>
                    <select name='pageCount' defaultValue={filter.pageSize}
                            onChange={e => setFilter({...filter, pageSize: parseInt(e.target.value)})}>
                        <option value={10}>10</option>
                        <option value={20}>20</option>
                        <option value={50}>50</option>
                    </select>
                </div>
                <p style={{userSelect: 'none'}}>{page + 1}</p>
                <div className={styles.pages_line}>
                    <div style={{gridTemplateColumns: `repeat(${pageCount * 2}, 1fr)`}} ref={pageContainer}>
                        {new Array(pageCount)
                            .fill(0).map((e, i) =>
                                <Fragment key={i}>
                                    <p onClick={() => setPage(i)}
                                       style={{
                                           opacity: i === page ? 1 : (Math.abs(i - page) <= 3 ? (0.7 - Math.abs(i - page) / 7) : 0)
                                       }}>{i + 1}</p>
                                    <p></p>
                                </Fragment>)}
                    </div>
                </div>
                <div></div>
            </div>
            {toEdit > -2 &&
                <MusicBandPopup ref={musicBandPopup}
                                onClose={() => setToEdit(-2)}
                                musicBand={toEdit !== -1 ? bands[page][toEdit] : undefined}
                                setMusicBand={toEdit !== -1 ? e => {
                                    bands[page][toEdit] = e;
                                    setBands(bands);
                                } : undefined}
                />}
        </main>
    );
}