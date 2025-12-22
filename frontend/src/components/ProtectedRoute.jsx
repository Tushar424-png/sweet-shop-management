import { Navigate } from "react-router-dom";
import { getUserRole } from "../utils/auth";

export default function ProtectedRoute({ children, adminOnly = false }) {
  const role = getUserRole();

  if (!role) return <Navigate to="/" />; // Not logged in
  if (adminOnly && role !== "ROLE_ADMIN") return <Navigate to="/sweets" />; // Only admin access

  return children;
}
