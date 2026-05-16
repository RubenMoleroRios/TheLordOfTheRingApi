#!/bin/sh
set -eu

API_BASE_URL_VALUE="${API_BASE_URL:-http://localhost:9525/api}"

cat <<EOF >/usr/share/nginx/html/browser-config.js
window.__appConfig = {
  apiBaseUrl: '${API_BASE_URL_VALUE}'
};
EOF