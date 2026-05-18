for i in $(seq 1 1000);
do
	curl -X POST http://localhost:8080/admin/alter-product/price/1/$i
done
