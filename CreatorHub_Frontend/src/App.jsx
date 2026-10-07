import { Routes, Route } from "react-router-dom";
import Navbar from "./Component/Navbar";
import ProtectedRoute from "./Component/ProtectedRoute";
import Home from "./Pages/Home";
import Login from "./Pages/Login";
import Register from "./Pages/Register";
import Dashboard from "./Pages/Dashboard";
import PublicPortfolio from "./Pages/PublicPortfolio";
import AdminPanel from "./Pages/AdminPanel";
import RecruiterProfile from "./Pages/RecruiterProfile";

export default function App() {
  return (
    <>
      <Navbar />
      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/u/:username" element={<PublicPortfolio />} />
          <Route path="/r/:username" element={<RecruiterProfile />} />
          <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
          <Route path="/admin" element={<ProtectedRoute roles={["ADMIN"]}><AdminPanel /></ProtectedRoute>} />
          <Route path="*" element={<p>Page not found</p>} />
        </Routes>
      </main>
    </>
  );
}