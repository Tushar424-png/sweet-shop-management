import { useEffect, useState } from "react";
import api from "../api/axios";
import { getUserRole } from "../utils/auth";
import AddSweet from "./AddSweet";

export default function Sweets() {
  const [sweets, setSweets] = useState([]);
  const role = getUserRole();

  useEffect(() => {
    loadSweets();
  }, []);

  const loadSweets = async () => {
    try {
      const res = await api.get("/api/sweets");
      setSweets(res.data);
    } catch (err) {
      console.error("Failed to load sweets:", err);
    }
  };

 const purchase = async (id, price, name) => {
  const options = {
    key: "rzp_test_Ru7lBN12wF2OzR", // only key_id
    amount: price * 100, // paise
    currency: "INR",
    name: "Sweet Shop",
    description: name,

    handler: async function () {
      // payment successful → now update backend quantity
      await api.post(`/api/sweets/${id}/purchase`);
      alert("Payment successful!");
      loadSweets();
    },

    modal: {
      ondismiss: function () {
        console.log("Payment cancelled");
      }
    },

    theme: {
      color: "#3399cc"
    }
  };

  const rzp = new window.Razorpay(options);
  rzp.open();
};


  const deleteSweet = async (id) => {
    try {
      await api.delete(`/api/sweets/${id}`);
      loadSweets();
    } catch (err) {
      console.error("Delete failed:", err);
    }
  };

  const restock = async (id) => {
  const amount = prompt("Enter restock amount");

  if (!amount) return;

  const qty = parseInt(amount, 10);

  if (isNaN(qty) || qty <= 0) {
    alert("Please enter a valid number");
    return;
  }

  try {
    await api.put(`/api/sweets/${id}/restock`, null, {
      params: { amount: qty }
    });
    loadSweets();
  } catch (err) {
    console.error("Restock failed:", err);
  }
};


  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h2 className="text-3xl font-bold text-center mb-6">Sweet Shop</h2>

      {/* Admin can add sweets */}
      {role === "ROLE_ADMIN" && <AddSweet reload={loadSweets} />}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
        {sweets.map((s) => (
          <div
            key={s.id}
            className="bg-white p-4 rounded-xl shadow hover:shadow-lg transition"
          >
            <h3 className="font-bold text-xl">{s.name}</h3>
            <p className="text-gray-600">{s.category}</p>
            <p className="mt-1">
              ₹{s.price} | Qty: {s.quantity}
            </p>

            <div className="mt-4 flex gap-2">
              <button
                className={`flex-1 py-2 rounded-lg text-white ${
                  s.quantity === 0
                    ? "bg-gray-400 cursor-not-allowed"
                    : "bg-blue-500 hover:bg-blue-600"
                }`}
                disabled={s.quantity === 0}
                onClick={() => purchase(s.id, s.price, s.name)}
              >
                Purchase
              </button>

              {role === "ROLE_ADMIN" && (
                <>
                  <button
                    className="flex-1 py-2 bg-red-500 rounded-lg text-white hover:bg-red-600"
                    onClick={() => deleteSweet(s.id)}
                  >
                    Delete
                  </button>
                  <button
                    className="flex-1 py-2 bg-green-500 rounded-lg text-white hover:bg-green-600"
                    onClick={() => restock(s.id)}
                  >
                    Restock
                  </button>
                </>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
