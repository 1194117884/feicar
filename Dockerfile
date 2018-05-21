FROM java:8

MAINTAINER yongk_liu@163.com

RUN mkdir -p /opt/app

ADD target/*.jar /opt/app/app.jar

WORKDIR /opt/app/
CMD ["java", "-jar", "app.jar", "--spring.cloud.config.profile=dev"]