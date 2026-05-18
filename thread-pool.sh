for i in $(seq 1 100)
do
curl --json "p1"  http://localhost:8080/admin/alter-product/name/1 
done

