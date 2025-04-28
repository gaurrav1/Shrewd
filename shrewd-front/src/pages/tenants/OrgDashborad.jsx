import { useUser } from "../../contexts/UserContext";
import {LogoutButton} from "../../components/tenants/LogoutButton.jsx";

export function OrgDashborad() {
    const { user } = useUser();
    return (
        <div>
            <h1>Senior Developer Dashboard</h1>
            <p>Welcome {user?.username}, manage your projects here.</p>
            <LogoutButton/>
        </div>
    );
}
