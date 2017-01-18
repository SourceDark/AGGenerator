FROM centos

# install required softwares
RUN yum -y install gcc-c++

# copy required files
ADD src /src

# build
RUN g++ /src/main.cpp -std=c++11 -o /src/main

WORKDIR /src

# enrtypoint
CMD /src/main /data/input /data/output /data/error