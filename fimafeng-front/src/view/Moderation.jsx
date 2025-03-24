import React, {useCallback, useEffect, useState} from "react";
import axios from "axios";
import '../styles/Moderation.css';
import {GET_MODERATION, UPDATE_MODERATION} from "../api/constants/back";
import {Link} from "react-router-dom";


export default function Moderation() {

    const [moderations, setModerations] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [notification, setNotification] = useState({show: false, message: '', type: ''});
    const [editingId, setEditingId] = useState(null);


    const [sortConfig] = useState({key: 'id', direction: 'desc'});

    const showNotification = (message, type = 'success') => {
        setNotification({show: true, message, type});
        setTimeout(() => setNotification({show: false, message: '', type: ''}), 3000);
    };

    const formatDateTime = (dateString) => {
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

    const setModerationData = useCallback( async () => {
        const url = `${GET_MODERATION}?page=${currentPage - 1}&size=10`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                setModerations(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(error => {
                console.error('Error loading moderations:', error);
                alert("Error occurred while loading data:" + error);
            });
    }, [currentPage]);

    useEffect(() => {
        setModerationData();
    }, [currentPage, setModerationData]);

    function handleNextPage() {
        setCurrentPage(prevPage => Math.min(prevPage + 1, totalPages));
    }

    function handlePreviousPage() {
        setCurrentPage(prevPage => Math.max(prevPage - 1, 1));
    }

    const sortedModerations = [...moderations].sort((a, b) => {
        if (!sortConfig.key) return 0;

        const key = sortConfig.key;
        const order = sortConfig.direction === 'asc' ? 1 : -1;

        if (a[key] < b[key]) return -1 * order;
        if (a[key] > b[key]) return 1 * order;
        return 0;
    });

    const updateModeration = async (announce) => {
        if (
            !announce.reason?.trim() ||
            !announce.description?.trim()
        ) {
            alert('Please ensure that reason and description are not empty or only spaces.');
            return;
        }

        const announceToUpdate = {
            ...announce,
            reason: announce.reason.trim(),
            description: announce.description.trim(),
        };

        console.log('Updating announce:', announceToUpdate);

        try {
            const response = await axios.post(UPDATE_MODERATION, announceToUpdate);
            console.log('Updated announce:', response.data);
            showNotification('Moderation successfully updated');
            setEditingId(null);
            setModerationData();
        } catch (error) {
            console.error('Error updating announce:', error);
            alert("Error occurred in updateModeration: " + error);
        }
    };


    const handleChangeField = (id, field, value) => {
        setModerations(prevData => prevData.map(
            row => row.id === id ? {...row, [field]: value} : row)
        );
    }

    const handleCancelEdit = () => {
        setEditingId(null);
        setModerationData();
    };

    const renderReadOnlyItem = (moderation) => (
        <div className="card">
            <div className="card-left">
                <div className="card-subtitle">Information de l'action</div>
                <div className="card-field">
                    <span className="card-key">Modérée le : </span>{formatDateTime(moderation.moderationDate)}</div>
                <div className="card-field">
                    <span className="card-key">Modérée par : </span>{moderation.moderatorName}</div>
                <div className="card-field">
                    <span className="card-key">Raison : </span>{moderation.reason}</div>
                <div className="card-field">
                    <span className="card-key">Description :</span>
                    <div className="card-text-field">
                        {moderation.description}
                    </div>
                </div>
                <div className="moderator-actions">
                    <div className="moderator-actions-left">
                        <button
                            type="button"
                            className="btn btn-danger"
                            onClick={() => console.log("annonce delete event")}
                            disabled
                        >Supprimer l'annonce
                        </button>
                    </div>
                    <div className="moderator-actions-right">
                        <button
                            type="button"
                            className="btn btn-warning"
                            onClick={() => setEditingId(moderation.id)}
                        >Modifier les raisons
                        </button>
                    </div>
                    <div>
                        <Link
                            type="button"
                            className="btn btn-primary"
                            to={`/moderation/${moderation.announceId}`}
                        >Voir l'historique
                        </Link>
                    </div>
                    <div>
                        <button
                            type="button"
                            className="btn btn-success"
                            onClick={() => console.log("annonce delete event")}
                            disabled
                        >Approuver l'annonce
                        </button>
                    </div>
                </div>
            </div>
            <div className="card-right">
                <div className="card-subtitle">État de l'annonce lors de l'action</div>
                <div className="card-field">
                    <span className="card-key">Publiée le : </span>{formatDateTime(moderation.announcePublicationDate)}
                </div>
                <div className="card-field">
                    <span className="card-key">Autheur par : </span>{moderation.authorId}</div>
                <div className="card-field">
                    <span className="card-key">Type : </span>{moderation.announceType}</div>
                <div className="card-field">
                    <span className="card-key">Titre :</span>
                    <div className="card-text-field">{moderation.announceTitle}</div>
                </div>
                <div className="card-field">
                    <span className="card-key">Description :</span>
                    <div className="card-text-field">{moderation.announceDescription}</div>
                </div>
            </div>
        </div>
    );

    const renderEditItem = (moderation) => (
        <div className="card">
            <div className="card-left">
                <div className="card-subtitle">Information de l'action</div>
                <div className="card-field">
                    <span className="card-key">Modérée le : </span>{formatDateTime(moderation.moderationDate)}</div>
                <div className="card-field">
                    <span className="card-key">Modérée par : </span>{moderation.moderatorName}</div>
                <div className="card-field">
                    <span className="card-key">Raison : </span>
                    <select
                        className="form-control"
                        value={moderation.reason || ''}
                        onChange={e => handleChangeField(moderation.id, 'reason', e.target.value)}
                    >
                        <option value="SPAM">Spam</option>
                        <option value="HATE">Haine</option>
                        <option value="HARASSMENT">Harcèlement</option>
                        <option value="DISINFORMATION">Désinformation</option>
                        <option value="VIOLENCE">Violence</option>
                        <option value="PORNOGRAPHY">Pornographie</option>
                        <option value="INTIMIDATION">Intimidation</option>
                        <option value="IDENTITY_STEALTH">Vol d'identité</option>
                        <option value="PRIVACY_VIOLATION">Atteinte à la vie privée</option>
                        <option value="UNDEFINED">Indéfini</option>
                    </select>
                </div>
                <div className="card-field">
                    <span className="card-key">Description :</span>
                    <textarea
                        className="form-control"
                        value={moderation.description || ''}
                        onChange={e => handleChangeField(moderation.id, 'description', e.target.value)}
                    />
                </div>
                <div className="moderator-actions">
                    <div className="moderator-actions-left">
                        <button
                            type="button"
                            className="btn btn-danger"
                            onClick={handleCancelEdit}
                        >Annuler la mise à jour
                        </button>
                    </div>
                    <div>
                        <button
                            type="button"
                            className="btn btn-success"
                            onClick={() => updateModeration(moderation)}
                        >Mettre à jour les raisons de modération
                        </button>
                    </div>
                </div>
            </div>
            <div className="card-right">
                <div className="card-subtitle">État de l'annonce lors de l'action</div>
                <div className="card-field">
                    <span className="card-key">Publiée le : </span>{formatDateTime(moderation.announcePublicationDate)}
                </div>
                <div className="card-field">
                    <span className="card-key">Autheur par : </span>{moderation.authorId}</div>
                <div className="card-field">
                    <span className="card-key">Type : </span>{moderation.announceType}</div>
                <div className="card-field">
                    <span className="card-key">Titre :</span>
                    <div className="card-text-field">{moderation.announceTitle}</div>
                </div>
                <div className="card-field">
                    <span className="card-key">Description :</span>
                    <div className="card-text-field">{moderation.announceDescription}</div>
                </div>
            </div>
        </div>
    );

    return (
        <div className="moderation-container">
            {notification.show && (
                <div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>
            )}

            <div className="moderation-content">
                <div className="section">
                    <h4 className="section-title">Liste des modérations</h4>
                    {moderations.length === 0 ? (
                        <div className="alert alert-info">No moderations available</div>
                    ) : (

                        <div>
                        {sortedModerations.map((moderation, index) => (
                            <div key={index}>
                                {editingId === moderation.id
                                    ? renderEditItem(moderation)
                                    : renderReadOnlyItem(moderation)}
                            </div>
                        ))}
                        </div>
                    )}
                </div>
            </div>
            <div className="pagination">
                <button onClick={handlePreviousPage} disabled={currentPage === 1}>&lt;</button>
                <span>Page {currentPage} sur {totalPages}</span>
                <button onClick={handleNextPage} disabled={currentPage === totalPages}>&gt;</button>
            </div>
        </div>
    );
}