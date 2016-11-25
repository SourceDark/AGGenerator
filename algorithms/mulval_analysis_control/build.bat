docker build -f mulvalAnalysis.dockerfile -t agbot/mulvalanalysis .
docker run -v G:/data/mulvalanalysis:/data agbot/mulvalanalysis