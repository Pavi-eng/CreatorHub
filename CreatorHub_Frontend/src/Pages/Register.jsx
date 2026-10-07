import { useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
import api, { errMsg } from "../api/axios";
import { useAuth } from "../context/AuthContext";

export default function Register() {
  const [form, setForm] = useState({ fullName: "", username: "", email: "", password: "", role: "CREATOR" });
  const [error, setError] = useState("");
  const location = useLocation();
  const from = location.state?.from;

  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/register", form);
      login(res.data);
      navigate(from || "/dashboard");
    } catch (e) {
      setError(errMsg(e));
    }
  };

  return (
    <form className="card auth-card" onSubmit={handleSubmit}>
      <h2>Create account</h2>
      <label>Full name<input name="fullName" value={form.fullName} onChange={handleChange} required /></label>
      <label>Username<input name="username" value={form.username} onChange={handleChange} required /></label>
      <label>Email<input name="email" type="email" value={form.email} onChange={handleChange} required /></label>
      <label>Password (min 6)<input name="password" type="password" value={form.password} onChange={handleChange} required minLength={6} /></label>
      <label>I am a
        <select name="role" value={form.role} onChange={handleChange}>
          <option value="CREATOR">Creator (build a portfolio)</option>
          <option value="RECRUITER">Recruiter / Client</option>
        </select>
      </label>
      {error && <p className="error">{error}</p>}
      <button className="btn" type="submit">Sign up</button>
      <p>Already have an account? <Link to="/login">Login</Link></p>
    </form>
  );
}