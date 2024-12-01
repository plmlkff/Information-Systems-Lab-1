'use client'
import styles from "@/app/page.module.css";
import React, {useState} from "react";
import {ApproveClaim, GetClaims, RejectClaim} from "@/api/approve";


export default function ClaimPopup() {
    const [claims, setClaims] = useState<string[]>([]);
    const [loaded, setLoaded] = useState(false);
    const dialog = React.createRef<HTMLDialogElement>();

    return (
        <>
            <button className={styles.claims} onClick={e => {
                e.preventDefault();
                GetClaims().then(data => (setClaims(data), setLoaded(true)));
                dialog.current?.showModal();
            }}>Заявки
            </button>
            <dialog style={{maxHeight: '80vh', overflowY: 'auto'}} ref={dialog} onClick={e => {
                if ((e.target as HTMLElement).tagName === "DIALOG" && e.target === e.currentTarget) e.currentTarget.close();
            }}>
                <div className={styles.claims_dialog}>
                    {claims.length === 0 ?
                        <p className={styles.empty}>{loaded ? 'Пока нет заявок' : 'Загрузка...'}</p>
                        :
                        <>
                            {claims.map(e =>
                                <div key={e} className={styles.claim}>
                                    <p>{e}</p>
                                    <button className={styles.approve}
                                            onClick={() => {
                                                ApproveClaim(e);
                                                setClaims(claims.filter(c => c != e));
                                            }}>Принять
                                    </button>
                                    <button className={styles.approve}
                                            onClick={() => {
                                                RejectClaim(e);
                                                setClaims(claims.filter(c => c != e));
                                            }}>Отклонить
                                    </button>
                                </div>
                            )}
                        </>
                    }</div>
            </dialog>
        </>
    )
}