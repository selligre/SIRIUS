import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './NotificationsPage.css';
import config from '../../api/config';
import NotificationItem from '../../components/NotificationItem/NotificationItem';

function NotificationsPage({ currentUser, currentUserId: propUserId, onUpdateNotificationCount }) {
  const navigate = useNavigate();
  const [userId, setUserId] = useState(propUserId || null);
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

  // If parent provides currentUserId prop (set after login), use it and store locally
  useEffect(() => {
    if (propUserId) {
      setUserId(propUserId);
      localStorage.setItem('userId', propUserId);
    }
  }, [propUserId]);

  // Appeler la fonction pour mettre à jour le compteur de notifications uniquement au chargement de la page
  useEffect(() => {
    if (onUpdateNotificationCount) {
      onUpdateNotificationCount();
    }
  }, []);

  useEffect(() => {
    // Use propUserId as priority; avoid calling API when no valid userId
    const effectiveUserId = userId || propUserId;
    if (effectiveUserId) {
      fetchNotifications(effectiveUserId);
    }
  }, [userId]);

  // Listen for live websocket notifications and prepend them to the list
  useEffect(() => {
    const handler = (ev) => {
      try {
        const event = ev.detail;
        const newNotif = {
          id: event.id,
          title: event.message,
          creationDate: event.createdAt,
          announceId: event.announceId,
          hasBeenRed: false,
        };
        setNotifications((prev) => {
          if (!prev || prev.some((n) => n.id === newNotif.id)) return prev || [];
          return [newNotif, ...(prev || [])];
        });
        if (onUpdateNotificationCount) onUpdateNotificationCount();
      } catch (e) {
        console.error('Erreur lors du traitement d\'une notification websocket:', e);
      }
    };

    window.addEventListener('ws-notification', handler);
    return () => window.removeEventListener('ws-notification', handler);
  }, [onUpdateNotificationCount]);

  const fetchNotifications = async (overrideUserId) => {
    setIsLoading(true);
    try {
      const uid = overrideUserId || userId || propUserId;
      if (!uid) return;
      const response = await fetch(
        `${config.notificationManagerServiceUrl}/api/notifications?userId=${encodeURIComponent(uid)}`
      );
      if (response.ok) {
        const data = await response.json();
        // normalize server payload to include `isRead` consistently
        const normalized = (data || []).map((n) => ({
          ...n,
          isRead: n.isRead === true || n.hasBeenRed === true,
          hasBeenRed: n.hasBeenRed === true || n.isRead === true,
        }));
        setNotifications(normalized);
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
        `${config.notificationManagerServiceUrl}/api/notifications/${notificationId}/read`,
        { method: 'PUT' }
      );

      if (response.ok) {
        setNotifications((prev) =>
          prev.map((notif) =>
            notif.id === notificationId ? { ...notif, hasBeenRed: true, isRead: true } : notif
          )
        );
        if (onUpdateNotificationCount) onUpdateNotificationCount();
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
        `${config.notificationManagerServiceUrl}/api/notifications/read`,
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
            selectedNotifications.has(notif.id) ? { ...notif, hasBeenRed: true, isRead: true } : notif
          )
        );
        setSelectedNotifications(new Set());
        if (onUpdateNotificationCount) onUpdateNotificationCount();
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

  // Normalize read flag: prefer `isRead` from backend, fallback to `hasBeenRed`
  const normalizedNotifications = notifications.map((n) => ({
    ...n,
    _isRead: n.isRead === true || n.hasBeenRed === true,
  }));

  const unreadCount = normalizedNotifications.filter((n) => !n._isRead).length;
  const displayedNotifications = filterUnread
    ? normalizedNotifications.filter((n) => !n._isRead)
    : normalizedNotifications;

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
                  onClick={() => handleSelectNotification(notification.id)}
                >
                  <input
                    type="checkbox"
                    className="notification-checkbox"
                    checked={selectedNotifications.has(notification.id)}
                    onChange={(e) => {
                      e.stopPropagation();
                      handleSelectNotification(notification.id);
                    }}
                  />
                        <NotificationItem
                          notification={{
                            ...notification,
                            // pass standardized props expected by NotificationItem
                            isRead: notification._isRead || notification.isRead,
                            timestamp: notification.creationDate || notification.timestamp,
                            announcementId: notification.announceId || notification.announcementId,
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
