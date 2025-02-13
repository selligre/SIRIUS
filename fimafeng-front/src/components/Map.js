import React, { useEffect, useState, useRef } from 'react';
import { MapContainer, TileLayer, Marker, Polygon, useMap } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from './data/polygones.json';
import deli from './data/deli.json';
import { GET_LOCATIONS, GET_COUNT, GET_COUNTDIS, GET_ANNOUNCES_FILTERED } from '../constants/back';
import customPin from './PNG/broche-de-localisation.png';
import Overlay from './Overlay'; // Import the Overlay component

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const [counts, setCounts] = useState([]);
    const [countsDis, setCountsDIs] = useState([]);
    const [zoomLevel, setZoomLevel] = useState(14);
    const [announces, setAnnounces] = useState([]);
    const [showOverlay, setShowOverlay] = useState(false);
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

        fetch(GET_COUNTDIS)
            .then(response => response.json())
            .then(data => setCountsDIs(data))
            .catch(error => console.error('Erreur lors de la récupération des counts:', error));
    };

    useEffect(() => {
        fetchData();
        const interval = setInterval(fetchData, 100000);
        return () => clearInterval(interval);
    }, []);

    const fetchFilteredAnnounces = (refLocationId) => {
        const url = `${GET_ANNOUNCES_FILTERED}?page=0&size=10&sortBy=publicationDate&refLocationId=${refLocationId}`;
        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log('Filtered Announces:', data);
                setAnnounces(data.content);
            })
            .catch(error => console.error('Error fetching filtered announces:', error));
    };

    const customIcon = L.icon({
        iconUrl: customPin, iconSize: [50, 50],
        iconAnchor: [35, 70],
        popupAnchor: [0, -70]
    });

    const handleMarkerClick = (lat, lng, locationId) => {
        const map = mapRef.current;
        if (map) {
            map.setView([lat, lng], 18);
            fetchFilteredAnnounces(locationId);
            setShowOverlay(true);
            console.log('Marker clicked:', lat, lng, locationId);
        }
    };

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
        return Math.max(0.2, Math.min(0.9, opacity));
    };

    useEffect(() => {
        if (countsDis.length > 0) {
            const minCount = Math.min(...countsDis.map(c => c.count));
            const maxCount = Math.max(...countsDis.map(c => c.count));
            console.log('Min Count:', minCount, 'Max Count:', maxCount);
        }
    }, [countsDis]);

    const minCount = countsDis.length > 0 ? Math.min(...countsDis.map(c => c.count)) : 0;
    const maxCount = countsDis.length > 0 ? Math.max(...countsDis.map(c => c.count)) : 1;

    const locationsWithAnnounces = locations.filter(location =>
        counts.some(count => count.location === location.idLocation)
    );

    return (
        <>
            <MapContainer
                center={[48.7856883564271, 2.4577914299514134]}
                zoom={14}
                minZoom={14}
                maxZoom={18}
                maxBounds={deli.zone}
                style={{ height: '100vh', width: '100%' }}
                ref={mapRef}
            >
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution="© OpenStreetMap contributors"
                />

                <MapEventHandler />

                {zoomLevel >= 14 && zoomLevel <= 17 && polygones.zones.map((zone, index) => {
                    const center = calculateCenter(zone.coordinates);
                    const count = countsDis.find(c => c.district === zone.id)?.count || 0;
                    const opacity = getOpacity(count, minCount, maxCount);
                    console.log('Zone:', zone.id, 'Count:', count, 'Opacity:', opacity);
                    return (
                        <React.Fragment key={index}>
                            <Polygon
                                key={index}
                                positions={zone.coordinates}
                                pathOptions={{
                                    color: 'black',
                                    fillColor: 'magenta',
                                    fillOpacity: opacity,
                                }}
                            />

                            <Marker position={center} icon={L.divIcon({
                                className: 'count-marker',
                                html: `<div style=" font-size: 20px; font-weight: bold;">${count}</div>`
                            })} />
                        </React.Fragment>
                    );
                })}

                {zoomLevel >= 16 && locationsWithAnnounces.map(location => (
                    <Marker
                        key={location.idLocation}
                        position={[location.latitude, location.longitude]}
                        icon={customIcon}
                        eventHandlers={{
                            click: () => handleMarkerClick(location.latitude, location.longitude, location.idLocation),
                        }}
                    >
                    </Marker>
                ))}
            </MapContainer>

            <Overlay show={showOverlay} onClose={() => setShowOverlay(false)} announces={announces}>
            </Overlay>
        </>
    );
};

export default OSMMap;