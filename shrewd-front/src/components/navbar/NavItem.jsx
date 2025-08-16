import { Link } from "react-router-dom";
import styles from "./css/NavItem.module.css";

export const NavItem = ({ title, href, icon: Icon, hovered, index }) => (
    <Link
        className={`${styles.navItem} ${hovered ? styles.visible : ''}`}
        to={href}
        style={{ transitionDelay: hovered ? `${index * 80}ms` : '0ms' }}
    >
        <div className={styles.iconContainer}>
            <Icon />
        </div>
        <span className={styles.title}>{title}</span>
    </Link>
);