import React, {useEffect, useState, useRef} from 'react';
import {MapContainer, TileLayer, Marker, Polygon, useMap} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from './data/polygones.json';
import deli from './data/deli.json';
import {GET_LOCATIONS, GET_COUNT, GET_COUNTDIS, GET_ANNOUNCES_SEARCH} from '../constants/back';
import customPin from './PNG/broche-de-localisation.png';

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const [refLocationId, setRefLocationId] = useState('');
    const [counts, setCounts] = useState([]);
    const [countsDis, setCountsDIs] = useState([]);
    const [zoomLevel, setZoomLevel] = useState(14);
    const [announces, setAnnounces] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [showOverlay, setShowOverlay] = useState(false);
    const [searchKeyword, setSearchKeyword] = useState('');
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

    const fetchFilteredAnnounces = (keyword, refLocationId, currentPage, size = 10) => {
        const url = `${GET_ANNOUNCES_SEARCH}?keyword=${keyword}&page=${currentPage - 1}&size=${size}&sortBy=publicationDate&sortDirection=desc&refLocationId=${refLocationId}`;
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
        iconAnchor: [25, 50],
        popupAnchor: [0, -50]
    });

    const handleMarkerClick = (lat, lng, locationId) => {
        const map = mapRef.current;
        if (map) {
            setCurrentPage(1);
            map.setView([lat, lng], 18);
            fetchFilteredAnnounces('', locationId, 1);
            setRefLocationId(locationId)
            setShowOverlay(true);
        }
    };

    const handleSearchChange = (event) => {
        setSearchKeyword(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        setCurrentPage(1);
        setRefLocationId('');
        fetchFilteredAnnounces(searchKeyword,'', 1);
        setShowOverlay(true);
    };

    const handleClearSearch = () => {
        setSearchKeyword('');
        setAnnounces([]);
        setCurrentPage(1);
        setRefLocationId('');
        setShowOverlay(false);
    };

    const handleZoom = () => {
        const map = mapRef.current;
        if (map) {
            map.setZoom(13);
        }
    }

    const MapEventHandler = () => {
        const map = useMap();
        useEffect(() => {
            const onZoomEnd = () => {
                setZoomLevel(map.getZoom());
            };
            const onClick = () => {
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

    const locationsSearch = locations.filter(location =>
        announces.some(announce => announce.refLocationId === location.idLocation)
    );

    return (
        <>
            <form onSubmit={handleSearchSubmit} className="search-form">
                <input
                    type="text"
                    value={searchKeyword}
                    onChange={handleSearchChange}
                    placeholder="Rechercher une annonce..."
                    className="search-input"
                />
                <button type="submit" className="search-button">Recherche</button>
                <button type="button" className="clear-button" onClick={handleClearSearch}>Effacer</button>
            </form>

            <button type="button" className="zoom-button" onClick={handleZoom}>🔍</button>

            <MapContainer
                center={[48.7856883564271, 2.4577914299514134]}
                zoom={13}
                minZoom={13}
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

                {zoomLevel >= 13 && polygones.zones.map((zone, index) => {
                    const count = countsDis.find(c => c.district === zone.id)?.count || 0;
                    const opacity = getOpacity(count);
                    const fillOpacity = zoomLevel > 14 ? 0 : opacity;
                    return (
                        <React.Fragment key={index}>
                            <Polygon
                                key={index}
                                positions={zone.coordinates}
                                pathOptions={{
                                    color: 'black',
                                    fillColor: 'magenta',
                                    fillOpacity: fillOpacity,
                                }}
                            />
                        </React.Fragment>
                    );
                })}

                {zoomLevel >= 15 && locationsWithAnnounces.map(location => (
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

                {locationsSearch.map(location => {
                    return location.latitude && location.longitude ? (
                        <Marker
                            key={location.idLocation}
                            position={[location.latitude, location.longitude]}
                            icon={customIcon}
                            eventHandlers={{
                                click: () => handleMarkerClick(location.latitude, location.longitude, location.idLocation),
                            }}
                        />
                    ) : (
                        console.warn('Invalid location:', location) // Log invalid locations
                    );
                })}
            </MapContainer>

            {showOverlay && (
                <div className="overlay">
                    <div className="overlay-content">
                        <button className="close-button" onClick={() => setShowOverlay(false)}>X</button>
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
                                    <td>{mapType(announce.type)}</td>
                                </tr>
                                <tr>
                                    <td>Débute</td>
                                    <td>Le {formatDate(announce.dateTimeStart)} à {formatTime(announce.dateTimeStart)}</td>
                                </tr>
                                <tr>
                                    <td>Fini</td>
                                    <td>Le {formatDate(announce.dateTimeEnd)} à {formatTime(announce.dateTimeEnd)}</td>
                                </tr>
                                <tr>
                                    <td>Durée</td>
                                    <td>{formatDuration(announce.duration)}</td>
                                </tr>
                                </tbody>
                            </table>
                        ))}
                        <div className="pagination">
                            <button onClick={handlePreviousPage} disabled={currentPage === 1}>&lt;</button>
                            <span>Page {currentPage} sur {totalPages}</span>
                            <button onClick={handleNextPage} disabled={currentPage === totalPages}>&gt;</button>
                        </div>
                    </div>
                </div>
            )}
        </>
    );

    function mapType(type) {
        const typeMapping = {
            'EVENT': 'évènement',
            'LOAN': 'prêt',
            'SERVICE': 'service',
        };
        return typeMapping[type] || type;
    }

    function formatDate(date) {
        const options = {year: 'numeric', month: 'numeric', day: 'numeric'};
        return new Date(date).toLocaleDateString(undefined, options);
    }

    function formatTime(date) {
        const options = {hour: '2-digit', minute: '2-digit'};
        return new Date(date).toLocaleTimeString(undefined, options);
    }

    function formatDuration(duration) {
        const totalMinutes = Math.round(duration * 60);
        const hours = Math.floor(totalMinutes / 60);
        const minutes = totalMinutes % 60;
        if (hours === 0) {
            return `${minutes} min`;
        }
        if (minutes === 0) {
            return `${hours} h`;
        }
        return `${hours}h ${minutes}min`;
    }

    function handleNextPage() {
        setCurrentPage(currentPage + 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, currentPage + 1);
    }

    function handlePreviousPage() {
        setCurrentPage(currentPage - 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, currentPage - 1);
    }
};

export default OSMMap;