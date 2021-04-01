Pour build le Dockerfile :
docker build -t moneyetdystopie
 
Pour lancer le serveur :
docker run -it --rm -p 42000:8080 moneyetdystopie