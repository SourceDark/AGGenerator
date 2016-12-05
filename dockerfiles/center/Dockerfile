FROM eboraas/laravel
MAINTAINER R <r@tianmaying.com>

ADD sources.list /etc/apt/sources.list
RUN apt-get update && apt-get -y install php5-curl

WORKDIR /var/www/laravel
CMD /bin/chown www-data:www-data -R /data &&  php artisan migrate && /usr/sbin/apache2ctl -D FOREGROUND

ADD center /var/www/laravel
RUN chmod -R 777 /var/www/laravel/*
