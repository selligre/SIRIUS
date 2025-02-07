import React from 'react';
import {Link} from "react-router-dom";
import '../styles/Navbar.css';

export default function Navbar() {
    return (
        <ul className="nav justify-content-center my-3">
            <li className="nav-item">
                <Link className="nav-link" to="/">Accueil</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/announce">Announces</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/moderation">Modération</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/client">Clients</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/profile">Profils</Link>
            </li>
            <li className="nav-item">
                <Link className="nav-link" to="/map">Carte</Link>
            </li>
        </ul>
    );
};