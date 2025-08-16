import {Logo} from "../../../components/logo/Logo.jsx";
import {Navbar} from "../../../components/navbar/Navbar.jsx";
import {Header} from "../../../components/social/home/Header.jsx";
import styles from "./css/Home.module.css";
import {ThemeSwitcher} from "../../../components/navbar/ThemeSwitcher.jsx";
import {MenuBar} from "../../../components/menubar/MenuBar.jsx";
import {ShrewdHorizontalLookUpIcon, ShrewdIcon} from "../../../svgs/Shrewd.jsx";

export function Home() {
  return (
      <div className={styles.container}>
          <MenuBar />
          <ShrewdIcon />
          <div className={styles.content}>
              <Header />
          </div>
      </div>
  );
}