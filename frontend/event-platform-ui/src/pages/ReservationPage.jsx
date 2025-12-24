import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/api";
import { getUser } from "../utils/auth";
import "./ReservationPage.css";

export default function ReservationPage() {
  const { eventId, categoryId } = useParams();
  const navigate = useNavigate();
  const user = getUser();

  const [event, setEvent] = useState(null);
  const [category, setCategory] = useState(null);

  const [form, setForm] = useState({
    firstName: user?.firstName || "",
    lastName: user?.lastName || "",
    email: user?.email || "",
    phone: user?.phone || "",
    address: user?.address || "",
    quantity: 1,
  });

  useEffect(() => {
    api.get(`/event-service/events/${eventId}`)
      .then(res => setEvent(res.data))
      .catch(err => console.error(err));

    api.get(`/event-service/events/categories/${categoryId}`)
      .then(res => setCategory(res.data))
      .catch(err => console.error(err));
  }, [eventId, categoryId]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleContinue = async () => {
    try {
      const res = await api.post("/reservation-service/reservations", {
        userId: user.id,
        eventId,
        categoryId,
        quantity: form.quantity,
      });

      navigate(`/payment/${res.data.id}`);
    } catch (err) {
      alert("Erreur lors de la réservation");
      console.error(err);
    }
  };

  if (!event || !category) return <p>Chargement...</p>;

  const total = category.price * form.quantity;
return (
  <div className="reservation-page">

    <h2>Informations de réservation</h2>

    <div className="reservation-form">

      <div className="field">
        <label>Nom</label>
        <input
          name="firstName"
          value={form.firstName}
          onChange={handleChange}
          placeholder="Votre nom"
        />
      </div>

      <div className="field">
        <label>Prénom</label>
        <input
          name="lastName"
          value={form.lastName}
          onChange={handleChange}
          placeholder="Votre prénom"
        />
      </div>

      <div className="field full">
        <label>Email</label>
        <input
          name="email"
          value={form.email}
          disabled
        />
      </div>

      <div className="field">
        <label>Téléphone</label>
        <input
          name="phone"
          value={form.phone}
          onChange={handleChange}
          placeholder="06xxxxxxxx"
        />
      </div>

      <div className="field">
        <label>Quantité</label>
        <input
          type="number"
          min="1"
          name="quantity"
          value={form.quantity}
          onChange={handleChange}
        />
      </div>

      <div className="field full">
        <label>Adresse</label>
        <input
          name="address"
          value={form.address}
          onChange={handleChange}
          placeholder="Adresse complète"
        />
      </div>

      <button className="submit-btn" onClick={handleContinue}>
        Continuer le paiement →
      </button>

    </div>
  </div>
);

}
