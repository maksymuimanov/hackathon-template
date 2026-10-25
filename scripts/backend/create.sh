echo "Creating backend..."

cd $PROJECT_ROOT

echo "Downloading backend..."
curl -fL 'https://start.spring.io/starter.zip?type=gradle-project-kotlin&language=kotlin&bootVersion=4.1.2&baseDir=backend&groupId=squad.nononotaa&artifactId=backend&packageName=squad.nononotaa.backend&packaging=jar&javaVersion=25&configurationFileFormat=yaml&dependencies=web,springdoc-openapi' -o backend.zip

echo "Unzipping backend..."
unzip backend.zip && rm backend.zip

echo "Setting backend root..."
BACKEND_ROOT="$PROJECT_ROOT/backend"
export BACKEND_ROOT
echo "Backend root set to $BACKEND_ROOT"

echo "Backend created successfully"