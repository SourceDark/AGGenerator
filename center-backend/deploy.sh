mvn clean install
./build.sh
./push.sh
ssh sdocker '/root/xr/ag_backend/restart.sh'
