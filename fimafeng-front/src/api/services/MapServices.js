import {
    GET_ALL_TAGS,
    GET_ANNOUNCE_TAG_COUNT,
    GET_ANNOUNCES_SEARCH,
    GET_COUNT,
    GET_COUNTDIS,
    GET_LOCATIONS,
    GET_ANNOUNCE_TAGS
} from "../constants/back";

export const fetchFilteredAnnounces = (keyword, refLocationId, tagIds, currentPage, size = 10, setAnnounces, setTotalPages) => {
    const url = `${GET_ANNOUNCES_SEARCH}?keyword=${keyword}&page=${currentPage - 1}&size=${size}&sortBy=publication_date&sortDirection=desc&refLocationId=${refLocationId}&tagIds=${tagIds}&status=PUBLISHED`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            setAnnounces(data.content);
            setTotalPages(data.totalPages);
        })
        .catch(error => console.error('Error fetching filtered announces:', error));
};

export const fetchData = (setLocations, setCounts, setCountsDIs) => {
    fetch(GET_LOCATIONS)
        .then(response => response.json())
        .then(data => setLocations(data))
        .catch(error => console.error('Erreur lors de la récupération des locations:', error));

    fetch(GET_COUNT)
        .then(response => response.json())
        .then(data => setCounts(data))
        .catch(error => console.error('Erreur lors de la récupération des counts:', error));

    fetch(GET_COUNTDIS)
        .then(response => response.json())
        .then(data => setCountsDIs(data))
        .catch(error => console.error('Erreur lors de la récupération des counts:', error));
};

export const fetchAnnounceTagCount = (districtId, setTagCounts) => {
    const url = `${GET_ANNOUNCE_TAG_COUNT}/${districtId}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            setTagCounts(data);
        })
}

export const fetchAllTags = (setTags) => {
    fetch(GET_ALL_TAGS)
        .then(response => response.json())
        .then(data => setTags(data))
        .catch(error => console.error('Erreur lors de la récupération des locations:', error));
}

export const fetchAnnounceTags = (announceId, setAnnounceTags) => {
    const url = `${GET_ANNOUNCE_TAGS}/${announceId}`;
    fetch(url)
        .then(response => response.json())
        .then(data => setAnnounceTags(data))
        .catch(error => console.error('Erreur lors de la récupération des tags:', error));
};