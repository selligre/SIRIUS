import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./App";
import Navbar from "../components/Navbar";
import Announce from './Announce';
import Map from './Map';
import Client from './Client';
import ClientAnnounce from "./ClientAnnounce";
import Profile from "./Profile";
import Moderation from './Moderation';
import ModerationHistory from "./ModerationHistory";


export default function Router() {
    return (
        <BrowserRouter>
            <div>
                <Navbar/>
                <Routes>
                    <Route path="/" element={<App />}/>
                    <Route path="/announce" element={<Announce />}/>
                    <Route path="/map" element={<Map />}/>
                    <Route path="/client" element={<Client />}/>
                    <Route path="/client/:clientId/announces" element={<ClientAnnounce />}/>
                    <Route path="/profiles" element={<Profile/>}/>
                    <Route path="/moderation" element={<Moderation />}/>
                    <Route path="/moderation/:announceId" element={<ModerationHistory/>}/>

                </Routes>
            </div>
        </BrowserRouter>
    );
};