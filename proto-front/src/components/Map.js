import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, GeoJSON } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css'

const OSMMap = () => {
    // Stockage des données GeoJSON dans un état
    const [geoData, setGeoData] = useState([]);

    useEffect(() => {
        // Appel à l'API pour récupérer les données
        fetch('/api/osm-lines')
            .then(response => response.json())
            .then(data => {
                const parsedData = data.map(geojson => JSON.parse(geojson));
                setGeoData(parsedData); // Mettre à jour l'état
            })
            .catch(error => console.error('Erreur lors de la récupération des données:', error));
    }, []);

    const bounds = [
        [48.7000, 2.3000], // Coin inférieur gauche
        [48.9000, 2.6000]  // Coin supérieur droit
    ];

    return (
        <MapContainer
            center={[48.79520917100231, 2.447223069760298]} // Centré sur Créteil L'Échat
            zoom={17}
            minZoom={15}
            maxZoom={18}
            maxBounds={bounds}
            style={{ height: '100vh', width: '100%' }}
        >
            {/* Couche de fond OpenStreetMap */}
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution="© OpenStreetMap contributors"
            />

            {/* Ajouter des lignes GeoJSON */}
            {geoData.map((geojson, index) => (
                <GeoJSON key={index} data={geojson} style={{ color: 'blue' }} />
            ))}
        </MapContainer>
    );
};

export default OSMMap;
