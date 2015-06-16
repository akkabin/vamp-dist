FROM debian:jessie-backports

MAINTAINER matthijs@magnetic.io

ADD https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.8/sbt-launch.jar /opt/sbt-launch.jar

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk git fakeroot

WORKDIR /project

ENTRYPOINT ["java", "-Xms512M", "-Xmx1536M", "-Xss1M", "-XX:+CMSClassUnloadingEnabled", "-Dsbt.global.base=/sbt/0.13", "-Dsbt.ivy.home=/ivy2", "-Dsbt.boot.directory=/sbt/boot", "-jar", "/opt/sbt-launch.jar"]




