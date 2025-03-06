import React, {useState} from 'react';

const SearchForm = ({
                        handleZoom,
                        searchKeyword,
                        handleSearchChange,
                        handleSearchSubmit,
                        handleClearSearch,
                        tags,
                        handleTagSelect,
                        selectedTags
                    }) => {
    const [showTagSelector, setShowTagSelector] = useState(false);

    const toggleTagSelector = () => {
        setShowTagSelector(!showTagSelector);
    };

    return (
        <div className="search-form-container">
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
                <button type="button" className="toggle-button" onClick={toggleTagSelector}>
                    {showTagSelector ? '▲' : '▼'}
                </button>
                <button type="button" className="zoom-button" onClick={handleZoom}>🔍</button>
                {showTagSelector && (
                    <div className="tag-selector">
                        {tags.map(tag => (
                            <div key={tag.id} className="tag-item">
                                <input
                                    type="checkbox"
                                    id={`tag-${tag.id}`}
                                    value={tag.id}
                                    checked={selectedTags.includes(tag.id)}
                                    onChange={handleTagSelect}
                                />
                                <label htmlFor={`tag-${tag.id}`}>{tag.name}</label>
                            </div>
                        ))}
                    </div>
                )}
            </form>
        </div>
    );
};

export default SearchForm;