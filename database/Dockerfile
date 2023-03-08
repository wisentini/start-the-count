FROM postgres:15
RUN sed -i -e 's/# pt_BR.UTF-8 UTF-8/pt_BR.UTF-8 UTF-8/' /etc/locale.gen && locale-gen
ENV LANG pt_BR.UTF-8 
ENV LANGUAGE pt_BR:pt
ENV LC_ALL pt_BR.UTF-8
