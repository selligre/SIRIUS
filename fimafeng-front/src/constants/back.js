export const LOCAL_HOST = 'http://localhost:8080';
export const LOCAL_HOST_ANNOUNCE = LOCAL_HOST + '/announce';
export const GET_ANNOUNCES = LOCAL_HOST_ANNOUNCE + '/all';
export const ADD_ANNOUNCE = LOCAL_HOST_ANNOUNCE + '/add';
export const UPDATE_ANNOUNCES = LOCAL_HOST_ANNOUNCE + '/update';
export const GET_ANNOUNCES_SEARCH = LOCAL_HOST_ANNOUNCE + '/search';

export const LOCAL_HOST_LOCATION = LOCAL_HOST + '/location';
export const GET_LOCATIONS = LOCAL_HOST_LOCATION + '/all';
export const GET_COUNT = LOCAL_HOST_LOCATION + '/count';
export const GET_COUNTDIS = LOCAL_HOST_LOCATION + '/countDis';

export const LOCAL_HOST_CLIENTS = LOCAL_HOST + '/client';
export const GET_CLIENTS = LOCAL_HOST_CLIENTS + '/all';

export const LOCAL_HOST_MODERATIONS = LOCAL_HOST + '/moderation';
export const GET_MODERATION = LOCAL_HOST_MODERATIONS + '/all';
export const UPDATE_MODERATION = LOCAL_HOST_MODERATIONS + '/update';

export const GET_CLIENTS_PROFILES = LOCAL_HOST_CLIENTS + '/profile';
export const GET_ANNOUNCES_PROFILES = LOCAL_HOST_ANNOUNCE + '/profile';