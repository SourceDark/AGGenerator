FROM mikesplain/openvas
MAINTAINER R <xierui.serc@gmail.com>

ADD workspace /workspace
WORKDIR /workspace
entrypoint ./start.sh

RUN cd /workspace/openvas_lib \
	&& python setup.py install \
	&& pip install openvas_lib

