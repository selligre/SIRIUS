import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './NotificationsPage.css';
import config from '../../api/config';
import NotificationItem from '../../components/NotificationItem/NotificationItem';

function NotificationsPage({ currentUser, onUpdateNotificationCount }) {
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);
  const [notifications, setNotifications] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [filterUnread, setFilterUnread] = useState(false);
  const [selectedNotifications, setSelectedNotifications] = useState(new Set());

  useEffect(() => {
    const savedUserId = localStorage.getItem('userId');
    if (savedUserId) {
      setUserId(parseInt(savedUserId, 10));
    }
  }, []);

  // Appeler la fonction pour mettre à jour le compteur de notifications uniquement au chargement de la page
  useEffect(() => {
    if (onUpdateNotificationCount) {
      onUpdateNotificationCount();
    }
  }, []);

  useEffect(() => {
    if (userId) {
      fetchNotifications();
    }
  }, [userId]);

  const fetchNotifications = async () => {
    setIsLoading(true);
    try {
      const response = await fetch(
        `${config.notificationsServiceUrl}/api/notifications?userId=${userId}`
      );
      if (response.ok) {
        const data = await response.json();
        setNotifications(data || []);
        setSelectedNotifications(new Set());
      }
    } catch (error) {
      console.error('Erreur lors du chargement des notifications:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const handleMarkAsRead = async (notificationId) => {
    try {
      const response = await fetch(
        `${config.notificationsServiceUrl}/api/notifications/${notificationId}/read`,
        { method: 'PUT' }
      );

      if (response.ok) {
        setNotifications((prev) =>
          prev.map((notif) =>
            notif.id === notificationId ? { ...notif, hasBeenRed: true } : notif
          )
        );
      }
    } catch (error) {
      console.error('Erreur lors du marquage de la notification:', error);
    }
  };

  const handleSelectNotification = (notificationId) => {
    setSelectedNotifications((prev) => {
      const newSet = new Set(prev);
      if (newSet.has(notificationId)) {
        newSet.delete(notificationId);
      } else {
        newSet.add(notificationId);
      }
      return newSet;
    });
  };

  const handleMarkAllSelectedAsRead = async () => {
    if (selectedNotifications.size === 0) {
      alert('Sélectionnez au moins une notification');
      return;
    }

    try {
      const notificationIds = Array.from(selectedNotifications);
      const response = await fetch(
        `${config.notificationsServiceUrl}/api/notifications/read`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(notificationIds),
        }
      );

      if (response.ok) {
        setNotifications((prev) =>
          prev.map((notif) =>
            selectedNotifications.has(notif.id) ? { ...notif, hasBeenRed: true } : notif
          )
        );
        setSelectedNotifications(new Set());
      }
    } catch (error) {
      console.error('Erreur lors du marquage des notifications:', error);
    }
  };

  const handleSelectAll = (e) => {
    if (e.target.checked) {
      const allUnreadIds = displayedNotifications
        .filter((n) => !n.hasBeenRed)
        .map((n) => n.id);
      setSelectedNotifications(new Set(allUnreadIds));
    } else {
      setSelectedNotifications(new Set());
    }
  };

  const unreadCount = notifications.filter((n) => !n.hasBeenRed).length;
  const displayedNotifications = filterUnread
    ? notifications.filter((n) => !n.hasBeenRed)
    : notifications;

  return (
    <div className="notifications-page">
      <div className="notifications-container">
        <section className="notifications-header">
          <h1>Mes notifications</h1>
          <div className="notifications-stats">
            <span className="stat-item">
              Total: <strong>{notifications.length}</strong>
            </span>
            <span className="stat-item">
              Non-lues: <strong>{unreadCount}</strong>
            </span>
          </div>
        </section>

        <div className="notifications-controls">
          <div className="left-controls">
            <label className="filter-checkbox">
              <input
                type="checkbox"
                checked={filterUnread}
                onChange={(e) => setFilterUnread(e.target.checked)}
              />
              Afficher les non-lues uniquement
            </label>
          </div>
          
          <div className="right-controls">
            {selectedNotifications.size > 0 && (
              <button
                className="mark-selected-btn"
                onClick={handleMarkAllSelectedAsRead}
              >
                ✓ Marquer {selectedNotifications.size} comme lue(s)
              </button>
            )}
            
            <button
              className="refresh-btn"
              onClick={fetchNotifications}
              disabled={isLoading}
            >
              🔄 Actualiser
            </button>
          </div>
        </div>

        <section className="notifications-list">
          {isLoading ? (
            <div className="loading">Chargement des notifications...</div>
          ) : displayedNotifications.length === 0 ? (
            <div className="no-notifications">
              {filterUnread ? (
                <p>Aucune notification non-lue.</p>
              ) : (
                <p>Aucune notification pour le moment.</p>
              )}
            </div>
          ) : (
            <div>
              {displayedNotifications.length > 1 && (
                <div className="select-all-container">
                  <label className="select-all-checkbox">
                    <input
                      type="checkbox"
                      checked={
                        displayedNotifications.length > 0 &&
                        displayedNotifications
                          .filter((n) => !n.hasBeenRed)
                          .every((n) => selectedNotifications.has(n.id))
                      }
                      onChange={handleSelectAll}
                    />
                    Sélectionner tous les non-lues
                  </label>
                </div>
              )}
              
              {displayedNotifications.map((notification) => (
                <div
                  key={notification.id}
                  className="notification-wrapper"
                  onClick={() => !notification.hasBeenRed && handleSelectNotification(notification.id)}
                >
                  {!notification.hasBeenRed && (
                    <input
                      type="checkbox"
                      className="notification-checkbox"
                      checked={selectedNotifications.has(notification.id)}
                      onChange={(e) => {
                        e.stopPropagation();
                        handleSelectNotification(notification.id);
                      }}
                    />
                  )}
                  <NotificationItem
                    notification={{
                      ...notification,
                      isRead: notification.hasBeenRed,
                      timestamp: notification.creationDate,
                      announcementId: notification.announceId,
                    }}
                    onMarkAsRead={handleMarkAsRead}
                    onViewAnnouncement={(announcementId) =>
                      navigate(`/announcement/${announcementId}`)
                    }
                  />
                </div>
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

export default NotificationsPage;
