import { Link, useNavigate } from "react-router-dom";
import { useMemo } from "react";

export default function Cart() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const cart = useMemo(() => {
    return JSON.parse(localStorage.getItem("cart") || "[]");
  }, []);

  const total = cart.reduce((sum, item) => sum + (item.price || 0) * (item.qty || 1), 0);

  const handleCheckout = () => {
    // ✅ si connecté -> checkout, sinon -> login (puis retour checkout)
    if (token) navigate("/checkout");
    else navigate("/login", { state: { from: { pathname: "/checkout" } } });
  };

  return (
    <div style={{ padding: 24 }}>
      <h2>Panier</h2>

      {cart.length === 0 ? (
        <p>Panier vide. <Link to="/">Voir les événements</Link></p>
      ) : (
        <>
          {cart.map((it) => (
            <div key={it.id} style={{ border: "1px solid #ddd", padding: 12, marginBottom: 10 }}>
              <b>{it.title}</b> — {it.qty} x {it.price} MAD
            </div>
          ))}

          <h3>Total: {total} MAD</h3>

          {!token && (
            <p style={{ marginTop: 10 }}>
              🔐 Pour réserver et payer, tu dois te connecter.
            </p>
          )}

          <button onClick={handleCheckout}>
            {token ? "Passer au paiement" : "Se connecter pour continuer"}
          </button>
        </>
      )}
    </div>
  );
}
