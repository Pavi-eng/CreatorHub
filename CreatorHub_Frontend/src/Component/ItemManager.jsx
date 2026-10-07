import { useEffect, useState } from "react";
import api, { errMsg } from "../api/axios";

export default function ItemManager({ endpoint, fields, columns }) {
  const blank = Object.fromEntries(fields.map((f) => [f.name, ""]));
  const [items, setItems] = useState([]);
  const [form, setForm] = useState(blank);
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState("");

  const load = async () => {
    try {
      const res = await api.get(`/${endpoint}`);
      setItems(res.data);
    } catch (e) {
      setError(errMsg(e));
    }
  };

  useEffect(() => { load(); }, [endpoint]);

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const reset = () => { setForm(blank); setEditId(null); };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    // empty string -> null (dates save aaga)
    const payload = Object.fromEntries(Object.entries(form).map(([k, v]) => [k, v === "" ? null : v]));
    try {
      if (editId) await api.put(`/${endpoint}/${editId}`, payload);
      else await api.post(`/${endpoint}`, payload);
      reset();
      load();
    } catch (e) {
      setError(errMsg(e));
    }
  };

  const handleEdit = (item) => {
    setForm(Object.fromEntries(fields.map((f) => [f.name, item[f.name] ?? ""])));
    setEditId(item.id);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this item?")) return;
    try {
      await api.delete(`/${endpoint}/${id}`);
      load();
    } catch (e) {
      setError(errMsg(e));
    }
  };

  const label = (name) => fields.find((f) => f.name === name)?.label.split(" (")[0] || name;

  return (
    <div>
      <form className="card form-grid" onSubmit={handleSubmit}>
        {fields.map((f) => (
          <label key={f.name} className={f.type === "textarea" ? "full" : ""}>
            {f.label}
            {f.type === "textarea" ? (
              <textarea name={f.name} rows="3" value={form[f.name]} onChange={handleChange} />
            ) : f.type === "select" ? (
              <select name={f.name} value={form[f.name]} onChange={handleChange}>
                <option value="">Select...</option>
                {f.options.map((o) => <option key={o}>{o}</option>)}
              </select>
            ) : (
              <input name={f.name} type={f.type || "text"} value={form[f.name]}
                     onChange={handleChange} required={f.required} />
            )}
          </label>
        ))}
        <div className="full row">
          <button className="btn" type="submit">{editId ? "Update" : "Add"}</button>
          {editId && <button className="btn ghost" type="button" onClick={reset}>Cancel</button>}
        </div>
        {error && <p className="error full">{error}</p>}
      </form>

      <table>
        <thead>
          <tr>{columns.map((c) => <th key={c}>{label(c)}</th>)}<th>Actions</th></tr>
        </thead>
        <tbody>
          {items.length === 0 && <tr><td colSpan={columns.length + 1}>Nothing added yet</td></tr>}
          {items.map((item) => (
            <tr key={item.id}>
              {columns.map((c) => <td key={c}>{item[c] || "-"}</td>)}
              <td>
                <button className="btn small ghost" onClick={() => handleEdit(item)}>Edit</button>
                <button className="btn small danger" onClick={() => handleDelete(item.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}