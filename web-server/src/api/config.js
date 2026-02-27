const config = {
  announceManagerServiceUrl: process.env.REACT_APP_ANNOUNCE_MANAGER_URL,
  searchServiceUrl: process.env.REACT_APP_SEARCH_URL,
  notificationsServiceUrl: process.env.REACT_APP_NOTIFICATIONS_URL,
  environment: process.env.MODE, // 'development', 'preprod' ou 'production'
};

export default config;