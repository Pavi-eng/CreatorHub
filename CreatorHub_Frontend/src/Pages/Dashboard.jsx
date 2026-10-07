import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api/axios";
import { useAuth } from "../context/AuthContext";
import { SECTIONS } from "../config/sections";
import ItemManager from "../Component/ItemManager";
import ProfileForm from "../Component/ProfileForm";
import Messages from "../Component/Messages";

export default function Dashboard() {
  const { user } = useAuth();
  const [tab, setTab] = useState("profile");
  const [unread, setUnread] = useState(0);

  const isCreator = user.role === "CREATOR";
  const isRecruiter = user.role === "RECRUITER";

  useEffect(() => {
    if (isCreator || isRecruiter) {
      api.get("/messages")
        .then((r) => setUnread(r.data.filter((m) => !m.seen).length))
        .catch(() => {});
    }
  }, [isCreator, isRecruiter]);

  if (user.role === "ADMIN") {
    return (
      <div className="card">
        <h2>Welcome, {user.fullName}</h2>
        <p>You are logged in as an administrator.</p>
        <Link className="btn" to="/admin">Open Admin Panel</Link>
      </div>
    );
  }

  const tabs = isCreator ? ["profile", ...Object.keys(SECTIONS), "messages"] : ["profile", "messages"];
  const publicPath = isCreator ? `/u/${user.username}` : `/r/${user.username}`;

  return (
    <div>
      <div className="row between">
        <h2 className="page-title">My Dashboard</h2>
        <Link className="btn ghost" to={publicPath}>View public profile</Link>
      </div>
      <p className="muted">Public link: {window.location.origin}{publicPath}</p>

      <div className="tabs">
        {tabs.map((t) => (
          <button key={t} className={tab === t ? "tab active" : "tab"} onClick={() => setTab(t)}>
            {t === "profile" ? "Profile" : t === "messages" ? "Messages" : SECTIONS[t].label}
            {t === "messages" && unread > 0 && <span className="count">{unread}</span>}
          </button>
        ))}
      </div>

      {tab === "profile" && <ProfileForm role={user.role} />}
      {tab === "messages" && <Messages onUpdate={setUnread} />}
      {isCreator && SECTIONS[tab] && (
        <ItemManager key={tab} endpoint={tab} fields={SECTIONS[tab].fields} columns={SECTIONS[tab].columns} />
      )}
    </div>
  );
}