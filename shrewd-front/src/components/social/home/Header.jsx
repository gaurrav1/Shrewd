import { Link } from 'react-router-dom';
import headImg from '../../../static/images/head.svg';
import styles from './css/Header.module.css';

export const Header = () => (
    <header className={styles.head}>
        <div className={styles.shape + ' ' + styles.shape1}></div>
        <div className={styles.shape + ' ' + styles.shape2}></div>
        <div className={styles.shape + ' ' + styles.shape3}></div>
        <div className={styles.shape + ' ' + styles.shape4}></div>

        <div className={styles.left}>
            <div className={styles.upper}>
                <h3>Vision everywhere!</h3>
                <p>Lorem ipsum dolor sit amet consectetur...</p>
            </div>
            <div className={styles.lower}>
                <Link to="/signin" className={styles.signin}>Sign in</Link>
                <Link to="/signup" className={styles.signin}>Register</Link>
            </div>
        </div>

        <div className={styles.right}>
            <img src={headImg} alt="" className={styles.head_img} />
        </div>
    </header>
);
