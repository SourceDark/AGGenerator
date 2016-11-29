FROM eboraas/laravel
MAINTAINER R <r@tianmaying.com>

WORKDIR /var/www/laravel
CMD php artisan migrate && /usr/sbin/apache2ctl -D FOREGROUND

ADD center /var/www/laravel
RUN chmod -R 777 /var/www/laravel/*
