import {
    GET_ANNOUNCES_SEARCH,
    GET_CLIENTS_SEARCH,
    GET_ANNOUNCE_TAGS_COUNT
} from "../constants/back";

export const FetchTotalAnnounces = (status, setTotalElements) => {
    const url = `${GET_ANNOUNCES_SEARCH}?keyword=&refLocationId=&tagIds=&status=${status}&page=&size=1&sortBy=publication_date&sortDirection=desc`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            setTotalElements(data.totalElements);
        })
        .catch(error => console.error('Error fetching filtered announces:', error));
};

export const FetchTotalUsers = (setTotalUsers) => {
    const url = `${GET_CLIENTS_SEARCH}?page=0&size=1`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            setTotalUsers(data.totalElements);
        })
        .catch(error => console.error('Error fetching filtered announces:', error));
};

export const FetchTagsCount = (setTagsCount) => {
    fetch(GET_ANNOUNCE_TAGS_COUNT)
        .then(response => response.json())
        .then(data => {
            setTagsCount(data);
        })
        .catch(error => console.error('Error fetching tag counts:', error));
};