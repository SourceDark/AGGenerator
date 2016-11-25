docker build -f mulval_analysis.dockerfile -t agbot/mulval_analysis .
docker run -v G:/data/mulval_analysis:/data agbot/mulval_analysis