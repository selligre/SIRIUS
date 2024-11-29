import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./App";
import Navbar from "./Navbar";
import Announce from './Announce';
import Map from './Map';

export default function Router () {
    return (
        <BrowserRouter>
            <div>
                <Navbar />
                <Routes>
                    <Route path="/" element={<App />}/>
                    <Route path="/announce" element={<Announce />}/>
                    <Route path="/map" element={<Map />}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
};