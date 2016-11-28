docker build -f Dockerfile -t agbot/mulval_analysis .
docker run -v G:/data/mulval_analysis/algorithm:/data agbot/mulval_analysis