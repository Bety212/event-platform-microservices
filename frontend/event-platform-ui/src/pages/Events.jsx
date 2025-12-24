import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./Events.css";
import { useSearchParams } from "react-router-dom";

export default function Events() {
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
const [searchParams] = useSearchParams();
const query = searchParams.get("q")?.trim().toLowerCase() || "";

const filteredEvents = events.filter(event =>
  event.description.toLowerCase().includes(query) ||
  event.location.toLowerCase().includes(query) ||
  event.participants.join(" ").toLowerCase().includes(query)
);

  useEffect(() => {
    api.get("/event-service/events")
      .then(res => {
        setEvents(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError("Impossible de charger les événements");
        setLoading(false);
      });
  }, []);

  return (
    <div className="events-container">
      <div className="events-wrapper">
        <h2 className="title">Événements sportifs</h2>

        {/* 🔄 LOADING */}
{/* 📭 AUCUN RÉSULTAT */}
{!loading && filteredEvents.length === 0 && (
  <p>Aucun événement trouvé</p>
)}

{/* ✅ LISTE FILTRÉE */}
{filteredEvents.map(event => (
  <div key={event.id} className="event-card">

    <div className="date-box">
      <span className="day">
        {new Date(event.eventDate).getDate()}
      </span>
      <span className="month">
        {new Date(event.eventDate).toLocaleString("fr-FR", { month: "short" })}
      </span>
    </div>

    <div className="event-info">
      <h3>{event.description}</h3>

      <p>
        {event.location} –{" "}
        {new Date(event.eventDate).toLocaleTimeString("fr-FR", {
          hour: "2-digit",
          minute: "2-digit",
        })}
      </p>

      <p className="teams">
        {event.participants.join(" vs ")}
      </p>
    </div>

    <button
      className="btn-ticket"
      onClick={() => navigate(`/events/${event.id}/tickets`)}
    >
      Voir les billets <span className="arrow">→</span>
    </button>

  </div>
))}
      </div>
    </div>
  );
}
