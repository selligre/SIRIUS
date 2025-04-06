import React, {useCallback, useEffect, useState} from 'react';
import '../styles/Announce.css';
import {
    LOCAL_HOST_CLIENTS
} from "../api/constants/back";
import {useParams} from "react-router-dom";

export default function ClientAnnounce() {
    const [announces, setAnnounces] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [notification] = useState({show: false, message: '', type: ''});

    const { clientId } = useParams();

    const setAnnounceData = useCallback( async () => {
        const url = `${LOCAL_HOST_CLIENTS}/${clientId}/announces?page=${currentPage - 1}`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                setAnnounces(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(error => {
                console.error('Error loading announces:', error);
                alert("Error occurred while loading data:" + error);
            });
    }, [currentPage, clientId]);

    function handleNextPage() {
        setCurrentPage(prevPage => Math.min(prevPage + 1, totalPages));
    }

    function handlePreviousPage() {
        setCurrentPage(prevPage => Math.max(prevPage - 1, 1));
    }

    useEffect(() => {
        document.title = 'Annonces de l\'utilisateur';
        setAnnounceData();
    }, [currentPage, setAnnounceData]);

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

    const [sortConfig, setSortConfig] = useState({key: null, direction: 'asc'});

    const sortedAnnounces = [...announces].sort((a, b) => {
        if (!sortConfig.key) return 0;

        const key = sortConfig.key;
        const order = sortConfig.direction === 'asc' ? 1 : -1;

        if (a[key] < b[key]) return -1 * order;
        if (a[key] > b[key]) return 1 * order;
        return 0;
    });

    const handleSort = (key) => {
        setSortConfig((prev) => {
            const isSameKey = prev.key === key;
            const direction = isSameKey && prev.direction === 'asc' ? 'desc' : 'asc';
            return {key, direction};
        });
    };


    const formatDuration = (duration) => {
        if (isNaN(duration) || duration <= 0) return "0 h 0 min";

        const hours = Math.floor(duration);
        const minutes = Math.round((duration - hours) * 60);
        if (minutes < 1) {
            return `${hours} h`;
        } else {
            return `${hours} h ${minutes} min`;
        }
    };


    const renderReadOnlyRow = (announce) => (
        <>
            <td>{formatDateTime(announce.publicationDate)}</td>
            <td>{announce.status}</td>
            <td>{announce.type}</td>
            <td>{announce.title}</td>
            <td>{announce.description}</td>
            <td>{formatDateTime(announce.dateTimeStart)}</td>
            <td className="duration-cell">{formatDuration(announce.duration)}</td>
            <td>{formatDateTime(announce.dateTimeEnd)}</td>
            <td className="text-center">
                <input
                    type="checkbox"
                    checked={announce.isRecurrent || false}
                    disabled
                />
            </td>
        </>
    );


    return (
        <div className="announce-container">
            {notification.show && (
                <div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>
            )}

            <div className="announce-content">
                <div className="section">
                    <h4 className="section-title">Liste des annonces</h4>
                    {announces.length === 0 ? (
                        <div className="alert alert-info">No announces available</div>
                    ) : (
                        <div className="table-container">
                            <table className="table table-bordered table-hover">
                                <thead className="table-light">
                                <tr>
                                    <th onClick={() => handleSort('publicationDate')}>Date de publication</th>
                                    <th onClick={() => handleSort('status')}>Status</th>
                                    <th onClick={() => handleSort('type')}>Type</th>
                                    <th onClick={() => handleSort('title')}>Titre</th>
                                    <th onClick={() => handleSort('description')}>Description</th>
                                    <th onClick={() => handleSort('dateTimeStart')}>Date de début</th>
                                    <th onClick={() => handleSort('duration')}>Durée</th>
                                    <th onClick={() => handleSort('dateTimeEnd')}>Date de fin</th>
                                    <th>Récurrence</th>
                                </tr>
                                </thead>
                                <tbody>
                                {sortedAnnounces.map((announce, index) => (
                                    <tr key={index}>
                                        {renderReadOnlyRow(announce)}
                                    </tr>
                                ))}
                                </tbody>
                            </table>
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
