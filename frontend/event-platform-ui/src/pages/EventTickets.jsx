import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./EventTickets.css";
import { isAuthenticated } from "../utils/auth";

export default function EventTickets() {
  const { eventId } = useParams();
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get(`/event-service/events/${eventId}/categories`)
      .then(res => {
        setCategories(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, [eventId]);

  return (
    <div className="tickets-container">
      
      {/* 🔙 RETOUR */}
      <button className="btn-back" onClick={() => navigate(-1)}>
        ← Retour aux événements
      </button>

      <h2 className="tickets-title">Choisissez votre catégorie</h2>

      {loading && <p>Chargement...</p>}

      {!loading && categories.length === 0 && (
        <p>Aucune catégorie disponible</p>
      )}

      <div className="categories-list">
        {categories.map(cat => (
          <div key={cat.id} className="category-card">

            <div className="category-info">
              <h3>{cat.name}</h3>
              <p>Section : <strong>{cat.section}</strong></p>
              <p className="price">{cat.price} DH</p>
              <p className="remaining">
                {cat.remainingTickets} billets restants
              </p>
            </div>

          <button
  className="btn-select"
  onClick={() => {
    if (!isAuthenticated()) {
      alert("Vous devez être authentifié pour réserver");
      navigate("/login");
      return;
    }

navigate(`/reservation/${eventId}/${cat.id}`);
  }}
>
  Réservez maintenant
</button>

          </div>
        ))}
      </div>
    </div>
  );
}
