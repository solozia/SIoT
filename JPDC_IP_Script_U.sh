# varying the number of users 

for (( i = 1 ; i <= 100; i++ ))  do 
      java -jar IP.jar 30000 3 100
done
for (( i = 1 ; i <= 200; i++ ))  do 
      java -jar IP.jar 30000 3 200
done

for (( i = 1 ; i <= 300; i++ ))  do 
      java -jar IP.jar 30000 3 300
done
for (( i = 1 ; i <= 400; i++ ))  do 
      java -jar IP.jar 30000 3 400
done
for (( i = 1 ; i <= 500; i++ ))  do 
      java -jar IP.jar 30000 3 500
done
