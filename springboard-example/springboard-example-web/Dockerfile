FROM store/oracle/serverjre:8
RUN echo "Asia/Shanghai" > /etc/timezone
WORKDIR /app
ADD target/springboard-example-web.jar /app
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "springboard-example-web.jar"]