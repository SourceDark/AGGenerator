docker build -f Dockerfile -t agbot/keynodes_analysis .
docker run -v G:/data/keynodes_analysis:/data agbot/keynodes_analysis 