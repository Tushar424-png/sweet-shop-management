import { useState } from "react";
import api from "../api/axios";

export default function AddSweet({ reload }) {
  const [sweet, setSweet] = useState({
    name: "",
    category: "",
    price: "",
    quantity: ""
  });

  const addSweet = async () => {
    await api.post("/api/sweets", sweet);
    reload();
  };

  return (
    <>
      <h3>Add Sweet (Admin)</h3>
      <input placeholder="Name" onChange={e => setSweet({...sweet, name: e.target.value})}/>
      <input placeholder="Category" onChange={e => setSweet({...sweet, category: e.target.value})}/>
      <input placeholder="Price" onChange={e => setSweet({...sweet, price: e.target.value})}/>
      <input placeholder="Quantity" onChange={e => setSweet({...sweet, quantity: e.target.value})}/>
      <button className="primary" onClick={addSweet}>Add</button>
    </>
  );
}
