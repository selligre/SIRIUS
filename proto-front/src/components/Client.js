import React, {useEffect, useState} from "react";
import axios from "axios";
import '../styles/Client.css';
import {GET_CLIENTS} from "../constants/back";


export default function Client() {

    const [clients, setClients] = useState([]);
    const [notification, setNotification] = useState({show: false, message: '', type: ''});
    const [newClient, setNewClient] = useState({
        firstName: '',
        lastName: '',
        email: '',
        district: 1,
    });

    const [sortConfig, setSortConfig] = useState({key: 'id', direction: 'desc'});

    useEffect(() => {
        setClientData();
    }, []);

    const setClientData = async () => {
        axios.get(GET_CLIENTS).then((response) => {
            console.log('Received clients:', response.data);
            setClients(response.data || []);
        }).catch(error => {
            console.error('Error loading clients:', error);
            alert("Error occurred while loading data:" + error);
        });
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
        </div>
    );
}