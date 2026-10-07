import { useEffect, useState } from "react";
import api, { errMsg } from "../api/axios";

export default function AdminPanel() {
  const [users, setUsers] = useState([]);
  const [stats, setStats] = useState(null);
  const [search, setSearch] = useState("");
  const [error, setError] = useState("");

  const load = async () => {
    try {
      const [u, s] = await Promise.all([api.get("/admin/users"), api.get("/admin/stats")]);
      setUsers(u.data);
      setStats(s.data);
    } catch (e) {
      setError(errMsg(e));
    }
  };

  useEffect(() => { load(); }, []);

  const remove = async (u) => {
    if (!window.confirm(`Delete ${u.fullName} and all their data?`)) return;
    try {
      await api.delete(`/admin/users/${u.id}`);
      load();
    } catch (e) {
      setError(errMsg(e));
    }
  };

  const filtered = users.filter((u) =>
    `${u.fullName} ${u.username} ${u.email}`.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div>
      <h2 className="page-title">Admin Panel</h2>
      {error && <p className="error">{error}</p>}

      {stats && (
        <div className="stat-grid">
          <div className="stat-card"><span>{stats.totalUsers}</span>Total users</div>
          <div className="stat-card"><span>{stats.creators}</span>Creators</div>
          <div className="stat-card"><span>{stats.recruiters}</span>Recruiters</div>
          <div className="stat-card"><span>{stats.projects}</span>Projects</div>
          <div className="stat-card"><span>{stats.messages}</span>Messages</div>
        </div>
      )}

      <input className="search left" placeholder="Search users by name, username or email..."
             value={search} onChange={(e) => setSearch(e.target.value)} />

      <table>
        <thead>
          <tr><th>ID</th><th>Name</th><th>Username</th><th>Email</th><th>Role</th><th>Actions</th></tr>
        </thead>
        <tbody>
          {filtered.length === 0 && <tr><td colSpan="6">No users found</td></tr>}
          {filtered.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.fullName}</td>
              <td>{u.username}</td>
              <td>{u.email}</td>
              <td><span className={`role-tag role-${u.role}`}>{u.role}</span></td>
              <td>
                {u.role === "CREATOR" && (
                  <a className="btn small ghost" href={`/u/${u.username}`} target="_blank" rel="noreferrer">View</a>
                )}
                {u.role !== "ADMIN" && (
                  <button className="btn small danger" onClick={() => remove(u)}>Delete</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}