
import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {

  return (
    <header className="app-header">
      <div className="logo">
        <Link to="/">URL Shortener</Link>
      </div>
      <nav className="main-nav">
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/dashboard">Dashboard</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;