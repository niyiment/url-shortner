# URL Shortener Service

A lightweight, scalable URL shortening service with RESTful API and web interface. Built with Java Spring Boot, MySQL, and React.

![Screenshot](./assets/screenshot.png)

## Features

- **URL Shortening**: Convert long URLs into compact, shareable links
- **Redirection**: Seamless redirects from short URLs to original destinations
- **RESTful API**: Full CRUD operations for URL management
- **Persistence**: MySQL database storage for URL mappings
- **Listing**: View all shortened URLs with metadata
- **Deletion**: Remove unwanted short URLs

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.x
- **Database**: MySQL
- **Frontend**: React
- **API Documentation**: OpenAPI/Swagger (auto-generated)
- **Build Tool**: Maven

## API Reference

### Base URL
`http://localhost:8080/api/urls`

### Endpoints

| Method | Endpoint              | Description                           | Status Codes          |
|--------|-----------------------|---------------------------------------|-----------------------|
| GET    | `/`                   | Get all shortened URLs                | 200 OK               |
| POST   | `/`                   | Create new short URL                  | 201 Created          |
| GET    | `/{shortCode}`        | Redirect to original URL              | 302 Found            |
| DELETE | `/{shortCode}`        | Delete short URL mapping              | 204 No Content       |

### Request/Response Examples

**Create Short URL (POST):**
```json
// Request
{
  "url": "https://example.com/very/long/url/path"
}

// Response (201 Created)
{
  "originalUrl": "https://example.com/very/long/url/path",
  "shortUrl": "http://localhost:8080/api/urls/abc123",
  "shortCode": "abc123",
  "createdAt": "2023-08-15T10:30:00Z"
}
