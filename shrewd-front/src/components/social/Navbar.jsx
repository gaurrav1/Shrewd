import logo from './assets/images/logo.jpg';
import { NavItem } from './NavItem';
import HomeIcon from './assets/images/home.svg';
import AuthIcon from './assets/images/auth.svg';

export const icons = {
    home: HomeIcon,
    auth: AuthIcon,
};

const navItems = [
    { title: 'Home', href: 'home', icon: icons.home },
    { title: 'How SAPS works?', href: 'home', icon: icons.home },
    { title: 'Queries', href: 'home', icon: icons.home },
    { title: 'Login/ Register', href: 'signup', icon: icons.auth },
];

export const Navbar = () => (
    <nav id="navbar" className="navbar">
        <div className="logoDiv">
            <img src={logo} alt="logo" className="logo" />
        </div>
        <div className="icon_container">
            {navItems.map((item, index) => (
                <NavItem key={index} {...item} />
            ))}
        </div>
    </nav>
);