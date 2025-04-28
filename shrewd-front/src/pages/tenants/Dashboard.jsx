import { useUser } from "../../contexts/UserContext";
import {LogoutButton} from "../../components/tenants/LogoutButton.jsx";

export function Dashboard() {
  const { user } = useUser();

  if (!user) {
    return <h2>Please sign in first!</h2>;
  }

  return (
      <div className="dashboard">
        <h1>Hi {user.username}! You are a {user.role}</h1>
        <LogoutButton/>
      </div>
  );
}
