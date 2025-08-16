import styles from './css/MenuBar.module.css'
import {Logo} from "../logo/Logo.jsx";
import {Navbar} from "../navbar/Navbar.jsx";
import {ThemeSwitcher} from "../navbar/ThemeSwitcher.jsx";

export const MenuBar = () => {
    return (
        <div className={styles.menuContainer}>
            <Logo />
            <Navbar />
            <ThemeSwitcher />
        </div>
    )
}
