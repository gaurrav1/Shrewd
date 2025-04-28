import { useUser } from "../../contexts/UserContext";
import {LogoutButton} from "../../components/tenants/LogoutButton.jsx";

export function AdminDashboard() {
    const { user } = useUser();
    return (
        <div>
            <h1>Admin Dashboard</h1>
            <p>Welcome {user?.username}, you have full access.</p>
            <LogoutButton/>
        </div>
    );
}
