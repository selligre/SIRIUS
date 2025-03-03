import React from 'react';

const OverlayAnnounce = ({
                             announces,
                             handleAnnounceClick,
                             fetchFilteredAnnounces,
                             searchKeyword,
                             currentPage,
                             setCurrentPage,
                             totalPages,
                             setShowOverlayAnnounce,
                             setSearchKeyword,
                             refLocationId,
                             setRefLocationId
                         }) => {

    function handleNextPage() {
        setCurrentPage(currentPage + 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, currentPage + 1);
    }

    function handlePreviousPage() {
        setCurrentPage(currentPage - 1);
        fetchFilteredAnnounces(searchKeyword, refLocationId, currentPage - 1);
    }

    return (
        <div className="overlay-announce">
            <div className="overlay-content">
                <button className="close-button" onClick={() => {
                    setShowOverlayAnnounce(false);
                    setSearchKeyword('');
                    setRefLocationId('')
                }}>X
                </button>
                <h2>Annonces</h2>
                {announces.map(announce => (
                    <table key={announce.id} className="announce-table" onClick={() => handleAnnounceClick(announce)}>
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
    );
};

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
    return `${hours} h ${minutes} min`;
}

export default OverlayAnnounce;