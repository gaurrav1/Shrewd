import {Link} from "react-router-dom";
import styles from "./css/NavItem.module.css"

export const NavItem = ({ title, href, icon: Icon }) => (
    <Link className={styles.option} to={href}>
        <div className={styles.nav}>
            <div className={styles.icon}>
                <img src={Icon} alt={`${title} icon`} width={40} height={40} />
            </div>
            <div className={styles.title}>{title}</div>
        </div>
    </Link>
);