import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import config from './api/config';
import Navbar from './components/Navbar/Navbar';
import HomePage from './pages/HomePage/HomePage';
import LoginPage from './pages/LoginPage/LoginPage';
import NotificationsPage from './pages/NotificationsPage/NotificationsPage';
import MyAnnouncementsPage from './pages/MyAnnouncementsPage/MyAnnouncementsPage';

function App() {
  const [currentUser, setCurrentUser] = useState(null);
  const [currentUserId, setCurrentUserId] = useState(null);
  const [unreadNotificationCount, setUnreadNotificationCount] = useState(0);

  // Charger l'utilisateur depuis le localStorage au démarrage
  useEffect(() => {
    const savedUser = localStorage.getItem('currentUser');
    const savedUserId = localStorage.getItem('userId');
    if (savedUser) {
      setCurrentUser(savedUser);
    }
    if (savedUserId) {
      setCurrentUserId(parseInt(savedUserId, 10));
    }
  }, []);

  // Récupérer le nombre de notifications non-lues une seule fois au login
  useEffect(() => {
    if (currentUserId) {
      fetchUnreadNotificationCount();
    }
  }, [currentUserId]);

  // WebSocket for live notifications
  useEffect(() => {
    if (!currentUserId || !config.notificationSenderUrl) return;

    let ws;
    try {
      ws = new WebSocket(config.notificationSenderUrl);
    } catch (err) {
      console.error('Erreur lors de la création du websocket:', err);
      return;
    }

    ws.onopen = () => {
      try {
        ws.send(JSON.stringify({ type: 'register', userId: currentUserId }));
      } catch (e) {
        console.error('WS send register failed', e);
      }
    };

    ws.onmessage = (ev) => {
      try {
        const data = JSON.parse(ev.data);
        // dispatch a window event so pages can react
        window.dispatchEvent(new CustomEvent('ws-notification', { detail: data }));
        setUnreadNotificationCount((prev) => (prev || 0) + 1);
      } catch (e) {
        console.error('WS message parse error', e);
      }
    };

    ws.onerror = (e) => console.error('WS error', e);
    ws.onclose = () => console.log('WS closed');

    return () => {
      try { ws.close(); } catch (e) {}
    };
  }, [currentUserId]);

  const fetchUnreadNotificationCount = async () => {
    try {
      const response = await fetch(
        `${config.notificationManagerServiceUrl}/api/notifications/unread-count?userId=${currentUserId}`
      );
      if (response.ok) {
        const data = await response.json();
        setUnreadNotificationCount(data.unreadCount || 0);
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
          element={currentUser ? <HomePage currentUser={currentUser} onUpdateNotificationCount={fetchUnreadNotificationCount} /> : <Navigate to="/login" />}
        />
        <Route
          path="/login"
          element={<LoginPage onLogin={handleLogin} />}
        />
        <Route
          path="/notifications"
          element={
            currentUser ? (
              <NotificationsPage currentUser={currentUser} onUpdateNotificationCount={fetchUnreadNotificationCount} />
            ) : (
              <Navigate to="/login" />
            )
          }
        />
        <Route
          path="/my-announcements"
          element={
            currentUser ? (
              <MyAnnouncementsPage currentUser={currentUser} />
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