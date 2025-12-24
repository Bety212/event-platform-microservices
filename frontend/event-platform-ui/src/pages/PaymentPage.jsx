import { useParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import api from "../api/api";
import { getUser } from "../utils/auth";
import "./PaymentPage.css";

export default function PaymentPage() {
  const { reservationId } = useParams();
  const navigate = useNavigate();
  const user = getUser();

  const [accepted, setAccepted] = useState(false);
  const [loading, setLoading] = useState(false);

  const handlePay = async () => {
  if (!accepted) {
    alert("Veuillez accepter les conditions");
    return;
  }

  setLoading(true);
  try {
    // 1️⃣ Paiement
    await api.post("/payment-service/payments", {
      reservationId: Number(reservationId),
      userId: user.id,
      paymentMethod: "CARD",
      simulateFail: false
    });

    // 2️⃣ Marquer réservation payée
    try {
      await api.put(`/reservation-service/reservations/${reservationId}/paid`);
    } catch (e) {
      console.warn("Paiement OK mais réservation non mise à jour", e);
    }

    alert("✅ Paiement effectué avec succès");
    navigate("/mes-billets");

  } catch (err) {
    alert("❌ Erreur de paiement");
    console.error(err);
  } finally {
    setLoading(false);
  }
};



  return (
    <div className="payment-page">
      <h2>DEMANDE DE PAIEMENT</h2>

      <div className="payment-form">
        <label>Nom du porteur de la carte</label>
        <input placeholder="Nom du porteur" />

        <label>Numéro de carte</label>
        <input placeholder="Numéro de carte" />

        <label>Date d’expiration</label>
        <div className="row">
          <select><option>01</option></select>
          <select><option>2025</option></select>
        </div>

        <label>Code de vérification</label>
        <input placeholder="CVV" />

        <div className="checkbox">
          <input
            type="checkbox"
            checked={accepted}
            onChange={() => setAccepted(!accepted)}
          />
          <span>J’accepte les conditions générales</span>
        </div>

        <button onClick={handlePay} disabled={loading}>
          {loading ? "Paiement en cours..." : "Valider le paiement"}
        </button>
      </div>
    </div>
  );
}
