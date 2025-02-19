import React, { useEffect, useState, useRef } from 'react';
import { MapContainer, TileLayer, Marker, Polygon, useMap } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from './data/polygones.json';
import deli from './data/deli.json';
import { GET_LOCATIONS, GET_COUNT, GET_COUNTDIS, GET_ANNOUNCES_FILTERED } from '../constants/back';
import customPin from './PNG/broche-de-localisation.png';
import OverlayLocation from './OverlayLocation'; // Import the OverlayLocation component

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const [counts, setCounts] = useState([]);
    const [countsDis, setCountsDIs] = useState([]);
    const [zoomLevel, setZoomLevel] = useState(14);
    const [announces, setAnnounces] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [showOverlay, setShowOverlay] = useState(false);
    const mapRef = useRef();


    useEffect(() => {
        document.body.classList.add('no-scroll');
        return () => {
            document.body.classList.remove('no-scroll');
        };
    }, []);

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
                setAnnounces(data.content);
                setTotalPages(data.totalPages);
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

    const getOpacity = (count) => {
        const minCount = countsDis.length > 0 ? Math.min(...countsDis.map(c => c.count)) : 0;
        const maxCount = countsDis.length > 0 ? Math.max(...countsDis.map(c => c.count)) : 1;
        if (maxCount === minCount) return 0.9;
        const opacity = (count - minCount) / (maxCount - minCount);
        return Math.max(0.2, Math.min(0.9, opacity));
    };

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
                    const count = countsDis.find(c => c.district === zone.id)?.count || 0;
                    const opacity = getOpacity(count);
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

            <OverlayLocation show={showOverlay} onClose={() => setShowOverlay(false)} announces={announces} totalPages={totalPages}>
            </OverlayLocation>
        </>
    );
};

export default OSMMap;