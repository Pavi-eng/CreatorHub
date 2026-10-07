import { useEffect, useState } from "react";
import api, { errMsg } from "../api/axios";

const fieldsFor = (role) => [
  ["fullName", "Full name"],
  ["headline", role === "RECRUITER" ? "Headline (e.g. Hiring Java developers)" : "Headline (e.g. Full-stack developer)"],
  ["profession", role === "RECRUITER" ? "Company / Designation" : "Profession"],
  ["location", "Location"],
  ["avatarUrl", "Avatar image URL"],
  ["githubUrl", "GitHub URL"],
  ["linkedinUrl", "LinkedIn URL"],
  ["websiteUrl", "Website URL"],
];

export default function ProfileForm({ role }) {
  const FIELDS = fieldsFor(role);
  const [form, setForm] = useState(null);
  const [msg, setMsg] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    api.get("/profile").then((r) => {
      const d = r.data;
      setForm(Object.fromEntries([...FIELDS.map(([k]) => [k, d[k] ?? ""]), ["bio", d.bio ?? ""]]));
    }).catch((e) => setError(errMsg(e)));
  }, []);

  if (!form) return <p>{error || "Loading..."}</p>;

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMsg(""); setError("");
    try {
      await api.put("/profile", form);
      setMsg("Profile saved");
    } catch (e) {
      setError(errMsg(e));
    }
  };

  return (
    <form className="card form-grid" onSubmit={handleSubmit}>
      {FIELDS.map(([name, label]) => (
        <label key={name}>{label}
          <input name={name} value={form[name]} onChange={handleChange} required={name === "fullName"} />
        </label>
      ))}
      <label className="full">About me / Bio
        <textarea name="bio" rows="5" value={form.bio} onChange={handleChange} />
      </label>
      <div className="full row"><button className="btn" type="submit">Save profile</button></div>
      {msg && <p className="success full">{msg}</p>}
      {error && <p className="error full">{error}</p>}
    </form>
  );
}