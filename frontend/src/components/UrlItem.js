import React, { useState } from 'react';

const UrlItem = ({ url, onDelete }) => {
  const [copied, setCopied] = useState(false);
  const [deleting, setDeleting] = useState(false);

  const handleCopy = () => {
    navigator.clipboard.writeText(url.shortUrl);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this URL?')) {
      setDeleting(true);
      await onDelete(url.shortUrl);
    }
  };

  // Format expiry date
  const formatDate = (dateString) => {
    if (!dateString) return 'Never';
    const date = new Date(dateString);
    return date.toLocaleDateString();
  };

  return (
    <div className="url-item">
      <div className="url-details">
        <div className="original-url" title={url.originalUrl}>
          {url.originalUrl.length > 50
            ? `${url.originalUrl.substring(0, 50)}...`
            : url.originalUrl}
        </div>

        <div className="short-url-container">
          <a
            href={url.shortUrl}
            target="_blank"
            rel="noopener noreferrer"
            className="short-url"
          >
            {url.shortUrl}
          </a>
          <button
            className={`copy-button ${copied ? 'copied' : ''}`}
            onClick={handleCopy}
          >
            {copied ? 'Copied!' : 'Copy'}
          </button>
        </div>

        <div className="url-meta">
                  <span className="url-stat">
                    <strong>Created:</strong> {new Date(url.createdAt).toLocaleDateString()}
                  </span>
                  <span className="url-stat">
                    <strong>Expires:</strong> {formatDate(url.expiresAt)}
                  </span>
                  <span className="url-stat">
                    <strong>Clicks:</strong> {url.accessCount}
                  </span>
                </div>
              </div>

              <div className="url-actions">
                <button
                  className="delete-button"
                  onClick={handleDelete}
                  disabled={deleting}
                >
                  {deleting ? 'Deleting...' : 'Delete'}
                </button>
              </div>
            </div>
          );
        };

        export default UrlItem;