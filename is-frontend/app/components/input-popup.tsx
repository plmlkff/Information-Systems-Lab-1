'use client'
import './music-band-popup.css';
import React, {forwardRef, useEffect, useRef, useState} from "react";
import styles from "@/app/login/page.module.css";
import Spinner from "@/app/components/spinner";
import {Message} from "@/api/auth";
import {MusicBand, MusicGenre} from "@/app/context/music-store";
import MessageComponent from "@/app/components/message";


export default function InputPopup({actionName, children, sendRequestAction}: {
    actionName: string,
    children: React.ReactNode,
    sendRequestAction: (data: FormData) => Promise<Message>
}) {
    const [response, setResponse] = useState<Message | null>(null)
    const [requestSent, setRequestSent] = useState<boolean>(false);
    const dialog = React.createRef<HTMLDialogElement>();

    useEffect(() => {
        if (response) setRequestSent(false);
        // if (response && !response.isError) dialog.current?.close();
    }, [response]);

    return (
        <>
            <button onClick={() => {
                dialog.current?.showModal();
            }}>{actionName}</button>
            <dialog style={{maxHeight: '80vh', overflowY: 'auto'}} ref={dialog} onClick={e => {
                if ((e.target as HTMLElement).tagName === "DIALOG" && e.target === e.currentTarget) e.currentTarget.close();
            }} className="music-band-popup">
                <form className="band-form" method="dialog"
                      onSubmit={e => {
                          e.preventDefault();

                          setRequestSent(true);
                          setResponse(null);

                          const formData = new FormData(e.currentTarget);
                          sendRequestAction(formData).then(setResponse)
                      }}>
                    {children}
                    <MessageComponent message={response} onlyError={false}/>
                    <div className={styles.buttons}>
                        <button disabled={requestSent}>{requestSent &&
                            <Spinner size={30} style={{margin: "-11px 0 -11px -32px", paddingRight: "32px"}}/>}
                            Отправить
                        </button>
                    </div>
                </form>
            </dialog>
        </>
    )
}