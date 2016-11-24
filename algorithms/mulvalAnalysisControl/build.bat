docker build -f mulvalAnalysis.dockerfile -t agbot/mulvalanalysis .
docker run -v G:/data:/data agbot/mulvalanalysis