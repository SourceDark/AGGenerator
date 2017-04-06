cd /root
/root/startSql.bash
mkdir -p /data/tmp
cd /data/tmp
cp /input/config.txt /data/tmp/config.txt
cp /data/input /data/tmp/input.P
graph_gen.sh input.P -v  --nopdf
cp /data/tmp/AttackGraph.txt /data/output
