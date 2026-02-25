import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import config from './api/config';
import Navbar from './components/Navbar/Navbar';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import NotificationsPage from './pages/NotificationsPage/NotificationsPage';

function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [unreadNotificationCount, setUnreadNotificationCount] = useState(0);

  // Charger l'utilisateur depuis le localStorage au démarrage
  useEffect(() => {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
      setCurrentUser(savedUser);
    }
  }, []);

  // Récupérer le nombre de notifications non-lues
  useEffect(() => {
    if (currentUser) {
      fetchUnreadNotificationCount();
    }
  }, [currentUser]);

  const fetchUnreadNotificationCount = async () => {
    try {
      const response = await fetch(
        `${config.announceManagerServiceUrl}/api/notifications/unread-count?userId=${currentUser}`
      );
      if (response.ok) {
        const data = await response.json();
        setUnreadNotificationCount(data.count || 0);
      }
    } catch (error) {
      console.error('Erreur lors du chargement du nombre de notifications:', error);
    }
  };

  const handleLogin = (username) => {
    setCurrentUser(username);
    localStorage.setItem('currentUser', username);
  };

  const handleLogout = () => {
    setCurrentUser(null);
    localStorage.removeItem('currentUser');
  };

  return (
    <Router>
      {currentUser && (
        <Navbar
          currentUser={currentUser}
          unreadNotificationCount={unreadNotificationCount}
        />
      )}

      <Routes>
        <Route
          path="/"
          element={currentUser ? <HomePage currentUser={currentUser} /> : <Navigate to="/login" />}
        />
        <Route
          path="/login"
          element={<LoginPage onLogin={handleLogin} />}
        />
        <Route
          path="/notifications"
          element={
            currentUser ? (
              <NotificationsPage currentUser={currentUser} />
            ) : (
              <Navigate to="/login" />
            )
          }
        />
        <Route path="*" element={<Navigate to="/" />} />
      </Routes>
    </Router>
  );
}

export default App;