docker build -f Dockerfile -t agbot/mulval_analysis_control .
docker run -v G:/data/7:/data agbot/mulval_analysis_control