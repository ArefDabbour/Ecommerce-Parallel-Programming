for i in $(seq 1 200);
do 
curl --json 1 http://localhost:8080/admin/alter-product/quantity/1 & 
done
sleep 3
curl http://localhost:8080/admin/run-threads
