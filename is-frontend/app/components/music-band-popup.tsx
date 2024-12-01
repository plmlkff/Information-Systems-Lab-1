'use client'
import './music-band-popup.css';
import React, {forwardRef, useEffect, useRef, useState} from "react";
import styles from "@/app/login/page.module.css";
import Spinner from "@/app/components/spinner";
import {Message} from "@/api/auth";
import {MusicBand, MusicGenre} from "@/app/context/music-store";
import {CreateMusicBand, UpdateMusicBand} from "@/api/bands";
import MessageComponent from "@/app/components/message";
import {GetBandBestAlbum, GetBandCoordinates, GetBandStudio} from "@/api/band-parts";


export default forwardRef(function MusicBandPopup({musicBand, setMusicBand, onClose}: {
    musicBand?: MusicBand,
    setMusicBand?: (band: MusicBand) => void,
    onClose?: () => void
}, ref) {
    const [response, setResponse] = useState<Message | null>(null)
    const [requestSent, setRequestSent] = useState<boolean>(false);
    const dialog = React.createRef<HTMLDialogElement>();

    const desc = useRef<HTMLInputElement>(null);
    const [newBand, setNewBand] = useState<Partial<MusicBand> & { [key: string]: any }>({...musicBand});

    const locationId = useRef<HTMLInputElement>(null);
    const bestAlbumId = useRef<HTMLInputElement>(null);
    const studioId = useRef<HTMLInputElement>(null);

    useEffect(() => {
        // @ts-ignore
        ref.current = dialog.current;
    }, [dialog]);

    useEffect(() => {
        if (response) setRequestSent(false);
        if (response && !response.isError) dialog.current?.close();
    }, [response]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const name = e.currentTarget.name;
        const value = e.currentTarget.value;
        if (name.startsWith('no_') && value === 'on') {
            newBand[name.replaceAll('no_', '')] = null;
        } else if (name.includes('.')) {
            const [a, b] = name.split('.');
            newBand[a] = {...(newBand[a] || {}), [b]: value};
        } else if (name == 'numberOfParticipants' && value == '')
            newBand[name] = null;
        else if (name == 'establishmentDate') {
            if (value)
                newBand[name] = new Date(value);
            else
                newBand[name] = null;
        } else newBand[name] = value;

        setNewBand({...newBand});
    }

    useEffect(() => {
        console.log(newBand)
    }, [newBand]);

    return (
        <>
            <dialog style={{maxHeight: '80vh', overflowY: 'auto'}} ref={dialog} onClick={e => {
                if ((e.target as HTMLElement).tagName === "DIALOG" && e.target === e.currentTarget) e.currentTarget.close();
            }} className="music-band-popup" onClose={onClose}>
                <form className="band-form" method="dialog"
                      onSubmit={e => {
                          e.preventDefault();

                          setRequestSent(true);
                          setResponse(null);
                          const formData: any = Object.fromEntries(new FormData(e.currentTarget));
                          formData.id = musicBand?.id;
                          formData.ownerLogin = musicBand?.ownerLogin;
                          console.log(newBand);
                          if (musicBand) {
                              UpdateMusicBand(newBand as unknown as MusicBand).then(res => {
                                  newBand.creationDate = musicBand?.creationDate;
                                  setMusicBand && !res.isError && setMusicBand(newBand as MusicBand);
                                  setResponse(res);
                              })
                          } else
                              CreateMusicBand(newBand as unknown as MusicBand).then(setResponse)
                      }}>
                    <label>
                        Название<br/>
                        <input type="text" value={newBand?.name || ''} name='name' required onChange={handleChange}/>
                    </label>
                    <p>Координаты:</p>
                    <div className='import-line'>
                        <label>
                            ID
                            <input type="number" min='1' ref={locationId} step='1'/>
                        </label>
                        <button onClick={e => {
                            e.preventDefault();
                            if (locationId.current && locationId.current.reportValidity() && locationId.current.value) {
                                GetBandCoordinates(+locationId.current.value)
                                    .then(coords => setNewBand({...newBand, coordinates: coords}));
                                locationId.current.value = '';
                            }
                        }}>Загрузить
                        </button>
                    </div>
                    <div className='coordinates'>
                        <label>
                            X
                            <input type="number" value={newBand?.coordinates?.x || ''} min='-145' required
                                   name='coordinates.x' onChange={handleChange}  step='1'/>
                        </label>
                        <label>
                            Y
                            <input type="number" value={newBand?.coordinates?.y || ''} max='675' required
                                   name='coordinates.y' onChange={handleChange}  step='1'/>
                        </label>
                    </div>
                    <div>
                        <label>
                            Описание<br/>
                            <input value={newBand?.description || ''} ref={desc} name='description'
                                   onChange={handleChange}/>
                        </label><br/>
                        <label>
                            <input type='checkbox' style={{minWidth: 'fit-content'}} name='no_description'
                                   onInput={e => {
                                       if (desc.current) {
                                           desc.current.placeholder = e.currentTarget.checked ? '<не указано>' : '';
                                           setNewBand({
                                               ...newBand,
                                               description: e.currentTarget.checked ? null : ''
                                           });
                                           desc.current.disabled = e.currentTarget.checked;
                                       }
                                   }}/> Отсутсвует
                        </label>
                    </div>
                    <label>
                        Жанр<br/>
                        <select name='genre' value={newBand?.genre || ''} onChange={e => {
                            setNewBand({
                                ...newBand,
                                genre: e.currentTarget.value == "Не указано" ? null : e.currentTarget.value as MusicGenre
                            });
                        }}>
                            {["Не указано", MusicGenre.JAZZ, MusicGenre.ROCK, MusicGenre.BRIT_POP, MusicGenre.PSYCHEDELIC_CLOUD_RAP]
                                .map((e, i) =>
                                    <option key={i} value={i == 0 ? '' : e}>{e}</option>
                                )}
                        </select>
                    </label>
                    <label>
                        Кол-во синглов<br/>
                        <input type="number" value={newBand?.singlesCount || ''} name='singlesCount'
                               onChange={handleChange} min='1' step='1'/>
                    </label>
                    <label>
                        Кол-во участников<br/>
                        <input type='number' value={newBand?.numberOfParticipants || ''}  step='1'
                               min='1' name='numberOfParticipants' onChange={handleChange}/>
                    </label>
                    <label>
                        Кол-во альбомов<br/>
                        <input type="number" value={newBand?.albumsCount || ''} min='1' name='albumsCount'
                               onChange={handleChange} step='1'/>
                    </label>
                    <p>Лучший альбом:</p>
                    <div className='import-line'>
                        <label>
                            ID
                            <input type="number" min='1' ref={bestAlbumId} step='1'/>
                        </label>
                        <button onClick={e => {
                            e.preventDefault();
                            if (bestAlbumId.current && bestAlbumId.current.reportValidity() && bestAlbumId.current.value) {
                                GetBandBestAlbum(+bestAlbumId.current.value)
                                    .then(coords => setNewBand({...newBand, bestAlbum: coords}));
                                bestAlbumId.current.value = '';
                            }
                        }}>Загрузить
                        </button>
                    </div>
                    <div className='best-album'>
                        <label>
                            Название
                            <input type="text" value={newBand?.bestAlbum?.name || ''} required
                                   name='bestAlbum.name' onChange={handleChange}/>
                        </label>
                        <label>
                            Продажи
                            <input type="number" value={newBand?.bestAlbum?.sales || ''} min='1' required
                                   name='bestAlbum.sales' onChange={handleChange} step='1'/>
                        </label>
                    </div>
                    <label>
                        Дата открытия<br/>
                        <input type="date" name='establishmentDate' onChange={handleChange}
                               value={newBand?.establishmentDate?.toISOString().split('T')[0] || ''}/>
                    </label>
                    <p>Студия:</p>
                    <div className='import-line'>
                        <label>
                            ID
                            <input type="number" min='1' ref={studioId}  step='1'/>
                        </label>
                        <button onClick={e => {
                            e.preventDefault();
                            if (studioId.current && studioId.current.reportValidity() && studioId.current.value) {
                                GetBandStudio(+studioId.current.value)
                                    .then(coords => setNewBand({...newBand, studio: coords}));
                                studioId.current.value = '';
                            }
                        }}>Загрузить
                        </button>
                    </div>
                    <label>
                        Адрес<br/>
                        <input type="text" value={newBand?.studio?.address || ''} required name='studio.address'
                               onChange={handleChange}/>
                    </label>
                    <MessageComponent message={response} onlyError={true}/>
                    <div className={styles.buttons}>
                        <button disabled={requestSent}>{requestSent &&
                            <Spinner size={30} style={{margin: "-11px 0 -11px -32px", paddingRight: "32px"}}/>}
                            {musicBand ? 'Изменить' : 'Создать'}
                        </button>
                    </div>
                </form>
            </dialog>
        </>
    )
})