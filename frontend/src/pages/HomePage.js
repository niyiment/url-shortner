import React, { useState } from 'react';
import UrlForm from '../components/UrlForm';

const HomePage = () => {
  const [createdUrl, setCreatedUrl] = useState(null);
  const [copied, setCopied] = useState(false);

  const handleUrlCreated = (url) => {
    setCreatedUrl(url);
    setCopied(false);
  };

  const handleCopy = () => {
    navigator.clipboard.writeText(createdUrl.shortUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  return (
    <div className="home-page">
      <div className="hero-section">
        <h1>Transform Long URLs into Short Links</h1>
        <p>Create short, memorable links in seconds. Track clicks and manage your URLs easily.</p>
      </div>

      <UrlForm onUrlCreated={handleUrlCreated} />

      {createdUrl && (
        <div className="created-url-container">
          <h3>Your shortened URL:</h3>
          <div className="created-url-box">
            <a
              href={createdUrl.shortUrl}
              target="_blank"
              rel="noopener noreferrer"
            >
              {createdUrl.shortUrl}
            </a>
            <button
              className={`copy-button ${copied ? 'copied' : ''}`}
              onClick={handleCopy}
            >
              {copied ? 'Copied!' : 'Copy'}
            </button>
          </div>
        </div>
      )}

      <div className="features-section">
        <h2>Why Choose Our URL Shortener?</h2>
        <div className="features-grid">
          <div className="feature-card">
            <h3>Simple & Fast</h3>
            <p>Shorten URLs in seconds with just a few clicks.</p>
          </div>
          <div className="feature-card">
            <h3>Custom URLs</h3>
            <p>Create branded, memorable links with custom aliases.</p>
          </div>
          <div className="feature-card">
            <h3>Analytics</h3>
            <p>Track clicks and monitor the performance of your links.</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;