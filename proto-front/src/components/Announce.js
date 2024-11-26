import React, { useEffect, useState } from 'react';
import axios from "axios";
import '../styles/Announce.css';
import { GET_ANNOUNCES, LOCAL_HOST_ANNOUNCE, UPDATE_ANNOUNCES, ADD_ANNOUNCE } from "../constants/back";

export default function Announce() {
  const getCurrentDateTime = () => {
    const now = new Date();
      // Récupération des composants de la date et de l'heure en fonction du fuseau horaire local
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    
    // Retourne la date et l'heure au format attendu pour un champ datetime-local
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };

  const [announces, setAnnounces] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const [notification, setNotification] = useState({ show: false, message: '', type: '' });
  const [newAnnounce, setNewAnnounce] = useState({
    publicationDate: getCurrentDateTime(),
    status: 'online',
    type: '',
    title: '',
    description: '',
    dateTimeStart: getCurrentDateTime(),
    duration: 0,
    dateTimeEnd: getCurrentDateTime(),
    isRecurrent: false
  });

  const showNotification = (message, type = 'success') => {
    setNotification({ show: true, message, type });
    setTimeout(() => setNotification({ show: false, message: '', type: '' }), 3000);
  };

  const resetNewAnnounce = () => {
    setNewAnnounce({
      publicationDate: getCurrentDateTime(),
      status: '',
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
      row => row.idAnnounce === id ? { ...row, [field]: value } : row)
    );
  }

  const updateAnnounce = async (announce) => {
    console.log('Updating announce:', announce);
    axios.post(UPDATE_ANNOUNCES, announce).then((response) => {
      console.log('Updated announce:', response.data);
      showNotification('Announc successfully updated');
      setEditingId(null);
      setAnnounceData();
    }).catch(error => {
      console.error('Error updating announce:', error);
      alert("Error occurred in updateAnnounce:" + error);
    });
  }

  const handleCancelEdit = () => {
    setEditingId(null);
    setAnnounceData(); // Recharge les données originales depuis l'API
  };
  

  const handleNewAnnounceSubmit = async (e) => {
    e.preventDefault();
    if (!newAnnounce.title || !newAnnounce.description) {
      alert('Please fill in at least title and description');
      return;
    }
    
    console.log('Creating new announce:', newAnnounce);
    axios.post(ADD_ANNOUNCE, newAnnounce).then((response) => {
      console.log('Created announce:', response.data);
      showNotification('Announce successfully created');
      setAnnounceData();
      resetNewAnnounce();
      setShowCreateForm(false);
    }).catch(error => {
      console.error('Error creating announce:', error);
      alert("Error occurred while creating announce:" + error);
    });
  }

  useEffect(() => {
    setAnnounceData();
  }, []);

  const formatDateTime = (dateString) => {
    if (!dateString) return 'Invalid date';
    
    try {
      // Utilisation explicite pour extraire les composantes de la date
      const [datePart, timePart] = dateString.split('T');
      const [year, month, day] = datePart.split('-').map(Number);
      const [hours, minutes] = timePart.split(':').map(Number);
      
      // Création d'une date locale sans décalage UTC
      const date = new Date(year, month - 1, day, hours, minutes);
  
      // Formatage de la date avec Intl
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
    
    // Créer une nouvelle date à partir de la date de départ (en heure locale)
    const start = new Date(startDate);
    
    // Ajouter la durée en heures
    const end = new Date(start.getTime() + duration * 60 * 60 * 1000); // Durée en millisecondes
    
    // Formater la date de fin au format 'YYYY-MM-DDTHH:mm' pour le champ datetime-local
    const year = end.getFullYear();
    const month = String(end.getMonth() + 1).padStart(2, '0');
    const day = String(end.getDate()).padStart(2, '0');
    const hours = String(end.getHours()).padStart(2, '0');
    const minutes = String(end.getMinutes()).padStart(2, '0');
    
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };
  
  

  const handleTimeChange = (field, value) => {
    const updatedAnnounce = { ...newAnnounce, [field]: value };
  
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
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hours}:${minutes}`;
  };
  

  const formatDuration = (duration) => {
    if (isNaN(duration) || duration <= 0) return "0 h 0 min";
    
    const hours = Math.floor(duration);
    const minutes = Math.round((duration - hours) * 60);
    if (minutes < 1){
      return `${hours} h`;
    }
    else{
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
          >Edit</button>
          <button
            type="button"
            className="btn btn-outline-danger"
            onClick={() => confirmRemoveAnnounce(announce.idAnnounce)}
          >Delete</button>
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
          type="text"
          className="form-control"
          value={announce.status || ''}
          onChange={e => handleChangeField(announce.idAnnounce, 'status', e.target.value)}
          >
          <option value="online">Online</option>
          <option value="offline">Offline</option>
      </select>
      </td>
      <td>
        <input
          type="text"
          className="form-control"
          value={announce.type || ''}
          onChange={e => handleChangeField(announce.idAnnounce, 'type', e.target.value)}
        />
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
          >Save</button>
          <button
            type="button"
            className="btn btn-outline-secondary"
            onClick={handleCancelEdit}
          >Cancel</button>
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
                    type="text"
                    className="form-control"
                    value={newAnnounce.status}
                    onChange={e => setNewAnnounce({...newAnnounce, status: e.target.value})}
                    placeholder="Status"
                    >
                    <option value="online">Online</option>
                    <option value="offline">Offline</option>
                  </select>
                </td>
                <td>
                  <input
                    type="text"
                    className="form-control"
                    value={newAnnounce.type}
                    onChange={e => setNewAnnounce({...newAnnounce, type: e.target.value})}
                    placeholder="Type"
                  />
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
          <h4 className="section-title">Announces List</h4>
          {announces.length === 0 ? (
            <div className="alert alert-info">No announces available</div>
          ) : (
            <div className="table-container">
              <table className="table table-bordered table-hover">
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
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {announces.map((announce, index) => (
                    <tr key={index}>
                      {editingId === announce.idAnnounce ? 
                        renderEditRow(announce) : 
                        renderReadOnlyRow(announce)}
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
