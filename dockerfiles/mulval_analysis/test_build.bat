docker build -f Dockerfile -t agbot/mulval_analysis .
docker run -v G:/data/7/algorithm:/data agbot/mulval_analysis