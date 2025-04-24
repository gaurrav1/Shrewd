import {createBrowserRouter} from "react-router-dom";
import { Home } from "./pages/social/Home.jsx";
import { Signin } from "./pages/social/Signin.jsx";
import { Signup } from "./pages/social/Signup.jsx";
import { Dashboard } from "./pages/tenants/Dashboard.jsx";

export const router = createBrowserRouter(
    [
        {
            path: "/",
            children: [
                {
                    index: true,
                    element: <Home />
                },
                {
                    path: "signin",
                    element: <Signin />
                },
                {
                    path: "signup",
                    element: <Signup />
                },
                {
                    path: "dashboard",
                    element: <Dashboard />
                }
            ]
        },
        {
            path: "*",
            element: <h1>Okk</h1>
        }
    ]
)