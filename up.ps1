docker compose stop

cd .\back-end

.\gradlew bootJar

cd ..\

docker compose build start-the-count-back-end

docker compose up -d
