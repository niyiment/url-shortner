import { useState, useCallback } from 'react';
import { getAllUrls, deleteUrl as apiDeleteUrl } from '../services/api';

export const useUrls = () => {
  const [urls, setUrls] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchUrls = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await getAllUrls();
      setUrls(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch URLs');
    } finally {
      setLoading(false);
    }
  }, []);

  const addUrl = useCallback((newUrl) => {
    setUrls(prevUrls => [newUrl, ...prevUrls]);
  }, []);

  const deleteUrl = useCallback(async (shortUrl) => {
    try {
      await apiDeleteUrl(shortUrl);
      setUrls(prevUrls => prevUrls.filter(url => url.shortUrl !== shortUrl));
      return true;
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete URL');
      return false;
    }
  }, []);

  return {
    urls,
    loading,
    error,
    fetchUrls,
    addUrl,
    deleteUrl
  };
};

