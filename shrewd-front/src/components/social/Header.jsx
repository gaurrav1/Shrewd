import { Link } from 'react-router-dom';
import headImg from './assets/images/head.svg';

export const Header = () => (
    <header className="head">
        <div className="shape shape1"></div>
        <div className="shape shape2"></div>
        <div className="shape shape3"></div>
        <div className="shape shape4"></div>

        <div className="left">
            <div className="upper">
                <h3>Vision everywhere!</h3>
                <p>Lorem ipsum dolor sit amet consectetur...</p>
            </div>
            <div className="lower">
                <Link to="/signin" className="signin">Sign in</Link>
                <Link to="/signup" className="signin">Register</Link>
            </div>
        </div>

        <div className="right">
            <img src={headImg} alt="" className="head_img" />
        </div>
    </header>
);
