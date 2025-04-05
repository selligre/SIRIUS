import React, {useEffect, useState, useRef} from 'react';
import {MapContainer, TileLayer, Marker, Polygon, useMap} from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';
import '../styles/Map.css';
import polygones from '../components/data/polygones.json';
import deli from '../components/data/deli.json';
import {GET_LOCATIONS, GET_COUNT, GET_COUNTDIS, GET_ANNOUNCES_SEARCH, GET_ANNOUNCE_TAG_COUNT, GET_ALL_TAGS} from '../api/constants/back';
import customPin from '../components/PNG/broche-de-localisation.png';
import customPin2 from '../components/PNG/ezgif-2-711d1a5a58.gif';
import SearchForm from '../components/map/SearchForm';
import OverlayAnnounce from '../components/map/OverlayAnnounce';
import OverlayDistrict from '../components/map/OverlayDistrict';

const OSMMap = () => {
    const [locations, setLocations] = useState([]);
    const [selectedDistrict, setSelectedDistrict] = useState([]);
    const [refLocationId, setRefLocationId] = useState('');
    const [counts, setCounts] = useState([]);
    const [countsDis, setCountsDIs] = useState([]);
    const [tagCounts, setTagCounts] = useState([]);
    const [zoomLevel, setZoomLevel] = useState(14);
    const [announces, setAnnounces] = useState([]);
    const [tags, setTags] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [showOverlayAnnounce, setShowOverlayAnnounce] = useState(false);
    const [showOverlayDistrict, setShowOverlayDistrict] = useState(false);
    const [searchKeyword, setSearchKeyword] = useState('');
    const [selectedTags, setSelectedTags] = useState([]);
    const mapRef = useRef();

    useEffect(() => {
        document.title = 'Carte';
        document.body.classList.add('no-scroll');
        return () => {
            document.body.classList.remove('no-scroll');
        };
    }, []);

    useEffect(() => {
        fetch(GET_ALL_TAGS)
        .then(response => response.json())
        .then(data => setTags(data))
        .catch(error => console.error('Erreur lors de la récupération des locations:', error));
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

    const fetchFilteredAnnounces = (keyword, refLocationId, tagIds, currentPage, size = 10) => {
        const url = `${GET_ANNOUNCES_SEARCH}?keyword=${keyword}&page=${currentPage - 1}&size=${size}&sortBy=publication_date&sortDirection=desc&refLocationId=${refLocationId}&tagIds=${tagIds}`;
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

    const customIconSelected = L.icon({
        iconUrl: customPin2, iconSize: [60, 60],
        iconAnchor: [25, 50],
        popupAnchor: [0, -50]
    });

    const handleMarkerClick = (lat, lng, locationId, ref_district) => {
        const map = mapRef.current;
        if (map) {
            setCurrentPage(1);
            map.setMaxBounds(deli.zone);
            map.setView([lat, lng], 18);
            fetchFilteredAnnounces('', locationId, '', 1);
            handleDistrictClick(ref_district)
            setRefLocationId(locationId)
            setShowOverlayAnnounce(true);
        }
    };

    const handleAnnounceClick = (announce) => {
        const location = locations.find(loc => loc.idLocation === announce.refLocationId);
        if (location) {
            const map = mapRef.current;
            if (map) {
                map.setView([location.latitude, location.longitude], 18);
                handleDistrictClick(location.ref_district)
                setRefLocationId(announce.refLocationId);
            }
        }
    };

    const handleDistrictClick = (districtId) => {
        const district = polygones.zones.find(zone => zone.id === districtId);
        if (district) {
            const map = mapRef.current;
            if (map) {
                const margin = 0.001;
                const bounds = L.latLngBounds(district.coordinates);
                const southWest = bounds.getSouthWest();
                const northEast = bounds.getNorthEast();
                const adjustedBounds = L.latLngBounds(
                    [southWest.lat - margin, southWest.lng - margin],
                    [northEast.lat + margin, northEast.lng + margin]
                );
                setSelectedDistrict(district);
                const url = `${GET_ANNOUNCE_TAG_COUNT}/${districtId}`;
                fetch(url)
                    .then(response => response.json())
                    .then(data => {
                        setTagCounts(data);
                    })
                map.setMaxBounds(adjustedBounds);
                map.setZoom(15);
                setShowOverlayDistrict(true);
            }
        }
    }

    const handleSearchChange = (event) => {
        setSearchKeyword(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        setCurrentPage(1);
        setRefLocationId('');
        fetchFilteredAnnounces(searchKeyword, '', selectedTags, 1);
        setShowOverlayAnnounce(true);
    };

    const handleTagSelect = (event) => {
        const tagId = parseInt(event.target.value, 10);
        setSelectedTags(prevSelectedTags =>
            prevSelectedTags.includes(tagId)
                ? prevSelectedTags.filter(id => id !== tagId)
                : [...prevSelectedTags, tagId]
        );
    };

    const handleClearSearch = () => {
        setSearchKeyword('');
        setAnnounces([]);
        setCurrentPage(1);
        setRefLocationId('');
        mapRef.current.setMaxBounds(deli.zone);
        setSelectedDistrict([]);
        setShowOverlayDistrict(false)
        setShowOverlayAnnounce(false);
        setSelectedTags([]);
    };

    const handleZoom = () => {
        const map = mapRef.current;
        if (map) {
            map.setZoom(13);
        }
    }

    function handleNextPage() {
        setCurrentPage(currentPage + 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, '', currentPage + 1);
    }

    function handlePreviousPage() {
        setCurrentPage(currentPage - 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, '', currentPage - 1);
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
            <SearchForm
                handleZoom={handleZoom}
                searchKeyword={searchKeyword}
                handleSearchChange={handleSearchChange}
                handleSearchSubmit={handleSearchSubmit}
                handleClearSearch={handleClearSearch}
                tags={tags}
                handleTagSelect={handleTagSelect}
                selectedTags={selectedTags}
            />

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
                    const fillColor = zone.id === selectedDistrict.id ? 'blue' : 'magenta';
                    let fillOpacity;
                    if (selectedDistrict.id === zone.id) {
                        fillOpacity = 0.4;
                    } else if (zoomLevel > 14) {
                        fillOpacity = 0;
                    } else {
                        fillOpacity = opacity;
                    }
                    return (
                        <React.Fragment key={index}>
                            <Polygon
                                key={index}
                                positions={zone.coordinates}
                                pathOptions={{
                                    color: 'black',
                                    fillColor: fillColor,
                                    fillOpacity: fillOpacity,
                                }}
                                eventHandlers={{
                                    click: () => handleDistrictClick(zone.id),
                                }}
                            >
                            </Polygon>
                        </React.Fragment>
                    );
                })}

                {zoomLevel >= 15 && locationsWithAnnounces.map(location => (
                    <Marker
                        key={location.idLocation}
                        position={[location.latitude, location.longitude]}
                        icon={refLocationId === location.idLocation ? customIconSelected : customIcon}
                        eventHandlers={{
                            click: () => handleMarkerClick(location.latitude, location.longitude, location.idLocation, location.ref_district),
                        }}
                    >
                    </Marker>
                ))}

                {locationsSearch.map(location => {
                    return location.latitude && location.longitude ? (
                        <Marker
                            key={location.idLocation}
                            position={[location.latitude, location.longitude]}
                            icon={refLocationId === location.idLocation ? customIconSelected : customIcon}
                            eventHandlers={{
                                click: () => handleMarkerClick(location.latitude, location.longitude, location.idLocation, location.ref_district),
                            }}
                        />
                    ) : (
                        console.warn('Invalid location:', location) // Log invalid locations
                    );
                })}
            </MapContainer>

            {showOverlayDistrict && (
                <OverlayDistrict
                    selectedDistrict={selectedDistrict}
                    countsDis={countsDis}
                    tagCounts={tagCounts}
                    setShowOverlayDistrict={setShowOverlayDistrict}
                    setSelectedDistrict={setSelectedDistrict}
                    mapRef={mapRef}
                />
            )}

            {showOverlayAnnounce && (
                <OverlayAnnounce
                    announces={announces}
                    handleAnnounceClick={handleAnnounceClick}
                    handleNextPage = {handleNextPage}
                    handlePreviousPage = {handlePreviousPage}
                    currentPage={currentPage}
                    totalPages={totalPages}
                    setShowOverlayAnnounce={setShowOverlayAnnounce}
                    setSearchKeyword={setSearchKeyword}
                    setRefLocationId={setRefLocationId}
                />
            )}
        </>
    );
};

export default OSMMap;