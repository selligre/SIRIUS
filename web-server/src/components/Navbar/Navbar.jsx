import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar({ currentUser, unreadNotificationCount }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('currentUser');
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        {/* Gauche - Logo et Menu */}
        <div className="navbar-left">
          <Link to="/" className="navbar-profile">
            Ville Partagée
          </Link>
          <ul className="navbar-menu">
            <li>
              <Link className='navbar-link' to="/">Accueil</Link>
            </li>
            <li>
              <Link className='navbar-link' to="/">Services</Link>
            </li>
            <li>
              <Link className='navbar-link' to="/">Prêts</Link>
            </li>
            <li>
              <Link className='navbar-link' to="/">Événements</Link>
            </li>
          </ul>
        </div>

        {/* Droite - Notifications et Profil */}
        <div className="navbar-right">
          {currentUser ? (
            <>
              <Link to="/my-announcements" className="navbar-my-announcements">
                📋 Mes annonces
              </Link>
              <Link to="/notifications" className="navbar-bell">
                🔔
                {unreadNotificationCount > 0 && (
                  <span className="notification-badge">{unreadNotificationCount}</span>
                )}
              </Link>
              <div className="navbar-profile">
                <span className="profile-avatar">👤</span>
                <div className="profile-dropdown">
                  <p>{currentUser}</p>
                  <button onClick={handleLogout}>Déconnexion</button>
                </div>
              </div>
            </>
          ) : (
            <Link to="/login" className="navbar-login">
              Connexion
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
