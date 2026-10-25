echo "Creating frontend..."

cd $PROJECT_ROOT

echo "Downloading frontend..."
npx shadcn@latest init --preset b0 --template next --pointer --yes --name=frontend --monorepo

echo "Setting frontend root..."
FRONTEND_ROOT="$PROJECT_ROOT/frontend"
export FRONTEND_ROOT
echo "Frontend root set to $FRONTEND_ROOT"

echo "Frontend created successfully"