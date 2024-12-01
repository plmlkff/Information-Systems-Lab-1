import {create} from "zustand";
import {AppRouterInstance} from "next/dist/shared/lib/app-router-context.shared-runtime";


interface User {
	inited: boolean;
	login: string | null;
	token: string | null;
	isAdmin: boolean;
	Login: (login: string, token: string, isAdmin: boolean) => void;
	Logout: () => void;
	init: (router: AppRouterInstance, pathname: string) => void;
}

export const useUserStore = create<User>((set) => ({
    inited: false,
	login: null,
	token: null,
	isAdmin: false,
	Login: function (login: string, token: string, isAdmin: boolean) {
		localStorage.setItem("login", login);
		localStorage.setItem("token", token);
		localStorage.setItem("isAdmin", isAdmin ? "true" : "false");
		set({login: login, token: token, isAdmin: isAdmin});
	},
	Logout: function () {
		console.log('logout')
		localStorage.removeItem("login");
		localStorage.removeItem("token");
		localStorage.removeItem("isAdmin");
		set({login: null, token: null, isAdmin: false});
	},
	init: function(router, pathname) {
		const login = localStorage.getItem("login");
		const token = localStorage.getItem("token");
		const isAdmin = localStorage.getItem("isAdmin") === "true";
		set({login: login, token: token, isAdmin, inited: true});
		if(!login && pathname !== "/login.html" && pathname !== "/register.html") router.push("/login.html");
	}
}));
