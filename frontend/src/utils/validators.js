
export const isValidUrl = (url) => {
  try {
    // Check if URL is valid
    new URL(url);

    // Check if protocol is http or https
    if (!url.startsWith('http://') && !url.startsWith('https://')) {
      return false;
    }

    return true;
  } catch (e) {
    return false;
  }
};