import React, {useEffect, useState} from "react";
import '../styles/Client.css';
import {GET_CLIENTS_SEARCH} from "../api/constants/back";
import {Link} from "react-router-dom";

export default function Client() {

    const [clients, setClients] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [notification] = useState({show: false, message: '', type: ''});
    const [sortConfig, setSortConfig] = useState({key: 'id', direction: 'desc'});

    const setClientData = useCallback(async () => {
        const url = `${GET_CLIENTS_SEARCH}?page=${currentPage - 1}&size=10`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                setClients(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(error => {
                console.error('Error loading announces:', error);
                alert("Error occurred while loading data:" + error);
            });
    }, [currentPage]);

    useEffect(() => {
        setClientData();
    }, [currentPage]);

    const setClientData = async () => {
        const url = `${GET_CLIENTS_SEARCH}?page=${currentPage - 1}&size=10`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                setClients(data.content);
                setTotalPages(data.totalPages);
            })
            .catch(error => {
                console.error('Error loading announces:', error);
                alert("Error occurred while loading data:" + error);
            });
    }

    function handleNextPage() {
        setCurrentPage(prevPage => Math.min(prevPage + 1, totalPages));
    }

    function handlePreviousPage() {
        setCurrentPage(prevPage => Math.max(prevPage - 1, 1));
    }

    const handleSort = (key) => {
        setSortConfig((prev) => {
            const isSameKey = prev.key === key;
            const direction = isSameKey && prev.direction === 'asc' ? 'desc' : 'asc';
            return {key, direction};
        });
    };

    const sortedClients = [...clients].sort((a, b) => {
        if (!sortConfig.key) return 0;

        const key = sortConfig.key;
        const order = sortConfig.direction === 'asc' ? 1 : -1;

        if (a[key] < b[key]) return -1 * order;
        if (a[key] > b[key]) return 1 * order;
        return 0;
    });

    const renderReadOnlyRow = (client) => (
        <>
            <td>{client.id}</td>
            <td>{client.firstName}</td>
            <td>{client.lastName}</td>
            <td>{client.email}</td>
            <td>{client.district}</td>
            <td>
                <Link type="button"
                      className="btn btn-primary"
                      to={`/client/${client.id}/announces`}>
                    Voir les annonces
                </Link>
            </td>
        </>
    );

    return (
        <div className="client-container">
            {notification.show && (
                <div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>
            )}

            <div className="client-content">
                <div className="section">
                    <h4 className="section-title">Liste des clients</h4>
                    {clients.length === 0 ? (
                        <div className="alert alert-info">No clients available</div>
                    ) : (
                        <div className="table-container">
                            <table className="table table-bordered table-hover">
                                <thead className="table-light">
                                <tr>
                                    <th onClick={() => handleSort('id')}>ID</th>
                                    <th onClick={() => handleSort('firstName')}>Prénom</th>
                                    <th onClick={() => handleSort('lastName')}>Nom</th>
                                    <th onClick={() => handleSort('email')}>Email</th>
                                    <th onClick={() => handleSort('refDistrict')}>Quartier</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {sortedClients.map((client, index) => (
                                    <tr key={index}>
                                        {renderReadOnlyRow(client)}
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