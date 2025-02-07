// proto-front/src/components/Overlay.js
import React from 'react';
import '../styles/Overlay.css';

const Overlay = ({show, onClose, announces}) => {
    if (!show) {
        return null;
    }

    return (
        <div className="overlay">
            <div className="overlay-content">
                <button className="close-button" onClick={onClose}>X</button>
                <h2>Announces</h2>
                {announces.map(announce => (
                    <table key={announce.id} className="announce-table">
                        <thead>
                        <tr>
                            <th colSpan="2">{announce.title}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td colSpan="2">{announce.description}</td>
                        </tr>
                        <tr>
                            <td>Type</td>
                            <td>{announce.type}</td>
                        </tr>
                        <tr>
                            <td>Start</td>
                            <td>{new Date(announce.dateTimeStart).toLocaleString()}</td>
                        </tr>
                        <tr>
                            <td>End</td>
                            <td>{new Date(announce.dateTimeEnd).toLocaleString()}</td>
                        </tr>
                        <tr>
                            <td>Duration</td>
                            <td>{announce.duration} hours</td>
                        </tr>
                        </tbody>
                    </table>
                ))}
            </div>
        </div>
    );
};

export default Overlay;