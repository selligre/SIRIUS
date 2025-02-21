import React from 'react';
import deli from '../data/deli.json';

const OverlayDistrict = ({selectedDistrict, countsDis, setShowOverlayDistrict, setSelectedDistrict, mapRef}) => (
    <div className="overlay-district">
        <div className="overlay-district-content">
            <button className="close-button" onClick={() => {
                setShowOverlayDistrict(false);
                setSelectedDistrict([]);
                mapRef.current.setMaxBounds(deli.zone)
            }}>X
            </button>
            <h2>Quartier</h2>
            <table className="announce-table">
                <thead>
                <tr>
                    <th colSpan="2">{selectedDistrict.name}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Nombre d'annonces</td>
                    <td>{countsDis.find(c => c.district === selectedDistrict.id)?.count || 0}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
);

export default OverlayDistrict;