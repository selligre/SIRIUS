import React from 'react';
import './NotificationItem.css';

function NotificationItem({
  notification,
  onMarkAsRead,
  onViewAnnouncement,
}) {
  return (
    <div className={`notification-item ${notification.isRead ? 'read' : 'unread'}`}>
      {!notification.isRead && <div className="unread-indicator"></div>}

      <div className="notification-content">
        <h4 className="notification-title">{notification.title}</h4>
        <p className="notification-description">{notification.description}</p>
        <span className="notification-date">
          {new Date(notification.timestamp).toLocaleString('fr-FR')}
        </span>
      </div>

      <div className="notification-actions">
        {!notification.isRead && (
          <button
            className="notification-btn mark-read-btn"
            onClick={() => onMarkAsRead(notification.id)}
            title="Marquer comme lue"
          >
            ✓
          </button>
        )}
        <button
          className="notification-btn view-announcement-btn"
          onClick={() => onViewAnnouncement(notification.announcementId)}
          title="Voir l'annonce"
        >
          →
        </button>
      </div>
    </div>
  );
}

export default NotificationItem;
