import { createBrowserRouter } from "react-router-dom";
import { Home } from "./pages/Home";
import { Signin } from "./pages/Signin";
import { Signup } from "./pages/Signup";
import { Dashboard } from "./pages/Dashboard";

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
        }
    ]
)