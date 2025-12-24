import { Outlet, useLocation } from "react-router-dom";
import Navbar from "./components/Navbar";

export default function Layout() {
  const location = useLocation();
  const hideNavbar =
    location.pathname === "/login" ||
    location.pathname === "/register";

  return (
    <>
      {!hideNavbar && <Navbar />}
      <Outlet />
    </>
  );
}
