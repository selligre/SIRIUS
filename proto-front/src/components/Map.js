import React, { useEffect, useState, useRef } from 'react';
import { MapContainer, TileLayer, Marker, Polygon } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from './data/polygones.json';
import { GET_LOCATIONS } from '../constants/back';
import customPin from './PNG/broche-de-localisation.png'; // Adjust the path to your custom pin image

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const mapRef = useRef();

    useEffect(() => {
        fetch(GET_LOCATIONS)
            .then(response => response.json())
            .then(data => setLocations(data))
            .catch(error => console.error('Erreur lors de la récupération des locations:', error));
    }, []);

    const customIcon = L.icon({
        iconUrl: customPin,
        iconSize: [70, 70], // Adjust the size as needed
        iconAnchor: [35, 70], // Adjust the icon anchor point as needed
        popupAnchor: [0, -70] // Adjust the popup anchor point as needed
    });

    const handleMarkerClick = (lat, lng) => {
        const map = mapRef.current;
        if (map) {
            map.setView([lat, lng], 17);
        }
    };

    const colors = ['blue', 'red', 'green', 'purple', 'orange', 'yellow', 'pink', 'cyan', 'magenta', 'lime'];

    return (
        <MapContainer
            center={[48.79520917100231, 2.447223069760298]} // Centré sur Créteil L'Échat
            zoom={13}
            //minZoom={13}
            //maxZoom={18}
            //maxBounds={}
            style={{ height: '100vh', width: '100%' }}
            ref={mapRef}
        >
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution="© OpenStreetMap contributors"
            />

            {locations.map(location => (
                <Marker
                    key={location.location_id}
                    position={[location.latitude, location.longitude]}
                    icon={customIcon}
                    eventHandlers={{
                        click: () => handleMarkerClick(location.latitude, location.longitude),
                    }}
                />
            ))}

            {polygones.zones.map((zone, index) => (
                <Polygon
                    key={index}
                    positions={zone.coordinates}
                    color={colors[index % colors.length]}
                />
            ))}
        </MapContainer>
    );
};

export default OSMMap;