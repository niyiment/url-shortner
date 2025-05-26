import React, { useEffect } from 'react';
import UrlForm from '../components/UrlForm';
import UrlList from '../components/UrlList';
import { useUrls } from '../hooks/useUrls';

const DashboardPage = () => {
  const {
    urls,
    loading,
    error,
    fetchUrls,
    deleteUrl,
    addUrl
  } = useUrls();

  useEffect(() => {
      fetchUrls();
  }, [fetchUrls]);

  const handleUrlCreated = (newUrl) => {
    addUrl(newUrl);
  };

  const handleDeleteUrl = async (shortUrl) => {
    await deleteUrl(shortUrl);
  };

  return (
    <div className="dashboard-page">
      <h1>URL Dashboard</h1>
      <div className="dashboard-content">
        <div className="dashboard-section">
          <UrlForm onUrlCreated={handleUrlCreated} />
        </div>

        <div className="dashboard-section">
          <UrlList
            urls={urls}
            loading={loading}
            error={error}
            onDelete={handleDeleteUrl}
          />
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;