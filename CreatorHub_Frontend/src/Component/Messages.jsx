import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api, { errMsg } from "../api/axios";

const profilePath = (m) => {
  if (!m.senderUsername) return null;
  if (m.senderRole === "CREATOR") return `/u/${m.senderUsername}`;
  if (m.senderRole === "RECRUITER") return `/r/${m.senderUsername}`;
  return null;
};

export default function Messages({ onUpdate }) {
  const [messages, setMessages] = useState([]);
  const [error, setError] = useState("");

  const load = async () => {
    try {
      const res = await api.get("/messages");
      setMessages(res.data);
      if (onUpdate) onUpdate(res.data.filter((m) => !m.seen).length);
    } catch (e) {
      setError(errMsg(e));
    }
  };

  useEffect(() => { load(); }, []);

  const markRead = async (id) => { await api.put(`/messages/${id}/read`); load(); };

  const remove = async (id) => {
    if (!window.confirm("Delete this message?")) return;
    await api.delete(`/messages/${id}`);
    load();
  };

  return (
    <div>
      {error && <p className="error">{error}</p>}
      {messages.length === 0 && (
        <div className="card empty">
          <p>No messages yet.</p>
          <p className="muted">Messages from creators and recruiters will appear here.</p>
        </div>
      )}
      {messages.map((m) => {
        const path = profilePath(m);
        return (
          <div key={m.id} className={`card message ${m.seen ? "" : "unread"}`}>
            <div className="row between">
              <div className="row">
                <div className="avatar small">{m.senderName?.[0]}</div>
                <div>
                  <strong>{m.subject || "(no subject)"}</strong>
                  {!m.seen && <span className="new-tag">NEW</span>}
                  <div className="muted small-text">
                    From {m.senderName} · {m.senderEmail}
                    {m.senderRole && <span className={`role-tag role-${m.senderRole}`} style={{ marginLeft: 8 }}>{m.senderRole}</span>}
                  </div>
                </div>
              </div>
              <small className="muted">{m.createdAt?.replace("T", " ").slice(0, 16)}</small>
            </div>
            <p className="pre">{m.content}</p>
            <div className="row">
              {path && <Link className="btn small" to={path}>Reply</Link>}
              <a className="btn small ghost"
                 href={`mailto:${m.senderEmail}?subject=${encodeURIComponent("Re: " + (m.subject || ""))}`}>
                Email
              </a>
              {!m.seen && <button className="btn small ghost" onClick={() => markRead(m.id)}>Mark as read</button>}
              <button className="btn small danger" onClick={() => remove(m.id)}>Delete</button>
            </div>
          </div>
        );
      })}
    </div>
  );
}