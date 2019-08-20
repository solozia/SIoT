# varying the number of users 
for (( i = 1 ; i <= 155; i++ ))  do 
      java -jar MCDM.jar 30000 3 200
done
sleep 10

for (( i = 1 ; i <= 300; i++ ))  do 
      java -jar MCDM.jar 30000 3 300
done
sleep 10
for (( i = 1 ; i <= 400; i++ ))  do 
      java -jar MCDM.jar 30000 3 400
done
sleep 10
for (( i = 1 ; i <= 500; i++ ))  do 
      java -jar MCDM.jar 30000 3 500
done
sleep 10
for (( i = 1 ; i <= 600; i++ ))  do 
      java -jar MCDM.jar 30000 3 600
done
sleep 10
for (( i = 1 ; i <= 700; i++ ))  do 
      java -jar MCDM.jar 30000 3 700
      
done
sleep 10
for (( i = 1 ; i <= 800; i++ ))  do 
      java -jar MCDM.jar 30000 3 800
done
sleep 10
for (( i = 1 ; i <= 900; i++ ))  do 
      java -jar MCDM.jar 30000 3 900
done
sleep 10
for (( i = 1 ; i <= 1000; i++ ))  do 
      java -jar MCDM.jar 30000 3 1000
done
