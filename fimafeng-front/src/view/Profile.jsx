import React, {useEffect, useState} from 'react';
import axios from "axios";
import '../styles/Profile.css';
import {GET_ANNOUNCES_PROFILES, GET_CLIENTS_PROFILES} from "../constants/back";

export default function Profile() {

    const [clientProfiles, setClientProfiles] = useState([]);
    const [announceProfiles, setAnnounceProfiles] = useState([]);
    const [notification] = useState({show: false, message: '', type: ''});
    const [sortConfig, setSortConfig] = useState({key: 'id', direction: 'desc'});


    const setClientProfilesData = async () => {
        axios.get(GET_CLIENTS_PROFILES).then((response) => {
            console.log('Received client profiles:', response.data);
            setClientProfiles(response.data || []);
        }).catch(error => {
            console.error('Error loading clients profiles:', error);
            alert("Error occurred while loading data:" + error);
        });
    }

    const setAnnounceProfilesData = async () => {
        axios.get(GET_ANNOUNCES_PROFILES).then((response) => {
            console.log('Received announces profiles:', response.data);
            setAnnounceProfiles(response.data || []);
        }).catch(error => {
            console.error('Error loading announces profiles:', error);
            alert("Error occurred while loading data:" + error);
        });
    }

    useEffect(() => {
        setAnnounceProfilesData();
        setClientProfilesData();
    }, []);

    const handleSort = (key) => {
        setSortConfig((prev) => {
            const isSameKey = prev.key === key;
            const direction = isSameKey && prev.direction === 'asc' ? 'desc' : 'asc';
            return {key, direction};
        });
    };

    const sorted_clientProfiles = [...clientProfiles].sort((a, b) => {
        if (!sortConfig.key) return 0;

        const key = sortConfig.key;
        const order = sortConfig.direction === 'asc' ? 1 : -1;

        if (a[key] < b[key]) return -1 * order;
        if (a[key] > b[key]) return 1 * order;
        return 0;
    });

    const sorted_announceProfiles = [...announceProfiles].sort((a, b) => {
        if (!sortConfig.key) return 0;

        const key = sortConfig.key;
        const order = sortConfig.direction === 'asc' ? 1 : -1;

        if (a[key] < b[key]) return -1 * order;
        if (a[key] > b[key]) return 1 * order;
        return 0;
    });

    const renderReadOnlyRow_clientProfile = (clientProfile) => (<>
        <td style={{textAlign: "center"}}>{clientProfile.id}</td>
        <td style={{textAlign: "center"}}>{clientProfile.districtId}</td>
        {/* Got inspired by https://stackoverflow.com/questions/55829210/how-to-display-array-data-into-tables-in-reactjs to display a list of Integer separated by a char*/}
        <td style={{textAlign: "center"}}>
            {Array.isArray(clientProfile.tagIds) ? clientProfile.tagIds.join(" - ") : clientProfile.tagIds}
        </td>
        <td style={{textAlign: "center"}}>
            {Array.isArray(clientProfile.consultationIds) ? clientProfile.consultationIds.join(" - ") : clientProfile.consultationIds}
        </td>
    </>);

    const renderReadOnlyRow_announceProfile = (announceProfile) => (<>
        <td style={{textAlign: "center"}}>{announceProfile.id}</td>
        <td style={{textAlign: "center"}}>{announceProfile.districtId}</td>
        {/* Got inspired by https://stackoverflow.com/questions/55829210/how-to-display-array-data-into-tables-in-reactjs to display a list of Integer separated by a char*/}
        <td style={{textAlign: "center"}}>
            {Array.isArray(announceProfile.tagIds) ? announceProfile.tagIds.join(" - ") : announceProfile.tagIds}
        </td>
    </>);


    return (<div className="profile-container">
        {notification.show && (<div className={`alert alert-${notification.type} notification-popup`}>
            {notification.message}
        </div>)}
        <br/>
        <div className="announce-profile-content">
            <h4 className="section-title-left">Profils des annonces</h4>
            {clientProfiles.length === 0 ? (
                <div className="alert alert-info">Aucune annonce n'a été récupérée.</div>) : (
                <div className="table-container">
                    <table className="table table-bordered table-hover">
                        <thead className="table-light">
                        <tr>
                            <th onClick={() => handleSort('id')}>ID</th>
                            <th onClick={() => handleSort('districtId')}>District</th>
                            <th onClick={() => handleSort('tagIds')}>Tags</th>
                        </tr>
                        </thead>
                        <tbody>
                        {sorted_announceProfiles.map((announceProfile, index) => (<tr key={index}>
                            {renderReadOnlyRow_announceProfile(announceProfile)}
                        </tr>))}
                        </tbody>
                    </table>
                </div>)}
        </div>
        <br/>
        <div className="client-profile-content">
            <h4 className="section-title-left">Profils des clients</h4>
            {clientProfiles.length === 0 ? (
                <div className="alert alert-info">Aucun client n'a été récupéré.</div>) : (
                <div className="table-container">
                    <table className="table table-bordered table-hover">
                        <thead className="table-light">
                        <tr>
                            <th onClick={() => handleSort('id')}>ID</th>
                            <th onClick={() => handleSort('districtId')}>District</th>
                            <th onClick={() => handleSort('tagIds')}>Tags</th>
                            <th onClick={() => handleSort('consultationIds')}>Consultations</th>
                        </tr>
                        </thead>
                        <tbody>
                        {sorted_clientProfiles.map((clientProfile, index) => (<tr key={index}>
                            {renderReadOnlyRow_clientProfile(clientProfile)}
                        </tr>))}
                        </tbody>
                    </table>
                </div>)}
        </div>
    </div>);
}
