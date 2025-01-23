import React, {useEffect, useState} from 'react';
import axios from "axios";
import '../styles/Announce.css';
import {GET_ANNOUNCES_PROFILES, GET_CLIENTS_PROFILES} from "../constants/back";

export default function Profile() {

    const [clientProfiles, setClientProfiles] = useState([]);
    const [announceProfiles, setAnnounceProfiles] = useState([]);
    const [notification] = useState({show: false, message: '', type: ''});

    const setClientProfilesData = async () => {
        axios.get(GET_CLIENTS_PROFILES).then((response) => {
            console.log('Received clients profiles:', response.data);
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
        setClientProfilesData();
        setAnnounceProfilesData();
    }, []);


    return (
        <div className="announce-profile-container">
            {notification.show && (
                <div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>
            )}
            <div className="announce-profile-content">
                <h4 className="section-title">Profils d'annonces</h4>
                <div>
                    [announce_id, district_id, announce_tag1, *announce_tag2*]
                    {announceProfiles.map((profile, index) => (
                        <tr key={index}>
                            [{profile.toString()}]
                        </tr>
                    ))}
                </div>
            </div>
            <div className="client-profile-content">
                <h4 className="section-title">Profils d'utilisateurs</h4>
                <div>
                    [client_id, district_id, client_tag1, *client_tag2*]
                    {clientProfiles.map((profile, index) => (
                        <tr key={index}>
                            [{profile.toString()}]
                        </tr>
                    ))}
                </div>
            </div>
        </div>
    );
}
