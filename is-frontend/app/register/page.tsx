'use client'
import Spinner from "@/app/components/spinner";
import {useEffect, useState} from "react";
import styles from "./../login/page.module.css";
import {Message, SignUp} from "@/api/auth";
import MessageComponent from "@/app/components/message";
import {Login} from "@/api/auth";
import Link from "next/link";
import Image from "next/image";
import arrow from "../../public/login/arrow_up.svg"
import {useUserStore} from "@/app/context/user-store";
import useAnimatedRouter from "@/app/components/use-animated-router";


export default function LoginPage() {
	const [response, setResponse] = useState<Message | null>(null)
	const [requestSent, setRequestSent] = useState<boolean>(false);
	const {animatedRoute} = useAnimatedRouter();
	const user = useUserStore((state) => state);

	useEffect(() => {
		if(response) setRequestSent(false);
		if(response && !response.isError){

		}
	}, [response, animatedRoute]);

	useEffect(() => {
		if(user.login) animatedRoute("/main.html");
	}, [user, animatedRoute]);

	return (
		<main style={{height: '-webkit-fill-available', display: 'flex', flexDirection: "column"}}>
			<Image src={arrow.src} alt="" className={styles.arrow} width={135} height={100}/>
			<h1 className={styles.title}>Регистрация</h1>
			<form className={styles.form} onSubmit={e => {
				e.preventDefault();
				const formData = new FormData(e.currentTarget);

				setResponse(null);
				setRequestSent(true);
				SignUp(formData.get("username") as string, formData.get("password") as string, formData.get("role") === "on")
					.then(setResponse);
			}}>
				<label>Логин<br/><input type="text" name="username" required minLength={2}
										autoComplete="email"/></label>
				<label>Пароль<br/><input type="password" name="password" required
										 autoComplete="new-password"/></label>
				<label style={{display: 'flex', alignItems: 'flex-end', cursor: 'pointer'}}>Аккаунт администратора
					<input type="checkbox" name="role" style={{minWidth: 'fit-content', marginLeft: '20px', cursor: 'pointer'}}/>
				</label>
				<MessageComponent message={response}/>
				<div className={styles.buttons}>
					<button disabled={requestSent}>{requestSent &&
						<Spinner size={30} style={{margin: "-11px 0 -11px -32px", paddingRight: "32px"}}/>}Зарегистрироваться
					</button>
				</div>
				<Link href="/login.html" className={styles.register_link} onClick={(e) => {
					animatedRoute(e.currentTarget.href);
				}}>Вход</Link>
			</form>
		</main>
	)
}
