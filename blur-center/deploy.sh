./build.sh
./push.sh
ssh sdocker 'docker pull registry.cn-hangzhou.aliyuncs.com/serc/agbot:rebot-frontend && docker rm -f -v rebot-frontend &&  docker run -d --name rebot-frontend -p 9019:3001 -p 9018:3000 --entrypoint=gulp registry.cn-hangzhou.aliyuncs.com/serc/agbot:rebot-frontend serve:dist'
