import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './MyAnnouncementsPage.css';
import config from '../../api/config';
import AnnouncementCard from '../../components/AnnouncementCard/AnnouncementCard';
import EditAnnouncementForm from '../../components/EditAnnouncementForm/EditAnnouncementForm';

function MyAnnouncementsPage({ currentUser }) {
  const navigate = useNavigate();
  const [announcements, setAnnouncements] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [editingId, setEditingId] = useState(null);
  const [error, setError] = useState(null);

  // Récupérer l'ID utilisateur depuis le localStorage
  const userId = parseInt(localStorage.getItem('userId'));

  useEffect(() => {
    if (!currentUser) {
      navigate('/login');
      return;
    }

    if (!userId) {
      setError('Erreur : ID utilisateur non trouvé. Veuillez vous reconnecter.');
      setIsLoading(false);
      return;
    }

    fetchMyAnnouncements();
  }, [currentUser, navigate, userId]);

  const fetchMyAnnouncements = async () => {
    setIsLoading(true);
    setError(null);
    try {
      const response = await fetch(
        `${config.announceManagerServiceUrl}/api/announcements/enriched/user/${userId}`
      );
      if (response.ok) {
        const data = await response.json();
        const mappedData = data.map((announcement) => ({
          ...announcement,
          ownerUsername: announcement.username
        }));
        setAnnouncements(mappedData || []);
      } else {
        setError('Erreur lors de la récupération de vos annonces');
        setAnnouncements([]);
      }
    } catch (error) {
      console.error('Erreur :', error);
      setError('Erreur serveur');
      setAnnouncements([]);
    } finally {
      setIsLoading(false);
    }
  };

  const handleEdit = (announcementId) => {
    setEditingId(announcementId);
  };

  const handleCancelEdit = () => {
    setEditingId(null);
  };

  const handleAnnouncementUpdated = (updatedAnnouncement) => {
    setAnnouncements((prev) =>
      prev.map((a) => (a.id === updatedAnnouncement.id ? updatedAnnouncement : a))
    );
    setEditingId(null);
  };

  const handleDelete = async (announcementId) => {
    if (window.confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      try {
        const response = await fetch(
          `${config.announceManagerServiceUrl}/api/announcements/${announcementId}?userId=${userId}`,
          {
            method: 'DELETE',
          }
        );

        if (response.ok) {
          setAnnouncements((prev) => prev.filter((a) => a.id !== announcementId));
        } else if (response.status === 403) {
          alert('Vous n\'êtes pas autorisé à supprimer cette annonce');
        } else if (response.status === 404) {
          alert('Annonce non trouvée');
        } else {
          alert('Erreur lors de la suppression');
        }
      } catch (error) {
        console.error('Erreur lors de la suppression:', error);
        alert('Erreur serveur');
      }
    }
  };

  if (!currentUser) {
    return (
      <div className="my-announcements-page">
        <p>Veuillez vous connecter pour voir vos annonces.</p>
      </div>
    );
  }

  return (
    <div className="my-announcements-page">
      <div className="my-announcements-container">
        <section className="my-announcements-hero">
          <h1>Mes annonces</h1>
          <p>Gérez vos annonces : modifiez ou supprimez-les</p>
        </section>

        {error && <div className="error-message">{error}</div>}

        {isLoading ? (
          <div className="loading">Chargement de vos annonces...</div>
        ) : announcements.length === 0 ? (
          <div className="no-announcements">
            <p>Vous n'avez pas encore créé d'annonce.</p>
            <a href="/" className="create-link">
              Créer une annonce
            </a>
          </div>
        ) : (
          <div className="announcements-list">
            {announcements.map((announcement) => (
              <div key={announcement.id} className="announcement-item">
                {editingId === announcement.id ? (
                  <EditAnnouncementForm
                    announcement={announcement}
                    userId={userId}
                    onAnnouncementUpdated={handleAnnouncementUpdated}
                    onCancel={handleCancelEdit}
                  />
                ) : (
                  <>
                    <AnnouncementCard
                      announcement={announcement}
                      isOwnAnnouncement={true}
                    />
                    <div className="action-buttons">
                      <button
                        className="edit-btn"
                        onClick={() => handleEdit(announcement.id)}
                      >
                        ✏️ Modifier
                      </button>
                      <button
                        className="delete-btn"
                        onClick={() => handleDelete(announcement.id)}
                      >
                        🗑️ Supprimer
                      </button>
                    </div>
                  </>
                )}
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default MyAnnouncementsPage;
