import React from 'react';

const ZoomButton = ({handleZoom}) => (
    <button type="button" className="zoom-button" onClick={handleZoom}>🔍</button>
);

export default ZoomButton;