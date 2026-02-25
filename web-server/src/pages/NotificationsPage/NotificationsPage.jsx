import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './NotificationsPage.css';
import config from '../../api/config';
import NotificationItem from '../../components/NotificationItem/NotificationItem';

function NotificationsPage({ currentUser }) {
  const navigate = useNavigate();
  const [notifications, setNotifications] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [filterUnread, setFilterUnread] = useState(false);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    setIsLoading(true);
    try {
      const response = await fetch(
        `${config.announceManagerServiceUrl}/api/notifications?userId=${currentUser}`
      );
      if (response.ok) {
        const data = await response.json();
        setNotifications(data || []);
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
        `${config.announceManagerServiceUrl}/api/notifications/${notificationId}/read`,
        { method: 'PUT' }
      );

      if (response.ok) {
        setNotifications((prev) =>
          prev.map((notif) =>
            notif.id === notificationId ? { ...notif, isRead: true } : notif
          )
        );
      }
    } catch (error) {
      console.error('Erreur lors du marquage de la notification:', error);
    }
  };

  const handleViewAnnouncement = (announcementId) => {
    navigate(`/announcement/${announcementId}`);
  };

  const unreadCount = notifications.filter((n) => !n.isRead).length;
  const displayedNotifications = filterUnread
    ? notifications.filter((n) => !n.isRead)
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
          <label className="filter-checkbox">
            <input
              type="checkbox"
              checked={filterUnread}
              onChange={(e) => setFilterUnread(e.target.checked)}
            />
            Afficher les non-lues uniquement
          </label>
          <button
            className="refresh-btn"
            onClick={fetchNotifications}
            disabled={isLoading}
          >
            🔄 Actualiser
          </button>
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
              {displayedNotifications.map((notification) => (
                <NotificationItem
                  key={notification.id}
                  notification={notification}
                  onMarkAsRead={handleMarkAsRead}
                  onViewAnnouncement={handleViewAnnouncement}
                />
              ))}
            </div>
          )}
        </section>
      </div>
    </div>
  );
}

export default NotificationsPage;
