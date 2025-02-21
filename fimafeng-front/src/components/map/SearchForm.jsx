import React from 'react';

const SearchForm = ({searchKeyword, handleSearchChange, handleSearchSubmit, handleClearSearch}) => (
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
);

export default SearchForm;