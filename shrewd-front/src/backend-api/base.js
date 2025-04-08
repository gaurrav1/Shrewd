import axios from "axios";

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

baseApi.interceptors.request.use(
    async (config) => {
        const token = localStorage.getItem("JWT_TOKEN");
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }

        let csrfToken = localStorage.getItem("CSRF_TOKEN");
        if (!csrfToken) {
            try {
                const response = await axios.get(
                    `${BASE_API}/auth/csrf/csrf-token`,
                    { withCredentials: true }
                );
                csrfToken = response.data.token;
                localStorage.setItem("CSRF_TOKEN", csrfToken);
            } catch (error) {
                console.error("Failed to fetch CSRF token", error);
            }
        }

        if (csrfToken) {
            config.headers["X-XSRF-TOKEN"] = csrfToken;
        }
        console.log("X-XSRF-TOKEN " + csrfToken);
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);