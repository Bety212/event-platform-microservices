import api from "./api";

export const getEventById = (eventId) => {
  return api.get(`/event-service/events/${eventId}`);
};
