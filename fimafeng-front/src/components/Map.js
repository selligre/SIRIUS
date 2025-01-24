import React, {useEffect, useRef, useState} from 'react';
import {MapContainer, Marker, Polygon, TileLayer, useMap} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from './data/polygones.json';
import deli from './data/deli.json';
import {GET_COUNT, GET_LOCATIONS} from '../constants/back';
import customPin from './PNG/broche-de-localisation.png'; // Adjust the path to your custom pin image

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const [counts, setCounts] = useState([]);
    const [zoomLevel, setZoomLevel] = useState(14);
    const mapRef = useRef();

    const fetchData = () => {
        fetch(GET_LOCATIONS)
            .then(response => response.json())
            .then(data => setLocations(data))
            .catch(error => console.error('Erreur lors de la récupération des locations:', error));

        fetch(GET_COUNT)
            .then(response => response.json())
            .then(data => setCounts(data))
            .catch(error => console.error('Erreur lors de la récupération des counts:', error));
    };

    useEffect(() => {
        fetchData();
        const interval = setInterval(fetchData, 1000);
        return () => clearInterval(interval);
    }, []);

    const customIcon = L.icon({
        iconUrl: customPin, iconSize: [50, 50], // Adjust the size as needed
        iconAnchor: [35, 70], // Adjust the icon anchor point as needed
        popupAnchor: [0, -70] // Adjust the popup anchor point as needed
    });

    const handleMarkerClick = (lat, lng) => {
        const map = mapRef.current;
        if (map) {
            map.setView([lat, lng], 18);
        }
    };

    // const colors = ['blue', 'red', 'green', 'purple', 'orange', 'yellow', 'pink', 'cyan', 'magenta', 'lime'];

    const MapEventHandler = () => {
        const map = useMap();
        useEffect(() => {
            const onZoomEnd = () => {
                setZoomLevel(map.getZoom());
            };
            const onClick = (e) => {
                console.log('Clicked at:', e.latlng);
            };
            map.on('zoomend', onZoomEnd);
            map.on('click', onClick);
            return () => {
                map.off('zoomend', onZoomEnd);
                map.off('click', onClick);
            };
        }, [map]);
        return null;
    };

    const calculateCenter = (coordinates) => {
        const latitudes = coordinates.map(coord => coord[0]);
        const longitudes = coordinates.map(coord => coord[1]);
        const latSum = latitudes.reduce((a, b) => a + b, 0);
        const lngSum = longitudes.reduce((a, b) => a + b, 0);
        return [latSum / latitudes.length, lngSum / longitudes.length];
    };

    const getOpacity = (count, minCount, maxCount) => {
        if (maxCount === minCount) return 0.9;
        const opacity = (count - minCount) / (maxCount - minCount);
        return Math.max(0.2, Math.min(0.9, opacity)); // Clamp between 0 and 1
    };

    useEffect(() => {
        if (counts.length > 0) {
            const minCount = Math.min(...counts.map(c => c.count));
            const maxCount = Math.max(...counts.map(c => c.count));
            console.log('Min Count:', minCount, 'Max Count:', maxCount);
        }
    }, [counts]);

    const minCount = counts.length > 0 ? Math.min(...counts.map(c => c.count)) : 0;
    const maxCount = counts.length > 0 ? Math.max(...counts.map(c => c.count)) : 1;

    return (
        <MapContainer
            center={[48.7856883564271, 2.4577914299514134]} // Centré sur Créteil L'Échat
            zoom={14}
            minZoom={14}
            maxZoom={18}
            maxBounds={deli.zone}
            style={{height: '100vh', width: '100%'}}
            ref={mapRef}
        >
            <TileLayer
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                attribution="© OpenStreetMap contributors"
            />

            <MapEventHandler/>

            {zoomLevel >= 14 && zoomLevel <= 17 && polygones.zones.map((zone, index) => {
                const center = calculateCenter(zone.coordinates);
                const count = counts.find(c => c.district === zone.id)?.count || 0;
                const opacity = getOpacity(count, minCount, maxCount);
                console.log('Zone:', zone.id, 'Count:', count, 'Opacity:', opacity);
                return (
                    <React.Fragment key={index}>
                        <Polygon
                            key={index}
                            positions={zone.coordinates}
                            pathOptions={{
                                // color: colors[index % colors.length],
                                color: 'grey',
                                fillColor: 'magenta',
                                fillOpacity: opacity,
                            }}
                        />

                        <Marker position={center} icon={L.divIcon({
                            className: 'count-marker',
                            html: `<div style=" font-size: 20px; font-weight: bold;">${count}</div>`
                        })}/>
                    </React.Fragment>
                );
            })}

            {zoomLevel >= 16 && locations.map(location => (
                <Marker
                    key={location.location_id}
                    position={[location.latitude, location.longitude]}
                    icon={customIcon}
                    eventHandlers={{
                        click: () => handleMarkerClick(location.latitude, location.longitude),
                    }}
                />
            ))}
        </MapContainer>
    );
};

export default OSMMap;