import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Layout from "./Layout";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Events from "./pages/Events";
import EventTickets from "./pages/EventTickets";
import ReservationPage from "./pages/ReservationPage";
import PaymentPage from "./pages/PaymentPage";
import MyTickets from "./pages/MyTickets";
import RequireAuth from "./components/RequireAuth";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* LAYOUT */}
        <Route element={<Layout />}>

          {/* ROUTE PAR DÉFAUT */}
          <Route path="/" element={<Navigate to="/events" />} />

          {/* PUBLIC */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/events" element={<Events />} />
          <Route path="/events/:eventId/tickets" element={<EventTickets />} />

          {/* 🔐 PROTÉGÉ */}
          <Route element={<RequireAuth />}>
            <Route path="/reservation/:eventId/:categoryId" element={<ReservationPage />} />
            <Route path="/payment/:reservationId" element={<PaymentPage />} />
            <Route path="/my-tickets" element={<MyTickets />} />
          </Route>

        </Route>
      </Routes>
    </BrowserRouter>
  );
}
