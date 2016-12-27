rm -rf center
cd ../../center
composer install
cd ../dockerfiles/ag_frontend

cp -r ../../center center
docker build -t  serc/agbot:center-frontend .
