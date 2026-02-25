import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './LoginPage.css';
import config from '../../api/config';

function LoginPage({ onLogin }) {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!username.trim()) {
      setError('Veuillez entrer un nom d\'utilisateur');
      return;
    }

    setIsLoading(true);

    try {
      // Vérifier que l'utilisateur existe via le service announce-manager
      const response = await fetch(
        `${config.announceManagerServiceUrl}/api/users/verify/${username}`,
        {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      if (!response.ok) {
        throw new Error('Utilisateur non trouvé. Veuillez vérifier votre nom d\'utilisateur.');
      }

      const userId = await response.json();

      // Authentification réussie
      localStorage.setItem('currentUser', username);
      localStorage.setItem('userId', userId);
      onLogin(username);
      navigate("/");
    } catch (err) {
      setError(err.message || 'Erreur lors de la connexion. Veuillez réessayer.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-card">
          <h1 className="login-title">Ville Partagée</h1>
          <h2>Connexion</h2>

          <form onSubmit={handleSubmit} className="login-form">
            <div className="form-group">
              <label htmlFor="username">Nom d'utilisateur</label>
              <input
                type="text"
                id="username"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Entrez votre nom d'utilisateur"
                disabled={isLoading}
                autoFocus
              />
            </div>

            {error && <div className="login-error">{error}</div>}

            <button type="submit" className="login-btn" disabled={isLoading}>
              {isLoading ? 'Connexion en cours...' : 'Se connecter'}
            </button>
          </form>

          <div className="login-info">
            <p>Connectez-vous pour accéder à toutes les fonctionnalités</p>
          </div>

          <Link className="loggout-btn" to="http://172.31.249.140:21180/oauth2/sign_out?rd=http://172.31.249.140:21280/realms/fimafeng/protocol/openid-connect/logout">
            Se déconnecter complètement
          </Link>
        </div>
      </div>
    </div>
  );
}

export default LoginPage;
