FROM php:7.0-cli

# copy required files
RUN mkdir /src
WORKDIR /src
RUN mkdir /data
COPY src /src

# enrtypoint
CMD php /src/Control.php