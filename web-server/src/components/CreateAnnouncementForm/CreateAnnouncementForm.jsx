import React, { useState } from 'react';
import './CreateAnnouncementForm.css';
import config from '../../api/config';

function CreateAnnouncementForm({ currentUser, onAnnouncementCreated }) {
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    startTime: '',
    endTime: '',
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

  const validateForm = () => {
    if (!formData.title.trim()) {
      setError('Le titre est obligatoire');
      return false;
    }
    if (!formData.description.trim()) {
      setError('La description est obligatoire');
      return false;
    }
    if (!formData.startTime) {
      setError('L\'heure de début est obligatoire');
      return false;
    }
    if (!formData.endTime) {
      setError('L\'heure de fin est obligatoire');
      return false;
    }

    const startTime = new Date(formData.startTime);
    const endTime = new Date(formData.endTime);

    if (endTime <= startTime) {
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
      const announcementData = {
        ...formData,
        author: currentUser,
        timestamp: new Date().toISOString(),
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
        startTime: '',
        endTime: '',
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
            <label htmlFor="startTime">Heure de début *</label>
            <input
              type="datetime-local"
              id="startTime"
              name="startTime"
              value={formData.startTime}
              onChange={handleChange}
              disabled={isSubmitting}
            />
          </div>

          <div className="form-group">
            <label htmlFor="endTime">Heure de fin *</label>
            <input
              type="datetime-local"
              id="endTime"
              name="endTime"
              value={formData.endTime}
              onChange={handleChange}
              disabled={isSubmitting}
            />
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
