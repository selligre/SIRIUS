import React from 'react';
import './AnnouncementCard.css';

function AnnouncementCard({ announcement }) {
  const {
    id,
    title,
    description,
    startTime,
    endTime,
    duration,
    ownerUsername
  } = announcement;

  const formatTime = (dateString) => {
    return new Date(dateString).toLocaleString('fr-FR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="announcement-card">
      <div className="announcement-header">
        <h2 className="announcement-title">{title}</h2>
        <span className="announcement-owner">par {ownerUsername}</span>
      </div>

      <p className="announcement-description">{description}</p>

      <div className="announcement-details">
        <div className="announcement-timing">
          <div className="timing-item">
            <span className="timing-label">Début :</span>
            <span className="timing-value">{formatTime(startTime)}</span>
          </div>
          <div className="timing-item">
            <span className="timing-label">Fin :</span>
            <span className="timing-value">{formatTime(endTime)}</span>
          </div>
          <div className="timing-item">
            <span className="timing-label">Durée :</span>
            <span className="timing-value">{duration}</span>
          </div>
        </div>
      </div>

      <div className="announcement-actions">
        <button className="announcement-btn-details">Voir les détails</button>
        <button className="announcement-btn-contact">Contacter</button>
      </div>
    </div>
  );
}

export default AnnouncementCard;
