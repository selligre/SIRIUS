import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/App.css';
import {useEffect, useState} from "react";
import {FetchTagsCount, FetchTotalAnnounces, FetchTotalUsers} from "../api/services/AppServices";
import TagBarChart from '../components/app/TagBarChart';

function App() {
    const [totalElements, setTotalElements] = useState(0);
    const [totalElementsMod, setTotalElementsMod] = useState(0);
    const [totalUsers, setTotalUsers] = useState(0);
    const [tagsCount, setTagsCount] = useState([]);

    useEffect(() => {
        document.title = 'Accueil';
    }, []);

    useEffect(() => {
        FetchTotalAnnounces("PUBLISHED", setTotalElements);
        FetchTotalUsers(setTotalUsers);
        FetchTotalAnnounces("MODERATED", setTotalElementsMod);
        FetchTagsCount(setTagsCount);
        const interval = setInterval(() => FetchTotalAnnounces("PUBLISHED", setTotalElements), 30000);
        return () => clearInterval(interval);
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <h1>Bienvenue sur votre Ville Partagée</h1>
                <p>Nous avons actuellement {totalElements} annonces publiées et {totalUsers} utilisateurs.</p>
                <p>{totalElementsMod} annonces ont été modérées.</p>
            </header>
            <main>
                <TagBarChart tagsCount={tagsCount}/>
            </main>
        </div>
    );
}

export default App;