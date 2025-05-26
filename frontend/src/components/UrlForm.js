import React, { useState } from 'react';
import { createUrl } from '../services/api';
import { isValidUrl } from '../utils/validators';

const UrlForm = ({ onUrlCreated }) => {
  const [originalUrl, setOriginalUrl] = useState('');
  const [customAlias, setCustomAlias] = useState('');
  const [expiryDays, setExpiryDays] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    // Validate URL
    if (!isValidUrl(originalUrl)) {
      setError('Please enter a valid URL');
      return;
    }

    try {
      setLoading(true);

      // Calculate expiry date if set
      let expiresAt = null;
      if (expiryDays && !isNaN(expiryDays) && expiryDays > 0) {
        expiresAt = new Date();
        expiresAt.setDate(expiresAt.getDate() + parseInt(expiryDays));
      }

      const response = await createUrl({
        originalUrl,
        customAlias: customAlias || undefined,
        expiresAt: expiresAt ? expiresAt.toISOString() : undefined
      });

      onUrlCreated(response);
      setOriginalUrl('');
      setCustomAlias('');
      setExpiryDays('');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to create short URL');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="url-form-container">
      <h2>Shorten Your URL</h2>
      {error && <div className="error-message">{error}</div>}

      <form onSubmit={handleSubmit} className="url-form">
        <div className="form-group">
          <label htmlFor="originalUrl">URL to Shorten*</label>
          <input
            type="text"
            id="originalUrl"
            value={originalUrl}
            onChange={(e) => setOriginalUrl(e.target.value)}
            placeholder="https://example.com/long-url-to-shorten"
            required
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="customAlias">Custom Alias (Optional)</label>
          <input
            type="text"
            id="customAlias"
            value={customAlias}
            onChange={(e) => setCustomAlias(e.target.value)}
            placeholder="custom code"
            disabled={loading}
          />
          <small>Leave empty for auto-generated short URL</small>
        </div>

        <div className="form-group">
          <label htmlFor="expiryDays">Expire After (Days, Optional)</label>
          <input
            type="number"
            id="expiryDays"
            value={expiryDays}
            onChange={(e) => setExpiryDays(e.target.value)}
            placeholder="30"
            min="1"
            disabled={loading}
          />
          <small>Leave empty for non-expiring links</small>
        </div>

        <button
          type="submit"
          className="submit-button"
          disabled={loading}
        >
          {loading ? 'Creating...' : 'Shorten URL'}
        </button>
      </form>
    </div>
  );
};

export default UrlForm;