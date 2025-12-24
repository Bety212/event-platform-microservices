import { Link, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./Navbar.css";

export default function Navbar() {
  const location = useLocation();
  const navigate = useNavigate();
  const [query, setQuery] = useState("");
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // 🔐 Vérifier si l'utilisateur est connecté
  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuthenticated(!!token);
  }, [location.pathname]);

  const handleSearch = (e) => {
    e.preventDefault();
    navigate(`/events?q=${encodeURIComponent(query.trim())}`);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userId");
    navigate("/events");
    setIsAuthenticated(false);
  };

  const isActive = (path) => location.pathname.startsWith(path);

  return (
    <header className="navbar">
      <div className="navbar__left">
        <Link to="/events" className="navbar__brand">
          EventPlatform
        </Link>

        <form className="navbar__search" onSubmit={handleSearch}>
          <input
            type="text"
            placeholder="Rechercher un événement…"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          <button type="submit" disabled={!query.trim()}>
            Rechercher
          </button>
        </form>
      </div>

      <nav className="navbar__right">
        <Link
          className={`navlink ${isActive("/events") ? "active" : ""}`}
          to="/events"
        >
          Événements
        </Link>

        <Link
          className={`navlink ${isActive("/my-tickets") ? "active" : ""}`}
          to="/my-tickets"
        >
          Mes billets
        </Link>

        {/* 🔥 AUTH LOGIC */}
        {!isAuthenticated ? (
          <div className="profile-menu">
            <span className="navlink">Profil ▾</span>
            <div className="profile-dropdown">
              <button onClick={() => navigate("/login")}>
                Se connecter
              </button>
              <button onClick={() => navigate("/register")}>
                S’inscrire
              </button>
            </div>
          </div>
        ) : (
          <button className="logout-btn" onClick={handleLogout}>
            Déconnexion
          </button>
        )}
      </nav>
    </header>
  );
}
