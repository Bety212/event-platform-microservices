import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css"; // ⚠️ IMPORTANT : même CSS que Register

function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
  e.preventDefault();

  try {
    const res = await fetch("http://localhost:8080/auth-service/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!res.ok) throw new Error("Email ou mot de passe incorrect");

    const data = await res.json();

    // ✅ STOCKAGE CORRECT
    localStorage.setItem("token", data.token);
    localStorage.setItem("user", JSON.stringify(data.user)); // 🔥 IMPORTANT

    navigate("/events");
  } catch (err) {
    setError(err.message);
  }
};


  return (
    <div className="auth-page">
      <div className="auth-box">

        {/* LEFT */}
        <div className="auth-left">
          <h2>Connexion</h2>
          <p className="auth-subtitle">Accédez à votre compte EventPlatform</p>

          {error && <div className="auth-error">{error}</div>}

          <form onSubmit={handleLogin} className="auth-form">
            <label>Email</label>
            <input
              type="email"
              placeholder="Email address"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />

            <label>Mot de passe</label>
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            <button type="submit">Se connecter</button>
          </form>

          <p className="auth-footer">
            Pas encore de compte ?
            <span onClick={() => navigate("/register")}> S’inscrire</span>
          </p>
        </div>

        {/* RIGHT */}
        <div className="auth-right">
          <div className="auth-brand">
            <h3>EventPlatform</h3>
            <p>Réservez vos événements en toute simplicité</p>
          </div>
        </div>

      </div>
    </div>
  );
}

export default Login;
