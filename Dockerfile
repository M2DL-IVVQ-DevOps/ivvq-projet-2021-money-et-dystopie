# Build Front
FROM node:lts-alpine as node

RUN npm install -g http-server
WORKDIR /app
COPY package*.json ./
RUN npm install

COPY ./src ./src
WORKDIR /src
RUN npm run build

# Build Back
FROM maven:3-jdk-11 as maven
WORKDIR /app
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
COPY --from=node /app/src ./src/static
RUN mvn package && cp target/moneyetdystopie-back-*.jar app.jar

# Run Back
FROM openjdk:11-jre
WORKDIR /app
COPY --from=maven /app/app.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./app.jar"]
