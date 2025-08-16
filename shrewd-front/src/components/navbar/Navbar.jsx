import { useState, useEffect } from 'react';
import logo from '../../static/images/logo.jpg';
import { NavItem } from './NavItem.jsx';
import {HomeIcon} from '../../svgs/NavSvgs.jsx'
import {ILogin} from '../../svgs/NavSvgs.jsx';
import {PriceIcon} from '../../svgs/NavSvgs.jsx'
import {ExploreIcon} from '../../svgs/NavSvgs.jsx';
import { ThemeSwitcher } from "./ThemeSwitcher.jsx";
import styles from "./css/Navbar.module.css";

export const icons = {
    home: HomeIcon,
    auth: ILogin,
    price: PriceIcon,
    explore: ExploreIcon,
};

const navItems = [
    { title: 'Shrewd', href: '/', icon: icons.home },
    { title: 'Explore', href: 'explore', icon: icons.explore },
    { title: 'Pricing', href: 'pricing', icon: icons.price },
    { title: 'Login', href: 'signup', icon: icons.auth },
];

export const Navbar = () => {
    const [scrolled, setScrolled] = useState(false);
    const [hovered, setHovered] = useState(false);

    useEffect(() => {
        const handleScroll = () => {
            setScrolled(window.scrollY > 10);
        };
        window.addEventListener('scroll', handleScroll);
        return () => window.removeEventListener('scroll', handleScroll);
    }, []);

    return (
        <div
            className={`${styles.navbarContainer} ${scrolled ? styles.scrolled : ''}`}
            onMouseEnter={() => setHovered(true)}
            onMouseLeave={() => setHovered(false)}
        >
            <div className={`${styles.navbar} ${hovered ? styles.expanded : ''}`}>
                {/*<div className={styles.logoContainer}>*/}
                {/*    <img src={logo} alt="logo" className={styles.logo} />*/}
                {/*</div>*/}

                <div className={styles.navItemsContainer}>
                    {navItems.map((item, index) => (
                        <NavItem
                            key={index}
                            {...item}
                            hovered={hovered}
                            index={index}
                        />
                    ))}
                </div>

                {/*<div className={styles.themeSwitcherWrapper}>*/}
                {/*    <ThemeSwitcher hovered={hovered} />*/}
                {/*</div>*/}
            </div>
        </div>
    );
};