import { useState } from "react";
import { Link, useNavigate ,useLocation} from "react-router-dom";
import api, { errMsg } from "../api/axios";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");
  const { login } = useAuth();
  const location = useLocation();
  const from = location.state?.from;
  const navigate = useNavigate();

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/login", form);
      login(res.data);
      navigate(from || (res.data.role==="ADMIN"?"/AdminPanel":"/Dashboard"));
    } catch (e) {
      setError(errMsg(e));
    }
  };

  return (
    <form className="card auth-card" onSubmit={handleSubmit}>
      <h2>Login</h2>
      <label>Email<input name="email" type="email" value={form.email} onChange={handleChange} required /></label>
      <label>Password<input name="password" type="password" value={form.password} onChange={handleChange} required /></label>
      {error && <p className="error">{error}</p>}
      <button className="btn" type="submit">Login</button>
      <p>New here? <Link to="/register" state={{from}}>Create an account</Link></p>
    </form>
  );
}