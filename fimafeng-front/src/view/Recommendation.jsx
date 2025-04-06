import React, {useEffect, useState} from 'react';
import '../styles/Recommendation.css';
import axios from "axios";
import {GET_RECOMMENDATIONS} from "../api/constants/back";

export default function Recommendation() {

    const [notification] = useState({show: false, message: '', type: ''});
    const [recommendedAnnounces, setRecommendedAnnounces] = useState([]);

    useEffect(() => {
        // setRecommendationsData();
    }, []);

    // https://stackoverflow.com/questions/3547035/getting-html-form-values
    const handleGenerateRecommendations = async (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const id = parseInt(formData.get("id"));
        const amount = parseInt(formData.get("amount"));
        const action = formData.get("action");
        // if Non-Assigned
        if (isNaN(id) || isNaN(amount)) {
            alert("Please enter a valid input");
            return;
        }
        if (action === null) {
            console.log(GET_RECOMMENDATIONS + "?id=" + id + "&amount=" + amount);
            try {
                const response = await axios.get(`${GET_RECOMMENDATIONS}?id=${id}&amount=${amount}`);
                console.log('result=', response.data);
                setRecommendedAnnounces(response.data);
            } catch (error) {
                console.error("Error generating recommendations: ", error);
            }
        }
        // else if (action === "show") {
        //     console.log(SHOW_RECOMMENDATIONS + "?id=" + id + "&amount=" + amount);
        //     try {
        //         await axios.get(`${SHOW_RECOMMENDATIONS}?id=${id}&amount=${amount}`);
        //     } catch (error) {
        //         console.error("Error generating recommendations: ", error);
        //     }
        // }
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

    const renderReadOnlyRow_recommendedAnnounce = (recommendedAnnounce) => (
        <>
            <td style={{textAlign: "center"}}>{recommendedAnnounce.id}</td>
            <td style={{textAlign: "center"}}>{recommendedAnnounce.title}</td>
            <td style={{textAlign: "center"}}>{formatDateTime(recommendedAnnounce.publicationDate)}</td>
        </>
    );

    return (
        <div className="page">
            <div className="selection-form">
                <form onSubmit={handleGenerateRecommendations}>
                    Numéro du client :
                    <input type="number" name="id" min="1" required/>
                    Nombres de recommandations :
                    <input type="number" name="amount" min="1" max="10" required/>
                    {/*Montrer les calculs :*/}
                    {/*<input type="checkbox" name="action" value="show"/>*/}
                    <button type="submit">Calculer les recommandations</button>
                </form>
            </div>
            <br/>
            <div className="recommendations">
                {notification.show && (<div className={`alert alert-${notification.type} notification-popup`}>
                    {notification.message}
                </div>)}
                <div className="announce-profiles">
                    <h4 className="section-title-left">Annonces recommandées</h4>
                    {recommendedAnnounces.length === 0 ? (
                        <div className="alert alert-info">Aucune annonce n'a été récupérée.</div>) : (
                        <div className="table-container">
                            <table className="table table-bordered table-hover">
                                <thead className="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Titre</th>
                                    <th>Date de publication</th>
                                </tr>
                                </thead>
                                <tbody>
                                {recommendedAnnounces.map((recommendedAnnounce, index) => (
                                    <tr key={index}>
                                        {renderReadOnlyRow_recommendedAnnounce(recommendedAnnounce)}
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>)}
                </div>
            </div>
        </div>
    );
}
