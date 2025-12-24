import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Register.css"; // ✅ OBLIGATOIRE

function Register() {
  const navigate = useNavigate();

 const [form, setForm] = useState({
  username: "",
  firstName: "",
  lastName: "",
  email: "",
  confirmEmail: "",
  phone: "",
  password: "",
  confirmPassword: ""
});


  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch("http://localhost:8080/auth-service/auth/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      if (!res.ok) throw new Error("Erreur lors de l’inscription");

      navigate("/login");
    } catch (err) {
      setError(err.message);
    }
  };
return (
  <div className="auth-page">
    <div className="auth-box">
      
      {/* LEFT */}
      <div className="auth-left">
        <h2>Créer un compte</h2>
        <p className="auth-subtitle">Inscription à EventPlatform</p>

        {error && <div className="auth-error">{error}</div>}

        <form onSubmit={handleRegister} className="auth-form">
          <label>Email</label>
          <input
            type="email"
            name="email"
            placeholder="Email address"
            value={form.email}
            onChange={handleChange}
            required
          />

          <label>Confirmer email</label>
          <input
            type="email"
            name="confirmEmail"
            placeholder="Confirm email"
            value={form.confirmEmail}
            onChange={handleChange}
            required
          />

          <label>Mot de passe</label>
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={form.password}
            onChange={handleChange}
            required
          />

          <label>Confirmer mot de passe</label>
          <input
            type="password"
            name="confirmPassword"
            placeholder="Repeat password"
            value={form.confirmPassword}
            onChange={handleChange}
            required
          />

          <button type="submit">S’inscrire</button>
        </form>

        <p className="auth-footer">
          Déjà un compte ?
          <span onClick={() => navigate("/login")}> Se connecter</span>
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

export default Register;
