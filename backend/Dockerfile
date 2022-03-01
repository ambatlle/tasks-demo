FROM openjdk:17-alpine AS BUILD_IMAGE
ENV APP_HOME=/root/dev/myapp
WORKDIR $APP_HOME
COPY mvnw $APP_HOME
COPY mvnw  $APP_HOME
COPY .mvn/ $APP_HOME/.mvn
COPY pom.xml $APP_HOME
RUN chmod +x ./mvnw && \
  ./mvnw dependency:resolve
COPY . .
RUN chmod +x ./mvnw && ./mvnw clean package && \
  rm -f $APP_HOME/target/tasks-api-*sources*.jar && \
  mv $APP_HOME/target/tasks-api-*.jar $APP_HOME/target/tasks-api-app.jar

FROM openjdk:11-jre-slim AS DB_CREATOR
WORKDIR /root/tasks-app/
COPY --from=BUILD_IMAGE /root/dev/myapp/config.yml .
COPY --from=BUILD_IMAGE /root/dev/myapp/target/tasks-api-app.jar .
RUN java -jar tasks-api-app.jar db migrate config.yml

FROM openjdk:11-jre-slim
WORKDIR /root/tasks-app/
COPY --from=BUILD_IMAGE /root/dev/myapp/config.yml .
COPY --from=BUILD_IMAGE /root/dev/myapp/target/tasks-api-app.jar .
COPY --from=DB_CREATOR /root/tasksApp /root/tasksApp
EXPOSE 8080
CMD ["java","-jar","tasks-api-app.jar", "server", "config.yml"]