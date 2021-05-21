# Build Front
FROM node:lts-alpine as node

RUN npm install -g http-server
COPY moneyetdystopie-front/package*.json /front/
WORKDIR /front
RUN npm install

COPY moneyetdystopie-front/src /front/src
WORKDIR /front/src
RUN npm run build

# Build Back
FROM maven:3-jdk-11 as maven
COPY moneyetdystopie-back/pom.xml /back/pom.xml
WORKDIR /back
RUN mvn dependency:go-offline -B

COPY moneyetdystopie-back/src /back/src
COPY --from=node /front/dist/ /back/src/main/resources/static/
RUN mvn package && cp /back/target/moneyetdystopie-back-*.jar app.jar

# Run Back
FROM openjdk:11-jre
WORKDIR /app
COPY --from=maven /back/app.jar app.jar
COPY ./wait-for-it.sh .
RUN chmod +x wait-for-it.sh

ENV profile="dev"

EXPOSE 8080
ENTRYPOINT ["./wait-for-it.sh", "-s", "--"]
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=${profile}", "-jar", "app.jar"]