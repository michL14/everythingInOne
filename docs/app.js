const SUBSCRIPTIONS = [
  { name: "Loyer", amount: 445 },
  { name: "Basic Fit", amount: 34.99 },
  { name: "AXA", amount: 7.97 },
  { name: "Cloud apple", amount: 9.99 },
  { name: "Téléphone", amount: 25.99 },
  { name: "Netflix", amount: 14.99 },
  { name: "Ilévia", amount: 27.8 },
  { name: "Amazon Prime", amount: 6.99 },
  { name: "Revolut", amount: 200 },
  { name: "Compte commun", amount: 100 },
  { name: "De côté", amount: 200 },
];

const STORAGE_KEY = "paid-subscriptions";
const fmt = new Intl.NumberFormat("fr-FR", { style: "currency", currency: "EUR" });

function load() {
  try {
    const data = JSON.parse(localStorage.getItem(STORAGE_KEY));
    return new Set(Array.isArray(data) ? data : []);
  } catch {
    return new Set();
  }
}

const paid = load();

function save() {
  try {
    localStorage.setItem(STORAGE_KEY, JSON.stringify([...paid]));
  } catch {}
}

function render() {
  const list = document.getElementById("list");
  list.innerHTML = "";
  let total = 0;
  let remaining = 0;

  for (const { name, amount } of SUBSCRIPTIONS) {
    const isPaid = paid.has(name);
    total += amount;
    if (!isPaid) remaining += amount;

    const li = document.createElement("li");
    if (isPaid) li.className = "paid";

    const n = document.createElement("span");
    n.className = "name";
    n.textContent = name;

    const a = document.createElement("span");
    a.className = "amount";
    a.textContent = fmt.format(amount);

    const b = document.createElement("button");
    b.type = "button";
    b.textContent = isPaid ? "Payé✅" : "A payer";
    b.onclick = () => {
      if (paid.has(name)) paid.delete(name);
      else paid.add(name);
      save();
      render();
    };

    li.append(n, a, b);
    list.append(li);
  }

  document.getElementById("total").textContent = "Total à payé : " + fmt.format(total);
  document.getElementById("remaining").textContent = "Restant à payé : " + fmt.format(remaining);
}

document.getElementById("reset").onclick = () => {
  paid.clear();
  save();
  render();
};

render();
