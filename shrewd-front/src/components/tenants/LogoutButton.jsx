import { useUser } from "../../contexts/UserContext";
import { useNavigate } from "react-router-dom";

export function LogoutButton() {
    const { logout } = useUser();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/signin");
    };

    return (
        <button onClick={handleLogout}>
            Logout
        </button>
    );
}
