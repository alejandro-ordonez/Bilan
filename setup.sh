docker-compose up -d db adminer
cd ./core/
./mvnw compile package
docker-compose up -d backend
