# Build Front
FROM node:lts-alpine as node

RUN npm install -g http-server
COPY ./moneyetdystopie-front/package*.json /app/
WORKDIR /app
RUN npm install

COPY moneyetdystopie-front/src ./front
WORKDIR /app/front
RUN npm run build

# Build Back
FROM maven:3-jdk-11 as maven
WORKDIR /app
COPY ./moneyetdystopie-back/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./moneyetdystopie-back/src ./back
COPY --from=node /app/dist/ ./back/main/resources/static/
RUN mvn package && cp ./target/moneyetdystopie-back-*.jar app.jar

# Run Back
FROM openjdk:11-jre
WORKDIR /app
COPY --from=maven /app/app.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]