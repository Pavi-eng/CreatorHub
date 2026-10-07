import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api, { errMsg } from "../api/axios";

export default function Home() {
  const [type, setType] = useState("creators");
  const [people, setPeople] = useState([]);
  const [search, setSearch] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const t = setTimeout(() => {
      api.get(`/public/${type}`, { params: { search } })
        .then((r) => { setPeople(r.data); setError(""); })
        .catch((e) => setError(errMsg(e)));
    }, 300);
    return () => clearTimeout(t);
  }, [search, type]);

  const base = type === "creators" ? "/u/" : "/r/";

  return (
    <div>
      <section className="hero">
        <h1>Showcase your work. Get discovered.</h1>
        <p>Build a professional portfolio and let recruiters and clients find you.</p>
        <input className="search" placeholder="Search by name, profession or headline..."
               value={search} onChange={(e) => setSearch(e.target.value)} />
      </section>

      <div className="tabs">
        <button className={type === "creators" ? "tab active" : "tab"} onClick={() => setType("creators")}>
          Creators
        </button>
        <button className={type === "recruiters" ? "tab active" : "tab"} onClick={() => setType("recruiters")}>
          Recruiters
        </button>
      </div>

      {error && <p className="error">{error}</p>}
      <div className="grid">
        {people.length === 0 && <p>No {type} found.</p>}
        {people.map((c) => (
          <Link key={c.id} to={`${base}${c.username}`} className="card creator-card">
            <div className="avatar">{c.avatarUrl ? <img src={c.avatarUrl} alt="" /> : c.fullName[0]}</div>
            <h3>{c.fullName}</h3>
            <span className={`role-tag role-${c.role}`}>{c.role}</span>
            <p className="muted">{c.profession || (type === "creators" ? "Creator" : "Recruiter")}</p>
            <p>{c.headline}</p>
            {c.location && <small>{c.location}</small>}
          </Link>
        ))}
      </div>
    </div>
  );
}