import React, {useEffect, useState} from 'react';
import '../styles/OverlayLocation.css';

const OverlayLocation = ({show, onClose, announces, totalPages}) => {
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        const handleKeyDown = (event) => {
            if (event.key === 'Escape') {
                onClose();
            }
        };

        document.addEventListener('keydown', handleKeyDown);
        return () => {
            document.removeEventListener('keydown', handleKeyDown);
        };
        return null;
    }, [onClose]);

    if (!show) {
        return null;
    }

    const mapType = (type) => {
        const typeMapping = {
            'EVENT': 'évènement',
            'LOAN': 'prêt',
            'SERVICE': 'service',
        };
        return typeMapping[type] || type;
    };

    const formatDate = (date) => {
        const options = {year: 'numeric', month: 'numeric', day: 'numeric'};
        return new Date(date).toLocaleDateString(undefined, options);
    };

    const formatTime = (date) => {
        const options = {hour: '2-digit', minute: '2-digit'};
        return new Date(date).toLocaleTimeString(undefined, options);
    };

    const formatDuration = (duration) => {
        const totalMinutes = Math.round(duration * 60);
        const hours = Math.floor(totalMinutes / 60);
        const minutes = totalMinutes % 60;
        if(hours === 0) {
            return `${minutes} min`;
        }
        if (minutes === 0) {
            return `${hours} h`;
        }
        return `${hours}h ${minutes}min`;
    }

    const handleNextPage = () => {
        setCurrentPage(currentPage + 1);
    };

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

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

export default OverlayLocation;