import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';
import config from '../../api/config';
import AnnouncementCard from '../../components/AnnouncementCard/AnnouncementCard';
import CreateAnnouncementForm from '../../components/CreateAnnouncementForm/CreateAnnouncementForm';

function HomePage({ currentUser, onUpdateNotificationCount }) {
  const navigate = useNavigate();
  const [announcements, setAnnouncements] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [searchKeyword, setSearchKeyword] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [pageSize] = useState(20);

  // Appeler la fonction pour mettre à jour le compteur de notifications uniquement au chargement de la page
  useEffect(() => {
    if (onUpdateNotificationCount) {
      onUpdateNotificationCount();
    }
  }, []);

  useEffect(() => {
    fetchAnnouncements(searchKeyword, currentPage);
  }, [searchKeyword, currentPage]);

  const fetchAnnouncements = async (keyword = '', page = 0) => {
    setIsLoading(true);
    try {
      // Utiliser l'endpoint enrichi pour obtenir les annonces avec le username de l'auteur
      const response = await fetch(`${config.announceManagerServiceUrl}/api/announcements/enriched/all`);
      if (response.ok) {
        const data = await response.json();
        // Mapper les données enrichies au format attendu par AnnouncementCard
        let announcements = data.map((announcement) => ({
          ...announcement,
          ownerUsername: announcement.username
        }));
        
        // Filtrer par mot-clé si présent
        if (keyword.trim()) {
          const lowerKeyword = keyword.toLowerCase();
          announcements = announcements.filter(a =>
            a.title.toLowerCase().includes(lowerKeyword) ||
            a.description.toLowerCase().includes(lowerKeyword) ||
            (a.ownerUsername && a.ownerUsername.toLowerCase().includes(lowerKeyword))
          );
        }
        
        // Pagination manuelle
        const pageSize = 20;
        const totalPages = Math.ceil(announcements.length / pageSize);
        const startIndex = page * pageSize;
        const endIndex = startIndex + pageSize;
        const paginatedAnnouncements = announcements.slice(startIndex, endIndex);
        
        setAnnouncements(paginatedAnnouncements);
        setTotalPages(totalPages);
      } else {
        console.error('Erreur lors de la récupération des annonces');
        setAnnouncements([]);
        setTotalPages(0);
      }
    } catch (error) {
      console.error('Erreur :', error);
      setAnnouncements([]);
      setTotalPages(0);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSearch = (keyword) => {
    setSearchKeyword(keyword);
    setCurrentPage(0);
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleAnnouncementCreated = (newAnnouncement) => {
    setAnnouncements((prev) => [newAnnouncement, ...prev]);
    setShowCreateForm(false);
  };

  const handleViewDetails = (announcementId) => {
    navigate(`/announcement/${announcementId}`);
  };

  return (
    <div className="home-page">
      <div className="home-container">
        <section className="home-hero">
          <h1>Bienvenue sur Ville Partagée</h1>
          <p>Découvrez toutes les annonces disponibles dans votre communauté</p>
        </section>

        {currentUser && (
          <div className="create-announcement-section">
            <button
              className="create-btn"
              onClick={() => setShowCreateForm(!showCreateForm)}
            >
              {showCreateForm ? 'Fermer' : '+ Créer une annonce'}
            </button>
            {showCreateForm && (
              <CreateAnnouncementForm
                currentUser={currentUser}
                onAnnouncementCreated={handleAnnouncementCreated}
              />
            )}
          </div>
        )}

        <section className="announcements-section">
          <h2>Annonces disponibles</h2>

          <div className="search-bar">
            <input
              type="text"
              placeholder="Rechercher une annonce..."
              value={searchKeyword}
              onChange={(e) => handleSearch(e.target.value)}
              className="search-input"
            />
            {searchKeyword && (
              <button
                className="clear-search-btn"
                onClick={() => handleSearch('')}
              >
                ✕
              </button>
            )}
          </div>

          {isLoading ? (
            <div className="loading">Chargement des annonces...</div>
          ) : announcements.length === 0 ? (
            <div className="no-announcements">
              <p>Aucune annonce trouvée pour le moment.</p>
            </div>
          ) : (
            <>
              <div className="announcements-list">
                {announcements.map((announcement) => (
                  <AnnouncementCard
                    key={announcement.id}
                    announcement={announcement}
                    onViewDetails={() => handleViewDetails(announcement.id)}
                  />
                ))}
              </div>

              <div className="pagination">
                <button
                  onClick={handlePreviousPage}
                  disabled={currentPage === 0}
                  className="pagination-btn"
                >
                  ← Précédent
                </button>
                <span className="pagination-info">
                  Page {currentPage + 1} sur {totalPages}
                </span>
                <button
                  onClick={handleNextPage}
                  disabled={currentPage >= totalPages - 1}
                  className="pagination-btn"
                >
                  Suivant →
                </button>
              </div>
            </>
          )}
        </section>
      </div>
    </div>
  );
}

export default HomePage;
