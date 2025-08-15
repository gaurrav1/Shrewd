import logo from '../../static/images/logo.jpg';
import { NavItem } from './NavItem.jsx';
import HomeIcon from '../../static/images/home.svg';
import AuthIcon from '../../static/images/auth.svg';
import {ThemeSwitcher} from "./ThemeSwitcher.jsx";
import styles from "./css/Navbar.module.css"

export const icons = {
    home: HomeIcon,
    auth: AuthIcon,
};

const navItems = [
    { title: 'Shrewd', href: '/', icon: icons.home },
    { title: 'Explore', href: 'explore', icon: icons.home },
    { title: 'Pricing', href: 'pricing', icon: icons.home },
    { title: 'Login/ Register', href: 'signup', icon: icons.auth },
];

export const Navbar = () => (
    <div className={styles.pers}>
        <nav id="navbar" className={styles.navbar}>
            <div className={styles.logoDiv}>
                <img src={logo} alt="logo" className={styles.logo} />
            </div>
            <div className={styles.icon_container}>
                {navItems.map((item, index) => (
                    <NavItem key={index} {...item} />
                ))}
            </div>
            <ThemeSwitcher />
        </nav>
    </div>
);