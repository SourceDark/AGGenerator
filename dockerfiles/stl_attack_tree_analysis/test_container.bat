docker build -f Dockerfile -t agbot/stl_attack_path_analysis .
docker run -v G:/data/stl_attack_path_analysis:/data agbot/stl_attack_path_analysis