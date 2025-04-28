import axios from "axios";
import {getUserTokens} from "./token-utils.js";

const BASE_API = import.meta.env.VITE_BACKEND_ADDRESS
export const baseApi = axios.create(
    {
        baseURL: BASE_API,
        headers: {
            "Content-type": "application/json",
            Accept: "application/json",
        },
        withCredentials: true
    }
);


baseApi.interceptors.request.use((config) => {
    const { jwtToken, csrfToken } = getUserTokens();
    if (jwtToken) {
        config.headers.Authorization = `Bearer ${jwtToken}`;
    }
    if (csrfToken) {
        config.headers["X-XSRF-TOKEN"] = csrfToken;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});