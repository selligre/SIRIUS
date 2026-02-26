import React, { useState } from 'react';
import './EditAnnouncementForm.css';
import config from '../../api/config';

// Fonction utilitaire pour parser les dates en différents formats
const parseDateString = (dateString) => {
  if (!dateString) return new Date();
  
  // Essayer le format ISO 8601 avec T : "2023-06-24T03:30:00"
  if (dateString.includes('T')) {
    return new Date(dateString);
  }
  
  // Essayer le format avec espace : "2023-06-24 03:30:00"
  if (dateString.includes(' ')) {
    const [datePart, timePart] = dateString.split(' ');
    const [year, month, day] = datePart.split('-');
    const [hours, minutes, seconds] = timePart.split(':');
    return new Date(year, month - 1, day, hours, minutes, seconds || 0);
  }
  
  // Format par défaut
  return new Date(dateString);
};

function EditAnnouncementForm({ announcement, userId, onAnnouncementUpdated, onCancel }) {
  // Arrondir les minutes au quart d'heure le plus proche (0, 15, 30, 45)
  const roundToNearestQuarter = (minutes) => {
    const quarters = [0, 15, 30, 45];
    return quarters.reduce((prev, curr) => 
      Math.abs(curr - minutes) < Math.abs(prev - minutes) ? curr : prev
    );
  };

  const getMinDateTime = () => {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}T00:00`;
  };

  // Convertir le statut enum en valeur numérique
  const getStatusValue = (status) => {
    if (status === 'PUBLISHED' || status === 1) return '1';
    return '0';
  };

  // Convertir le type enum en valeur numérique
  const getTypeValue = (type) => {
    if (type === 'SERVICE' || type === 0) return '0';
    if (type === 'LOAN' || type === 1) return '1';
    if (type === 'LIFE_EVENT' || type === 2) return '2';
    return '0';
  };

  // Convertir les valeurs numériques en format ISO 8601
  const convertToISODateTime = (dateString) => {
    if (dateString.includes('T')) {
      return dateString;
    }
    // Format "2023-06-24 03:30:00" -> "2023-06-24T03:30:00"
    if (dateString.includes(' ')) {
      return dateString.replace(' ', 'T');
    }
    return dateString;
  };

  const [formData, setFormData] = useState({
    title: announcement.title,
    description: announcement.description,
    dateTimeStart: convertToISODateTime(announcement.dateTimeStart.slice(0, 19)),
    dateTimeEnd: convertToISODateTime(announcement.dateTimeEnd.slice(0, 19)),
    status: getStatusValue(announcement.status),
    type: getTypeValue(announcement.type),
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setError(null);
  };

  const handleDateTimeChange = (baseFieldName, dateValue, hoursValue, minutesValue) => {
    if (dateValue && hoursValue !== undefined && minutesValue !== undefined) {
      const hours = String(hoursValue).padStart(2, '0');
      const minutes = String(minutesValue).padStart(2, '0');
      const dateTimeValue = `${dateValue}T${hours}:${minutes}`;
      setFormData((prev) => ({
        ...prev,
        [baseFieldName]: dateTimeValue,
      }));
    }
  };

  const getDateTimeParts = (dateTimeString) => {
    if (!dateTimeString) {
      const now = new Date();
      return {
        date: `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}`,
        hours: String(now.getHours()).padStart(2, '0'),
        minutes: String(roundToNearestQuarter(now.getMinutes())).padStart(2, '0'),
      };
    }
    const [datePart, timePart] = dateTimeString.split('T');
    const [hours, minutes] = timePart.split(':');
    return { date: datePart, hours, minutes };
  };

  const validateForm = () => {
    if (!formData.title.trim()) {
      setError('Le titre est obligatoire');
      return false;
    }
    if (!formData.description.trim()) {
      setError('La description est obligatoire');
      return false;
    }
    if (!formData.dateTimeStart) {
      setError('L\'heure de début est obligatoire');
      return false;
    }
    if (!formData.dateTimeEnd) {
      setError('L\'heure de fin est obligatoire');
      return false;
    }

    const dateTimeStart = new Date(formData.dateTimeStart);
    const dateTimeEnd = new Date(formData.dateTimeEnd);

    if (dateTimeEnd <= dateTimeStart) {
      setError('L\'heure de fin doit être après l\'heure de début');
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setIsSubmitting(true);
    setError(null);

    try {
      const announceDTO = {
        title: formData.title,
        description: formData.description,
        dateTimeStart: formData.dateTimeStart,
        dateTimeEnd: formData.dateTimeEnd,
        publicationDate: new Date().toISOString().slice(0, 19),
        status: parseInt(formData.status),
        type: parseInt(formData.type),
        authorId: userId,
      };

      const response = await fetch(
        `${config.announceManagerServiceUrl}/api/announcements/${announcement.id}?userId=${userId}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(announceDTO),
        }
      );

      if (response.ok) {
        const updatedAnnounce = await response.json();
        onAnnouncementUpdated(updatedAnnounce);
      } else if (response.status === 403) {
        setError('Vous n\'êtes pas autorisé à modifier cette annonce');
      } else if (response.status === 404) {
        setError('Annonce non trouvée');
      } else {
        setError('Erreur lors de la modification');
      }
    } catch (error) {
      console.error('Erreur:', error);
      setError('Erreur serveur');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <form className="edit-announcement-form" onSubmit={handleSubmit}>
      <h3>Modifier l'annonce</h3>

      {error && <div className="error-message">{error}</div>}

      <div className="form-group">
        <label htmlFor="title">Titre *</label>
        <input
          type="text"
          id="title"
          name="title"
          value={formData.title}
          onChange={handleChange}
          placeholder="Entrez le titre de l'annonce"
          disabled={isSubmitting}
          maxLength="100"
          required
        />
      </div>

      <div className="form-group">
        <label htmlFor="description">Description *</label>
        <textarea
          id="description"
          name="description"
          value={formData.description}
          onChange={handleChange}
          placeholder="Entrez la description de l'annonce"
          rows="5"
          disabled={isSubmitting}
          maxLength="1000"
          required
        />
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="status">Statut *</label>
          <select
            id="status"
            name="status"
            value={formData.status}
            onChange={handleChange}
            disabled={isSubmitting}
            required
          >
            <option value="0">Brouillon</option>
            <option value="1">Publier</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="type">Type d'annonce *</label>
          <select
            id="type"
            name="type"
            value={formData.type}
            onChange={handleChange}
            disabled={isSubmitting}
            required
          >
            <option value="0">Service</option>
            <option value="1">Prêt</option>
            <option value="2">Événement</option>
          </select>
        </div>
      </div>

      <div className="form-row datetime-row">
        <div className="form-group">
          <label htmlFor="dateTimeStart-date">Date de début *</label>
          <input
            type="date"
            id="dateTimeStart-date"
            value={getDateTimeParts(formData.dateTimeStart).date}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeStart);
              handleDateTimeChange('dateTimeStart', e.target.value, parts.hours, parts.minutes);
            }}
            disabled={isSubmitting}
            min={getMinDateTime().split('T')[0]}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="dateTimeStart-hours">Heure de début *</label>
          <select
            id="dateTimeStart-hours"
            value={getDateTimeParts(formData.dateTimeStart).hours}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeStart);
              handleDateTimeChange('dateTimeStart', parts.date, e.target.value, parts.minutes);
            }}
            disabled={isSubmitting}
            required
          >
            {[...Array(24)].map((_, i) => (
              <option key={i} value={String(i).padStart(2, '0')}>
                {String(i).padStart(2, '0')}h
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="dateTimeStart-minutes">Minutes *</label>
          <select
            id="dateTimeStart-minutes"
            value={getDateTimeParts(formData.dateTimeStart).minutes}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeStart);
              handleDateTimeChange('dateTimeStart', parts.date, parts.hours, e.target.value);
            }}
            disabled={isSubmitting}
            required
          >
            <option value="00">00</option>
            <option value="15">15</option>
            <option value="30">30</option>
            <option value="45">45</option>
          </select>
        </div>
      </div>

      <div className="form-row datetime-row">
        <div className="form-group">
          <label htmlFor="dateTimeEnd-date">Date de fin *</label>
          <input
            type="date"
            id="dateTimeEnd-date"
            value={getDateTimeParts(formData.dateTimeEnd).date}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeEnd);
              handleDateTimeChange('dateTimeEnd', e.target.value, parts.hours, parts.minutes);
            }}
            disabled={isSubmitting}
            min={getMinDateTime().split('T')[0]}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="dateTimeEnd-hours">Heure de fin *</label>
          <select
            id="dateTimeEnd-hours"
            value={getDateTimeParts(formData.dateTimeEnd).hours}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeEnd);
              handleDateTimeChange('dateTimeEnd', parts.date, e.target.value, parts.minutes);
            }}
            disabled={isSubmitting}
            required
          >
            {[...Array(24)].map((_, i) => (
              <option key={i} value={String(i).padStart(2, '0')}>
                {String(i).padStart(2, '0')}h
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="dateTimeEnd-minutes">Minutes *</label>
          <select
            id="dateTimeEnd-minutes"
            value={getDateTimeParts(formData.dateTimeEnd).minutes}
            onChange={(e) => {
              const parts = getDateTimeParts(formData.dateTimeEnd);
              handleDateTimeChange('dateTimeEnd', parts.date, parts.hours, e.target.value);
            }}
            disabled={isSubmitting}
            required
          >
            <option value="00">00</option>
            <option value="15">15</option>
            <option value="30">30</option>
            <option value="45">45</option>
          </select>
        </div>
      </div>

      <div className="form-actions">
        <button type="submit" disabled={isSubmitting} className="submit-btn">
          {isSubmitting ? 'Enregistrement...' : 'Enregistrer les modifications'}
        </button>
        <button type="button" onClick={onCancel} className="cancel-btn">
          Annuler
        </button>
      </div>
    </form>
  );
}

export default EditAnnouncementForm;
