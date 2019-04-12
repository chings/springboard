FROM store/oracle/serverjre:8
RUN echo "Asia/Shanghai" > /etc/timezone
WORKDIR /app
ADD target/springboard-example-service.jar /app
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "springboard-example-service.jar"]