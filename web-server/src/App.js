import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [annonces, setAnnonces] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');

  // Fonction pour charger les annonces
  const fetchAnnonces = (query = '') => {
    // En local docker-compose, on tape sur localhost:8080
    // En prod, ce sera l'URL de ton reverse proxy
    const url = query
        ? `http://localhost:8080/api/search?query=${query}`
        : 'http://localhost:8080/api/search';

    fetch(url)
        .then(response => response.json())
        .then(data => setAnnonces(data))
        .catch(err => console.error("Erreur connexion API:", err));
  };

  // Chargement initial
  useEffect(() => {
    fetchAnnonces();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    fetchAnnonces(searchTerm);
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

          <div className="annonces-list">
            {annonces.length === 0 ? <p>Aucune annonce trouvée.</p> : null}

            {annonces.map(annonce => (
                <div key={annonce.id} className="card">
                  <h3>{annonce.titre}</h3>
                  <p>{annonce.description}</p>
                  <span>Prix: {annonce.prix} €</span>
                </div>
            ))}
          </div>
        </header>
      </div>
  );
}

export default App;