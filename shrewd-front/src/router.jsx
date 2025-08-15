import {createHashRouter, Navigate} from "react-router-dom";
import { Home } from "./pages/social/home/Home.jsx";
import {RootLayout} from "./layout/RootLayout.jsx";

export const router = createHashRouter([
    {
        path: "/",
        element: <RootLayout />,
        errorElement: <h1>Routing Error</h1>,
        children: [
            {
                index: true,
                element: <Navigate to="home" />
            },
            {
                path: 'home',
                element: <Home />
            },
        ]
    },
    {
        path: '*',
        element: <h1>Wrong path</h1>
    }
]);