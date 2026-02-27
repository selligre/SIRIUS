import React, { useState } from 'react';
import './CreateAnnouncementForm.css';
import config from '../../api/config';

function CreateAnnouncementForm({ currentUser, onAnnouncementCreated }) {
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

  const getCurrentDateTime = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const roundedMinutes = roundToNearestQuarter(now.getMinutes());
    const minutes = String(roundedMinutes).padStart(2, '0');

    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    dateTimeStart: getCurrentDateTime(),
    dateTimeEnd: getCurrentDateTime(),
    publicationDate: getCurrentDateTime(),
    status: '0', // DRAFT
    type: '0', // Service
    authorId: parseInt(localStorage.getItem('userId')) || 0
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    setError('');
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
    setError('');
    setSuccess('');

    try {
      // Convertir les chaînes utilisateur en nombres pour les enums
      const announcementData = {
        title: formData.title,
        description: formData.description,
        dateTimeStart: formData.dateTimeStart,
        dateTimeEnd: formData.dateTimeEnd,
        publicationDate: formData.publicationDate,
        status: parseInt(formData.status),
        type: parseInt(formData.type),
        authorId: parseInt(formData.authorId),
      };

      const response = await fetch(`${config.announceManagerServiceUrl}/api/announcements`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(announcementData),
      });

      if (!response.ok) {
        throw new Error('Erreur lors de la création de l\'annonce');
      }

      const newAnnouncement = await response.json();
      setSuccess('Annonce créée avec succès !');

      setFormData({
        title: '',
        description: '',
        dateTimeStart: getCurrentDateTime(),
        dateTimeEnd: getCurrentDateTime(),
        publicationDate: getCurrentDateTime(),
        status: '0', // DRAFT
        type: '0', // Service
        authorId: parseInt(localStorage.getItem('userId')) || 0
      });

      if (onAnnouncementCreated) {
        onAnnouncementCreated(newAnnouncement);
      }

      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err.message || 'Erreur lors de la création de l\'annonce');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="create-announcement-form">
      <h2>Créer une annonce</h2>
      <form onSubmit={handleSubmit}>
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
          ></textarea>
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
            >
              <option value="00">00</option>
              <option value="15">15</option>
              <option value="30">30</option>
              <option value="45">45</option>
            </select>
          </div>
        </div>

        <div className="form-info">
          <p><strong>Créateur :</strong> {currentUser || 'Non connecté'}</p>
        </div>

        {error && <div className="form-error">{error}</div>}
        {success && <div className="form-success">{success}</div>}

        <button
          type="submit"
          className="form-submit-btn"
          disabled={isSubmitting}
        >
          {isSubmitting ? 'Création en cours...' : 'Créer l\'annonce'}
        </button>
      </form>
    </div>
  );
}

export default CreateAnnouncementForm;
