import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api, { errMsg } from "../api/axios";
import ContactForm from "../Component/ContactForm";

function Section({ title, children, empty }) {
  if (empty) return null;
  return (
    <section>
      <h2 className="section-title">{title}</h2>
      {children}
    </section>
  );
}

export default function PublicPortfolio() {
  const { username } = useParams();
  const [data, setData] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    api.get(`/public/creators/${username}`)
      .then((r) => setData(r.data))
      .catch((e) => setError(errMsg(e)));
  }, [username]);

  if (error) return <p className="error">{error}</p>;
  if (!data) return <p>Loading...</p>;

  const { profile: p, projects, skills, certifications, experiences, achievements } = data;

  return (
    <div>
      <header className="card profile-header">
        <div className="avatar big">{p.avatarUrl ? <img src={p.avatarUrl} alt="" /> : p.fullName[0]}</div>
        <div>
          <h1>{p.fullName}</h1>
          <p className="muted">{p.profession} {p.location && `· ${p.location}`}</p>
          <p>{p.headline}</p>
          <div className="row">
            {p.githubUrl && <a href={p.githubUrl} target="_blank" rel="noreferrer">GitHub</a>}
            {p.linkedinUrl && <a href={p.linkedinUrl} target="_blank" rel="noreferrer">LinkedIn</a>}
            {p.websiteUrl && <a href={p.websiteUrl} target="_blank" rel="noreferrer">Website</a>}
          </div>
        </div>
      </header>

      <Section title="About" empty={!p.bio}><p className="pre">{p.bio}</p></Section>

      <Section title="Projects" empty={projects.length === 0}>
        <div className="grid">
          {projects.map((x) => (
            <div key={x.id} className="card">
              {x.imageUrl && <img className="project-img" src={x.imageUrl} alt="" />}
              <h3>{x.title}</h3>
              <p>{x.description}</p>
              {x.techStack && (
                <div className="chips">
                  {x.techStack.split(",").map((t) => <span key={t} className="chip">{t.trim()}</span>)}
                </div>
              )}
              <div className="row">
                {x.projectUrl && <a href={x.projectUrl} target="_blank" rel="noreferrer">Live</a>}
                {x.repoUrl && <a href={x.repoUrl} target="_blank" rel="noreferrer">Code</a>}
              </div>
            </div>
          ))}
        </div>
      </Section>

      <Section title="Skills" empty={skills.length === 0}>
        <div className="chips">
          {skills.map((s) => <span key={s.id} className="chip">{s.name}{s.level && ` · ${s.level}`}</span>)}
        </div>
      </Section>

      <Section title="Experience" empty={experiences.length === 0}>
        {experiences.map((x) => (
          <div key={x.id} className="card">
            <h3>{x.jobTitle} @ {x.company}</h3>
            <small>{x.startDate || "?"} – {x.endDate || "Present"}</small>
            <p>{x.description}</p>
          </div>
        ))}
      </Section>

      <Section title="Certifications" empty={certifications.length === 0}>
        {certifications.map((x) => (
          <div key={x.id} className="card">
            <h3>{x.title}</h3>
            <small>{x.issuer} {x.issueDate && `· ${x.issueDate}`}</small>
            {x.credentialUrl && <p><a href={x.credentialUrl} target="_blank" rel="noreferrer">View credential</a></p>}
          </div>
        ))}
      </Section>

      <Section title="Achievements" empty={achievements.length === 0}>
        {achievements.map((x) => (
          <div key={x.id} className="card">
            <h3>{x.title}</h3>
            <small>{x.achievedDate}</small>
            <p>{x.description}</p>
          </div>
        ))}
      </Section>

      <h2 className="section-title">Contact {p.fullName.split(" ")[0]}</h2>
      <ContactForm path={`/public/creators/${username}/contact`} username={username} />
    </div>
  );
}