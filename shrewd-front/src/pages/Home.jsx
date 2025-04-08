import { Link } from "react-router-dom";

export function Home() {
  return (
    <>
    <div>Welcome fellow friends!</div>
    <div>Backend connection to {import.meta.env.VITE_BACKEND_ADDRESS}</div>
    <div>
        <Link to={"/signin"}>Sign in</Link>
        <Link to={'/signup'}>Sign up</Link>
    </div>
    </>
  )
}
