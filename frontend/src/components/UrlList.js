import React from 'react';
import UrlItem from './UrlItem';
import LoadingSpinner from './LoadingSpinner';

const UrlList = ({ urls, loading, error, onDelete }) => {
  if (loading) {
    return <LoadingSpinner />;
  }

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (!urls || urls.length === 0) {
    return <div className="no-urls-message">No URLs found</div>;
  }

  return (
    <div className="url-list-container">
      <h2>Your URLs</h2>
      <div className="url-list">
        {urls.map((url) => (
          <UrlItem key={url.shortUrl} url={url} onDelete={onDelete} />
        ))}
      </div>
    </div>
  );
};

export default UrlList;