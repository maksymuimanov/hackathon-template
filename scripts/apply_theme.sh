echo "Applying theme..."

cd ../frontend

read -p "Enter theme URL: " THEME_URL

npx shadcn@latest add "$THEME_URL" --yes

echo "Theme applied successfully"