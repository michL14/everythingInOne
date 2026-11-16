// Worker SQLite (sql.js) compatible avec SQLDelight WebWorkerDriver.
// La base est sauvegardée dans IndexedDB pour survivre aux rechargements de la page.
importScripts("sql-wasm.js");

const DB_NAME = "everythinginone";
const STORE = "sqlite";
const KEY = "db";
const SAVE_DELAY_MS = 300;

let db = null;
let transactionDepth = 0;
let saveTimer = null;

function openIdb() {
  return new Promise((resolve, reject) => {
    const request = indexedDB.open(DB_NAME, 1);
    request.onupgradeneeded = () => request.result.createObjectStore(STORE);
    request.onsuccess = () => resolve(request.result);
    request.onerror = () => reject(request.error);
  });
}

async function loadSaved() {
  const idb = await openIdb();
  return new Promise((resolve, reject) => {
    const request = idb.transaction(STORE, "readonly").objectStore(STORE).get(KEY);
    request.onsuccess = () => resolve(request.result || null);
    request.onerror = () => reject(request.error);
  });
}

async function save() {
  saveTimer = null;
  // export() réinitialise la connexion : on attend la fin d'une éventuelle transaction.
  if (transactionDepth > 0) {
    scheduleSave();
    return;
  }
  const data = db.export();
  const idb = await openIdb();
  idb.transaction(STORE, "readwrite").objectStore(STORE).put(data, KEY);
}

function scheduleSave() {
  if (saveTimer === null) {
    saveTimer = setTimeout(() => save().catch(console.error), SAVE_DELAY_MS);
  }
}

async function createDatabase() {
  const SQL = await initSqlJs({ locateFile: (file) => file });
  const saved = await loadSaved();
  db = saved ? new SQL.Database(saved) : new SQL.Database();
}

function isWrite(sql) {
  return !/^\s*(select|pragma)\b/i.test(sql);
}

function handle(data) {
  switch (data && data.action) {
    case "exec":
      if (!data.sql) throw new Error("exec: Missing query string");
      const results = db.exec(data.sql, data.params)[0] ?? { values: [] };
      if (isWrite(data.sql)) scheduleSave();
      return results;
    case "begin_transaction":
      transactionDepth++;
      return db.exec("BEGIN TRANSACTION;");
    case "end_transaction":
      transactionDepth = Math.max(0, transactionDepth - 1);
      scheduleSave();
      return db.exec("END TRANSACTION;");
    case "rollback_transaction":
      transactionDepth = Math.max(0, transactionDepth - 1);
      return db.exec("ROLLBACK TRANSACTION;");
    default:
      throw new Error(`Unsupported action: ${data && data.action}`);
  }
}

const ready = createDatabase();

self.onmessage = (event) => {
  const { data } = event;
  ready
    .then(() => postMessage({ id: data.id, results: handle(data) }))
    .catch((error) => postMessage({ id: data.id, error: String(error) }));
};
