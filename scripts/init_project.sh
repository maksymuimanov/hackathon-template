echo "Initializing project..."
PROJECT_ROOT="$(pwd)"
export PROJECT_ROOT

echo "Found project root: $PROJECT_ROOT"

echo "Moving to scripts directory..."
cd ./scripts
#
#echo "Initializing backend..."
#cd ./backend
#./create.sh
#cd ..

echo "Initializing frontend..."
cd ./frontend
./create.sh
./apply_theme.sh
./clean.sh
cd ..

echo "Project initialized successfully"

read -r -p "Press Enter to exit..."