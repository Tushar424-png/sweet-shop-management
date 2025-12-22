import { Link, useNavigate } from "react-router-dom";
import { getUserRole } from "../utils/auth";

export default function Navbar() {
  const role = getUserRole();
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <nav className="bg-purple-600 text-white p-4 flex justify-between items-center">
      <Link to="/sweets" className="font-bold text-xl">Sweet Shop</Link>
      <div className="space-x-4">
        {!role && (
          <>
            <Link to="/" className="hover:underline">Login</Link>
            <Link to="/register" className="hover:underline">Register</Link>
          </>
        )}
        {role && (
          <>
            <span>{role === "ROLE_ADMIN" ? "Admin" : "User"}</span>
            <button onClick={logout} className="bg-red-500 px-3 py-1 rounded hover:bg-red-600">Logout</button>
          </>
        )}
      </div>
    </nav>
  );
}
