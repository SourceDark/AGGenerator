docker build -f Dockerfile -t agbot/mulval_analysis_control .
docker tag agbot/mulval_analysis_control 162.105.30.65:9998/mulval_analysis_control
docker push 162.105.30.65:9998/mulval_analysis_control
