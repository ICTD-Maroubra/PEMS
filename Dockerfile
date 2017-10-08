FROM openjdk:8

ADD ./docker-entrypoint.sh /
COPY ./pems-server/build/libs/pems-all.jar /pems/
COPY ./pems-server/lib/tinyb/* /pems/

RUN apt-get update && \
    apt-get install -q -y bluez

ENTRYPOINT [ "/docker-entrypoint.sh" ]
