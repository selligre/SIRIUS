import 'bootstrap/dist/css/bootstrap.min.css';
import '../styles/App.css';
import {useEffect} from "react";

function App() {
  useEffect(() => {
    document.title = 'Accueil';
  }, []);
  return (
    <div className="App">

    </div>
  );
}

export default App;
