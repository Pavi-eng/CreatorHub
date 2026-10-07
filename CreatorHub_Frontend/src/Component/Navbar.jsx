import { Link, NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <nav className="navbar">
      <Link to="/" className="brand">Creator<span>Hub</span></Link>
      <div className="nav-links">
        <NavLink to="/" end>Browse</NavLink>
        {user ? (
          <>
            <NavLink to="/dashboard">Dashboard</NavLink>
            {user.role?.toUpperCase().includes("ADMIN") && <NavLink to="/admin">Admin</NavLink>}
            <span className={`role-tag role-${user.role}`}>{user.role}</span>
            <button className="btn small ghost" onClick={() => { logout(); navigate("/"); }}>Logout</button>
          </>
        ) : (
          <>
            <NavLink to="/login">Login</NavLink>
            <Link to="/register" className="btn small">Sign up</Link>
          </>
        )}
      </div>
    </nav>
  );
}