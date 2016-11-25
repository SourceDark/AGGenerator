FROM php:7.0-cli

# copy required files
RUN mkdir /src
RUN mkdir /data
ADD control.php /src/control.php

# enrtypoint
CMD php /src/control.php