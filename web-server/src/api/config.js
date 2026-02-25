const config = {
  announceManagerServiceUrl: process.env.REACT_APP_ANNOUNCE_MANAGER_URL,
  searchServiceUrl: process.env.REACT_APP_SEARCH_URL,
  recommandationServiceUrl: process.env.REACT_APP_RECOMMANDATION_URL,
  announceLifeEventServiceUrl: process.env.REACT_APP_ANNOUNCE_LIFE_EVENT_URL,
  environment: process.env.MODE, // 'development', 'preprod' ou 'production'
};

export default config;