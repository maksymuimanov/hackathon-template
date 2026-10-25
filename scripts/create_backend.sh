echo "Creating backend..."

cd ../

curl -fL 'https://start.spring.io/starter.zip?type=gradle-project-kotlin&language=kotlin&bootVersion=4.1.2&baseDir=backend&groupId=squad.nononotaa&artifactId=backend&packageName=squad.nononotaa.backend&packaging=jar&javaVersion=25&configurationFileFormat=yaml&dependencies=web,springdoc-openapi' -o backend.zip

unzip backend.zip && rm backend.zip

echo "Backend created successfully"