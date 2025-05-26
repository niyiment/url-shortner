import axios from 'axios';

const API_BASE_URL = process.env.API_URL || 'http://localhost:8888/api';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// URL services
export const createUrl = async (urlData) => {
  const response = await api.post('/urls', urlData);
  return response.data;
};

export const getAllUrls = async () => {
  const response = await api.get('/urls');
  return response.data;
};

export const deleteUrl = async (shortUrl) => {
  await api.delete(`/urls/${shortUrl}`);
};


export default api;