#!/bin/bash

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo "Starting backend and frontend..."

# Run backend in background
cd "$ROOT_DIR/backend"
./gradlew bootRun &
BACKEND_PID=$!

# Run frontend in background
cd "$ROOT_DIR/frontend"
npm run dev &
FRONTEND_PID=$!

echo "Backend (PID: $BACKEND_PID) and frontend (PID: $FRONTEND_PID) are running."
echo "Press Ctrl+C to stop both."

# Trap Ctrl+C to kill both processes
trap "kill $BACKEND_PID $FRONTEND_PID 2>/dev/null; exit" SIGINT SIGTERM

# Wait for both processes
wait $BACKEND_PID $FRONTEND_PID
