// import { AppContextProvider } from "../context/AppContext";
import { ThemeProvider } from "../context/ThemeContext";
import {Outlet} from "react-router-dom";

const PAGE_TITLES = {
    "/location": "Location",
    "/settings": "Settings",
    "/history": "History",
    "/shifts": "Shifts",
};

export function RootLayout() {
    return (
        <>
            {/*<AppContextProvider>*/}
                <ThemeProvider>
                    <div className="layout">
                        <Outlet />
                    </div>
                </ThemeProvider>
            {/*</AppContextProvider>*/}
        </>
    );
};