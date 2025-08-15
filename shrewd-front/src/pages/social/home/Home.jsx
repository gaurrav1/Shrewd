import {Logo} from "../../../components/logo/Logo.jsx";
import {Navbar} from "../../../components/navbar/Navbar.jsx";
import {Header} from "../../../components/social/home/Header.jsx";
import styles from "./css/Home.module.css";

export function Home() {
  return (
      <div className={styles.container}>
          <Logo />
          <Navbar />
          <div className={styles.content}>
              <Header />
          </div>
      </div>
  );
}