import { useEffect, useState } from "react";
import api from "../api/api";
import { getEventById } from "../api/eventApi";
import { getUser } from "../utils/auth";
import "./MyTickets.css";

export default function MyTickets() {
  const user = getUser();
  const [tickets, setTickets] = useState([]);
  const [eventsMap, setEventsMap] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user) return;

    api
      .get(`/reservation-service/reservations/user/${user.id}/paid`)
      .then(async (res) => {
        setTickets(res.data);

        // 🔥 Charger les événements liés
        const events = {};
        for (const ticket of res.data) {
          if (!events[ticket.eventId]) {
            const eventRes = await getEventById(ticket.eventId);
            events[ticket.eventId] = eventRes.data;
          }
        }
        setEventsMap(events);
      })
      .catch((err) => {
        console.error("Erreur récupération billets", err);
      })
      .finally(() => setLoading(false));
  }, [user]);

  if (loading) return <p className="loading">Chargement...</p>;

  return (
    <div className="my-tickets-page">
      <h2>Mes billets</h2>

      {tickets.length === 0 ? (
        <p className="empty">Aucun billet pour le moment</p>
      ) : (
        <div className="tickets-grid">
          {tickets.map((ticket) => {
            const event = eventsMap[ticket.eventId];

            return (
              <div className="ticket-card" key={ticket.id}>
                <h3>🎟️ Billet #{ticket.id}</h3>

                {event ? (
                  <>
                   <p className="event-title">{event.title}</p>
<p>📍 {event.location}</p>

<p>
  📅{" "}
  {event.eventDate
    ? new Date(event.eventDate).toLocaleString()
    : "Date non disponible"}
</p>

                  </>
                ) : (
                  <p>Chargement événement...</p>
                )}

                <p><strong>Quantité :</strong> {ticket.quantity}</p>
                <p><strong>Total :</strong> {ticket.totalPrice} MAD</p>

                <p className="status paid">PAYÉ</p>

                <p className="date">
                  {new Date(ticket.createdAt).toLocaleString()}
                </p>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
