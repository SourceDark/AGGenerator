docker build -f Dockerfile -t agbot/mulval_analysis_control .
docker run -v G:/data/mulval_analysis:/data agbot/mulval_analysis_control