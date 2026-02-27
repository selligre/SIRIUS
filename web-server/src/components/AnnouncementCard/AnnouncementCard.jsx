import React from 'react';
import './AnnouncementCard.css';

function AnnouncementCard({ announcement }) {
  const {
    id,
    title,
    description,
    dateTimeStart,
    dateTimeEnd,
    duration,
    ownerUsername,
    typeLabel,
    statusLabel
  } = announcement;

  const getTypeColor = (type) => {
    switch(type?.toUpperCase()) {
      case 'SERVICE':
        return 'type-service';
      case 'LOAN':
        return 'type-loan';
      case 'EVENT':
        return 'type-event';
      default:
        return 'type-unknown';
    }
  };

  const getTypeDisplay = (type) => {
    switch(type?.toUpperCase()) {
      case 'SERVICE':
        return '🔧 Service';
      case 'LOAN':
        return '📦 Prêt';
      case 'EVENT':
        return '🎉 Événement';
      default:
        return type || 'Autre';
    }
  };

  const formatTime = (dateString) => {
    if (!dateString) return 'Invalid date';
    try {
        const [datePart, timePart] = dateString.split('T');
        const [year, month, day] = datePart.split('-').map(Number);
        const [hours, minutes] = timePart.split(':').map(Number);

        const date = new Date(year, month - 1, day, hours, minutes);

        return new Intl.DateTimeFormat('fr-FR', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
        }).format(date);
    } catch (e) {
        console.error('Invalid date format:', dateString);
        return 'Invalid date';
    }
  };

  const formatDuration = (duration) => {
    if (isNaN(duration) || duration <= 0) return "0h0min";

    const hours = Math.floor(duration);
    const minutes = Math.round((duration - hours) * 60);
    if (minutes < 1) {
        return `${hours}h`;
    } else {
        return `${hours}h${minutes}min`;
    }
  };

  return (
    <div className="announcement-card">
      <div className="announcement-header">
        <div className="announcement-header-top">
          <h2 className="announcement-title">{title}</h2>
          {typeLabel && (
            <span className={`announcement-type-badge ${getTypeColor(typeLabel)}`}>
              {getTypeDisplay(typeLabel)}
            </span>
          )}
        </div>
        <span className="announcement-owner">par {ownerUsername}</span>
      </div>

      <p className="announcement-description">{description}</p>

      <div className="announcement-details">
        <div className="announcement-timing">
          <div className="timing-item">
            <span className="timing-label">Début : </span>
            <span className="timing-value">{formatTime(dateTimeStart)}</span>
          </div>
          <div className="timing-item">
            <span className="timing-label">Fin : </span>
            <span className="timing-value">{formatTime(dateTimeEnd)}</span>
          </div>
          <div className="timing-item">
            <span className="timing-label">Durée : </span>
            <span className="timing-value">{formatDuration(duration)}</span>
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
