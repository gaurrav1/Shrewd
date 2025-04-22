import {Logo} from "../../components/social/Logo.jsx";
import {Navbar} from "../../components/social/Navbar.jsx";
import {Header} from "../../components/social/Header.jsx";

export function Home() {
  return (
      <>
          <Logo />
          <div className="pers">
              <Navbar />
          </div>
          <Header />
      </>
)
}
