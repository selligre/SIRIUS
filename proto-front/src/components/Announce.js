import React, {useEffect, useState} from 'react';
import axios from "axios";
import '../styles/Announce.css';
import {ADD_ANNOUNCE, GET_ANNOUNCES, LOCAL_HOST_ANNOUNCE, UPDATE_ANNOUNCES} from "../constants/back";

export default function Announce() {
    const getCurrentDateTime = () => {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day}T${hours}:${minutes}`;
    };

    const [announces, setAnnounces] = useState([]);
    const [editingId, setEditingId] = useState(null);
    const [showCreateForm, setShowCreateForm] = useState(false);
    const [notification, setNotification] = useState({show: false, message: '', type: ''});
    const [newAnnounce, setNewAnnounce] = useState({
        publicationDate: getCurrentDateTime(),
        status: 'DRAFT',
        type: '',
        title: '',
        description: '',
        dateTimeStart: getCurrentDateTime(),
        duration: 0,
        dateTimeEnd: getCurrentDateTime(),
        isRecurrent: false
    });

    const showNotification = (message, type = 'success') => {
        setNotification({show: true, message, type});
        setTimeout(() => setNotification({show: false, message: '', type: ''}), 3000);
    };

    const resetNewAnnounce = () => {
        setNewAnnounce({
            publicationDate: getCurrentDateTime(),
            status: 'DRAFT',
            type: '',
            title: '',
            description: '',
            dateTimeStart: getCurrentDateTime(),
            duration: 0,
            dateTimeEnd: getCurrentDateTime(),
            isRecurrent: false
        });
    };

    const setAnnounceData = async () => {
        axios.get(GET_ANNOUNCES).then((response) => {
            console.log('Received announces:', response.data);
            setAnnounces(response.data || []);
        }).catch(error => {
            console.error('Error loading announces:', error);
            alert("Error occurred while loading data:" + error);
        });
    }

    const confirmRemoveAnnounce = (id) => {
        if (window.confirm('Are you sure you want to delete this announce?')) {
            removeAnnounce(id);
        }
    };

    const removeAnnounce = async (id) => {
        axios.delete(LOCAL_HOST_ANNOUNCE + id).then((response) => {
            console.log('Deleted announce:', response.data);
            showNotification('Announce successfully deleted');
            setAnnounceData();
        }).catch(error => {
            console.error('Error removing announce:', error);
            alert("Error occurred in removeAnnounce:" + error);
        });
    }

    const handleChangeField = (id, field, value) => {
        setAnnounces(prevData => prevData.map(
            row => row.idAnnounce === id ? {...row, [field]: value} : row)
        );
    }

    const updateAnnounce = async (announce) => {
        if (
            !announce.type?.trim() ||
            !announce.title?.trim() ||
            !announce.description?.trim()
        ) {
            alert('Please ensure that type, title, and description are not empty or only spaces.');
            return;
        }

        const announceToUpdate = {
            ...announce,
            type: announce.type.trim(),
            title: announce.title.trim(),
            description: announce.description.trim(),
        };

        console.log('Updating announce:', announceToUpdate);

        try {
            const response = await axios.post(UPDATE_ANNOUNCES, announceToUpdate);
            console.log('Updated announce:', response.data);
            showNotification('Announce successfully updated');
            setEditingId(null);
            setAnnounceData();
        } catch (error) {
            console.error('Error updating announce:', error);
            alert("Error occurred in updateAnnounce: " + error);
        }
    };


    const handleCancelEdit = () => {
        setEditingId(null);
        setAnnounceData();
    };


    const handleNewAnnounceSubmit = async (e) => {
        e.preventDefault();

        if (
            !newAnnounce.type?.trim() ||
            !newAnnounce.title?.trim() ||
            !newAnnounce.description?.trim()
        ) {
            alert('Please ensure that type, title, and description are not empty or only spaces.');
            return;
        }

        const announceToSubmit = {
            ...newAnnounce,
            status: newAnnounce.status || 'DRAFT',
            type: newAnnounce.type.trim(),
            title: newAnnounce.title.trim(),
            description: newAnnounce.description.trim(),
        };

        console.log('Creating new announce:', announceToSubmit);

        try {
            const response = await axios.post(ADD_ANNOUNCE, announceToSubmit);
            console.log('Created announce:', response.data);
            showNotification('Announce successfully created');
            setAnnounceData();
            resetNewAnnounce();
            setShowCreateForm(false);
        } catch (error) {
            console.error('Error creating announce:', error);
            alert("Error occurred while creating announce:" + error);
        }
    };


    useEffect(() => {
        setAnnounceData();
    }, []);

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


    const calculateEndDateTime = (startDate, duration) => {
        if (!startDate || duration == null) return '';

        const start = new Date(startDate);

        const end = new Date(start.getTime() + duration * 60 * 60 * 1000);

        const year = end.getFullYear();
        const month = String(end.getMonth() + 1).padStart(2, '0');
        const day = String(end.getDate()).padStart(2, '0');
        const hours = String(end.getHours()).padStart(2, '0');
        const minutes = String(end.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day}T${hours}:${minutes}`;
    };


    const handleTimeChange = (field, value) => {
        const updatedAnnounce = {...newAnnounce, [field]: value};

        if (field === 'dateTimeStart' || field === 'duration') {
            updatedAnnounce.dateTimeEnd = calculateEndDateTime(
                updatedAnnounce.dateTimeStart,
                updatedAnnounce.duration
            );
        }

        setNewAnnounce(updatedAnnounce);
    };


    const toLocalDateTime = (dateString) => {
        if (!dateString) return '';

        try {
            const [datePart, timePart] = dateString.split('T');
            const [year, month, day] = datePart.split('-');
            const [hours, minutes] = timePart.split(':');

            return `${year}-${month}-${day}T${hours}:${minutes}`;
        } catch (e) {
            console.error('Invalid date format:', dateString);
            return '';
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
            <td>
                <div className="btn-container">
                    <button
                        type="button"
                        className="btn btn-outline-primary"
                        onClick={() => setEditingId(announce.idAnnounce)}
                    >Edit
                    </button>
                    <button
                        type="button"
                        className="btn btn-outline-danger"
                        onClick={() => confirmRemoveAnnounce(announce.idAnnounce)}
                    >Delete
                    </button>
                </div>
            </td>
        </>
    );

    const renderEditRow = (announce) => (
        <>
            <td>
                <input
                    type="datetime-local"
                    className="form-control"
                    value={toLocalDateTime(announce.publicationDate)}
                    onChange={e => handleChangeField(announce.idAnnounce, 'publicationDate', e.target.value)}
                />
            </td>
            <td>
                <select
                    className="form-control"
                    value={announce.status || ''}
                    onChange={e => handleChangeField(announce.idAnnounce, 'status', e.target.value)}
                >
                    <option value="PUBLISHED">Publiée</option>
                    <option value="DRAFT">Brouillon</option>
                </select>
            </td>
            <td>
                <select
                    className="form-control"
                    value={announce.type || ''}
                    onChange={e => handleChangeField(announce.idAnnounce, 'type', e.target.value)}
                >
                    <option value="EVENT">Événement</option>
                    <option value="LOAN">Prêt</option>
                    <option value="SERVICE">Service</option>
                </select>
            </td>
            <td>
        <textarea
            className="form-control"
            value={announce.title || ''}
            onChange={e => handleChangeField(announce.idAnnounce, 'title', e.target.value)}
        />
            </td>
            <td>
        <textarea
            className="form-control"
            value={announce.description || ''}
            onChange={e => handleChangeField(announce.idAnnounce, 'description', e.target.value)}
        />
            </td>
            <td>
                <input
                    type="datetime-local"
                    className="form-control"
                    value={toLocalDateTime(announce.dateTimeStart)}
                    onChange={e => handleChangeField(announce.idAnnounce, 'dateTimeStart', e.target.value)}
                />
            </td>
            <td className="duration-cell">
                <div className="duration-input-group">
                    <input
                        type="number"
                        className="form-control"
                        value={announce.duration || 0}
                        onChange={e => handleChangeField(announce.idAnnounce, 'duration', parseFloat(e.target.value))}
                        step="0.5"
                        min="0"
                    />
                    <span className="duration-unit">h</span>
                </div>
            </td>
            <td>
                <input
                    type="datetime-local"
                    className="form-control"
                    value={toLocalDateTime(announce.dateTimeEnd)}
                    onChange={e => handleChangeField(announce.idAnnounce, 'dateTimeEnd', e.target.value)}
                />
            </td>
            <td className="text-center">
                <input
                    type="checkbox"
                    className="form-check-input"
                    checked={announce.isRecurrent || false}
                    onChange={e => handleChangeField(announce.idAnnounce, 'isRecurrent', e.target.checked)}
                />
            </td>
            <td>
                <div className="btn-container">
                    <button
                        type="button"
                        className="btn btn-outline-success"
                        onClick={() => updateAnnounce(announce)}
                    >Save
                    </button>
                    <button
                        type="button"
                        className="btn btn-outline-secondary"
                        onClick={handleCancelEdit}
                    >Cancel
                    </button>
                </div>
            </td>
        </>
    );

    const renderCreateForm = () => (
        <div className="section">
            <div className="create-form-header">
                <h4 className="section-title">Create New Announce</h4>
                <button
                    type="button"
                    className="btn btn-outline-secondary"
                    onClick={() => {
                        setShowCreateForm(false);
                        resetNewAnnounce();
                    }}
                >
                    Cancel
                </button>
            </div>
            <form onSubmit={handleNewAnnounceSubmit} className="new-announce-form">
                <div className="table-container">
                    <table className="table table-bordered">
                        <thead className="table-light">
                        <tr>
                            <th>Publication Date</th>
                            <th>Status</th>
                            <th>Type</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Start Date</th>
                            <th>Duration</th>
                            <th>End Date</th>
                            <th>Recurrent</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>
                                <input
                                    type="datetime-local"
                                    className="form-control"
                                    value={newAnnounce.publicationDate}
                                    onChange={e => setNewAnnounce({...newAnnounce, publicationDate: e.target.value})}
                                />
                            </td>
                            <td>
                                <select
                                    className="form-control"
                                    value={newAnnounce.status}
                                    onChange={e => setNewAnnounce({...newAnnounce, status: e.target.value})}
                                >
                                    <option value="PUBLISHED">Publiée</option>
                                    <option value="DRAFT">Brouillon</option>
                                </select>
                            </td>
                            <td>
                                <select
                                    className="form-control"
                                    value={newAnnounce.type}
                                    onChange={e => setNewAnnounce({...newAnnounce, type: e.target.value})}
                                >
                                    <option value="EVENT">Événement</option>
                                    <option value="LOAN">Prêt</option>
                                    <option value="SERVICE">Service</option>
                                </select>
                            </td>
                            <td>
                  <textarea
                      className="form-control"
                      value={newAnnounce.title}
                      onChange={e => setNewAnnounce({...newAnnounce, title: e.target.value})}
                      placeholder="Title"
                  />
                            </td>
                            <td>
                  <textarea
                      className="form-control"
                      value={newAnnounce.description}
                      onChange={e => setNewAnnounce({...newAnnounce, description: e.target.value})}
                      placeholder="Description"
                  />
                            </td>
                            <td>
                                <input
                                    type="datetime-local"
                                    className="form-control"
                                    value={newAnnounce.dateTimeStart}
                                    onChange={(e) => handleTimeChange('dateTimeStart', e.target.value)}
                                />
                            </td>
                            <td className="duration-cell">
                                <div className="duration-input-group">
                                    <input
                                        type="number"
                                        className="form-control"
                                        value={newAnnounce.duration}
                                        onChange={(e) => handleTimeChange('duration', parseFloat(e.target.value))}
                                        step="0.5"
                                        min="0"
                                    />
                                    <span className="duration-unit">h</span>
                                </div>
                            </td>
                            <td>
                                <input
                                    type="datetime-local"
                                    className="form-control"
                                    value={newAnnounce.dateTimeEnd}
                                    onChange={e => setNewAnnounce({...newAnnounce, dateTimeEnd: e.target.value})}
                                />
                            </td>
                            <td className="text-center">
                                <input
                                    type="checkbox"
                                    className="form-check-input"
                                    checked={newAnnounce.isRecurrent}
                                    onChange={e => setNewAnnounce({...newAnnounce, isRecurrent: e.target.checked})}
                                />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <button type="submit" className="btn btn-primary mt-3">Create Announce</button>
            </form>
        </div>
    );

    return (
        <div className="announce-container">
            {notification.show && (
                <div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>
            )}

            <div className="announce-content">
                {!showCreateForm && (
                    <div className="create-button-container">
                        <button
                            className="btn btn-primary btn-lg"
                            onClick={() => setShowCreateForm(true)}
                        >
                            Create New Announce
                        </button>
                    </div>
                )}

                {showCreateForm && renderCreateForm()}

                <div className="section">
                    <h4 className="section-title">Announces Listeeeee</h4>
                    {announces.length === 0 ? (
                        <div className="alert alert-info">No announces available</div>
                    ) : (
                        <div className="table-container">
                            <table className="table table-bordered table-hover">
                                <thead className="table-light">
                                <tr>
                                    <th onClick={() => handleSort('publicationDate')}>Publication Date</th>
                                    <th onClick={() => handleSort('status')}>Status</th>
                                    <th onClick={() => handleSort('type')}>Type</th>
                                    <th onClick={() => handleSort('title')}>Title</th>
                                    <th onClick={() => handleSort('description')}>Description</th>
                                    <th onClick={() => handleSort('dateTimeStart')}>Start Date</th>
                                    <th onClick={() => handleSort('duration')}>Duration</th>
                                    <th onClick={() => handleSort('dateTimeEnd')}>End Date</th>
                                    <th>Recurrent</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                {sortedAnnounces.map((announce, index) => (
                                    <tr key={index}>
                                        {editingId === announce.idAnnounce
                                            ? renderEditRow(announce)
                                            : renderReadOnlyRow(announce)}
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
