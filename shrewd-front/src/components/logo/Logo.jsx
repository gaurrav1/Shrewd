import logwin from '../../static/images/logwin.webp';
import styles from './css/Logo.module.css'

export const Logo = () => (
    <div className={styles.main_logo}>
        <img src={logwin} alt="Main Logo" height="62px" width="250px" />
    </div>
);
