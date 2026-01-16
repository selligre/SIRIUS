import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [announces, setAnnounces] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  // Fonction pour charger les annonces
  const fetchAnnounces = (query = '') => {
    // En local docker-compose, on tape sur localhost:8080
    // En prod, ce sera l'URL de ton reverse proxy
    const url = query
        ? `http://localhost:8080/api/search?query=${query}`
        : 'http://localhost:8080/api/search';

    fetch(url)
        .then(response => response.json())
        .then(data => setAnnounces(data))
        .catch(err => console.error("Erreur connexion API:", err));
  };

  // Chargement initial
  useEffect(() => {
    fetchAnnounces();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchAnnounces(searchTerm);
  };

  return (
      <div className="App">
        <header className="App-header">
          <h1>Ville Partagée - Recherche</h1>

          <form onSubmit={handleSearch} style={{ marginBottom: '20px' }}>
            <input
                type="text"
                placeholder="Rechercher une annonce..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ padding: '10px', width: '300px' }}
            />
            <button type="submit" style={{ padding: '10px' }}>Chercher</button>
          </form>

          <div className="announces-list">
            {announces.length === 0 ? <p>Aucune annonce trouvée.</p> : null}

            {announces.map(announce => (
                <div key={announce.id} className="card">
                  <h3>{announce.title}</h3>
                  <p>{announce.description}</p>
                  <span>Prix: {announce.price} €</span>
                </div>
            ))}
          </div>
        </header>
      </div>
  );
}

export default App;