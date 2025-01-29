// Backend address
export const LOCAL_HOST = process.env.REACT_APP_BACKEND_URL;

// Announces address
export const LOCAL_HOST_ANNOUNCE = LOCAL_HOST + 'announce/';
export const GET_ANNOUNCES = LOCAL_HOST_ANNOUNCE + 'all';
export const ADD_ANNOUNCE = LOCAL_HOST_ANNOUNCE + 'add';
export const UPDATE_ANNOUNCES = LOCAL_HOST_ANNOUNCE + 'update';

// Locations address
export const LOCAL_HOST_LOCATION = LOCAL_HOST + 'location/';
export const GET_LOCATIONS = LOCAL_HOST_LOCATION + 'all';
export const ADD_LOCATION = LOCAL_HOST_LOCATION + 'add';
export const UPDATE_LOCATIONS = LOCAL_HOST_LOCATION + 'update';
export const GET_COUNT = LOCAL_HOST_LOCATION + 'countDis';
// Location profile
export const GET_ANNOUNCES_PROFILES = LOCAL_HOST_ANNOUNCE + 'profile';

// Clients address
export const LOCAL_HOST_CLIENTS = LOCAL_HOST + 'client/';
export const GET_CLIENTS = LOCAL_HOST_CLIENTS + 'all';
// Clients profile
export const GET_CLIENTS_PROFILES = LOCAL_HOST_CLIENTS + 'profile';

// Moderation address
export const LOCAL_HOST_MODERATION = LOCAL_HOST + 'moderation/';
export const GET_MODERATION = LOCAL_HOST_MODERATION + 'all';
export const UPDATE_MODERATION = LOCAL_HOST_MODERATION + 'update';

