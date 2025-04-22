import {Link} from "react-router-dom";

export const NavItem = ({ title, href, icon: Icon }) => (
    <Link className="option" to={href}>
        <div className="nav">
            <div className="icon">
                <img src={Icon} alt={`${title} icon`} width={40} height={40} />
            </div>
            <div className="title">{title}</div>
        </div>
    </Link>
);