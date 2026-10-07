import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api, { errMsg } from "../api/axios";
import ContactForm from "../Component/ContactForm";

export default function RecruiterProfile() {
  const { username } = useParams();
  const [p, setP] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get(`/public/recruiters/${username}`)
      .then((r) => setP(r.data))
      .catch((e) => setError(errMsg(e)));
  }, [username]);

  if (error) return <p className="error">{error}</p>;
  if (!p) return <p>Loading...</p>;

  return (
    <div>
      <header className="card profile-header">
        <div className="avatar big">{p.avatarUrl ? <img src={p.avatarUrl} alt="" /> : p.fullName[0]}</div>
        <div>
          <h1>{p.fullName}</h1>
          <span className="role-tag role-RECRUITER">RECRUITER</span>
          <p className="muted">{p.profession} {p.location && `· ${p.location}`}</p>
          <p>{p.headline}</p>
          <div className="row">
            {p.linkedinUrl && <a href={p.linkedinUrl} target="_blank" rel="noreferrer">LinkedIn</a>}
            {p.websiteUrl && <a href={p.websiteUrl} target="_blank" rel="noreferrer">Website</a>}
          </div>
        </div>
      </header>

      {p.bio && (
        <section>
          <h2 className="section-title">About</h2>
          <p className="pre">{p.bio}</p>
        </section>
      )}

      <h2 className="section-title">Contact {p.fullName.split(" ")[0]}</h2>
      <ContactForm path={`/public/recruiters/${username}/contact`} username={username} />
    </div>
  );
}