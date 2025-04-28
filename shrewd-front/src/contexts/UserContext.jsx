import { createContext, useContext, useState, useEffect } from "react";

const UserContext = createContext(null);

export function UserProvider({ children }) {
    const [user, setUser] = useState(null);
    const [tokens, setTokens] = useState({ jwtToken: null, csrfToken: null });

    useEffect(() => {
        // On app load, restore user and tokens from sessionStorage
        const storedUser = sessionStorage.getItem('user');
        const storedTokens = sessionStorage.getItem('user-tokens');

        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
        if (storedTokens) {
            setTokens(JSON.parse(storedTokens));
        }
    }, []);

    const login = (userData, tokenData) => {
        setUser(userData);
        setTokens(tokenData);
        sessionStorage.setItem('user', JSON.stringify(userData));
        sessionStorage.setItem('user-tokens', JSON.stringify(tokenData));
    };

    const logout = () => {
        setUser(null);
        setTokens({ jwtToken: null, csrfToken: null });
        sessionStorage.removeItem('user');
        sessionStorage.removeItem('user-tokens');
    };

    return (
        <UserContext.Provider value={{ user, tokens, login, logout }}>
            {children}
        </UserContext.Provider>
    );
}

export function useUser() {
    return useContext(UserContext);
}
