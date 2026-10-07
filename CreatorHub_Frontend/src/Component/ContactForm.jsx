import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import api, { errMsg } from "../api/axios";
import { useAuth } from "../context/AuthContext";

export default function ContactForm({ path, username }) {
  const { user } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const [form, setForm] = useState({ subject: "", content: "" });
  const [sent, setSent] = useState("");
  const [error, setError] = useState("");

  const me = user || JSON.parse(localStorage.getItem("user") || "null");
  const loggedIn = !!localStorage.getItem("token") && !!me;

  if (loggedIn && me.username === username) {
    return <div className="card muted">This is your own profile. You cannot message yourself.</div>;
  }

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const send = async (e) => {
    e.preventDefault();
    setSent("");
    setError("");

    if (!loggedIn) {
      navigate("/login", { state: { from: location.pathname } });
      return;
    }

    try {
      const res = await api.post(path, {
        senderName: me.fullName,
        senderEmail: me.email,
        subject: form.subject,
        content: form.content,
      });
      setSent(res.data.message);
      setForm({ subject: "", content: "" });
    } catch (e) {
      setError(errMsg(e));
    }
  };

  return (
    <form className="card form-grid" onSubmit={send}>
      {loggedIn ? (
        <p className="muted full">Sending as <strong>{me.fullName}</strong> ({me.role.toLowerCase()})</p>
      ) : (
        <p className="muted full">Please login to send a message.</p>
      )}
      <label className="full">Subject
        <input name="subject" value={form.subject} onChange={handleChange} />
      </label>
      <label className="full">Message
        <textarea name="content" rows="4" value={form.content} onChange={handleChange} required />
      </label>
      <div className="full row">
        <button className="btn" type="submit">{loggedIn ? "Send message" : "Login to send message"}</button>
      </div>
      {sent && <p className="success full">{sent}</p>}
      {error && <p className="error full">{error}</p>}
    </form>
  );
}