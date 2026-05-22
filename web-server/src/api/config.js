const config = {
  announceManagerServiceUrl: process.env.REACT_APP_ANNOUNCE_MANAGER_URL,
  searchServiceUrl: process.env.REACT_APP_SEARCH_URL,
  notificationManagerServiceUrl: process.env.REACT_APP_NOTIFICATION_MANAGER_URL,
  notificationSenderUrl: process.env.REACT_APP_NOTIFICATION_SENDER_URL,
  environment: process.env.MODE, // 'development', 'preprod' ou 'production'
};

export default config;