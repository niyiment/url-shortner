
import React from 'react';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="app-footer">
      <div className="footer-content">
        <p>&copy; {currentYear} URL Shortener Service. All rights reserved.</p>
        <div className="footer-links">
          <a href="/#">Terms of Service</a>
          <a href="/#">Privacy Policy</a>
          <a href="/#">Contact Us</a>
        </div>
      </div>
    </footer>
  );
};

export default Footer;