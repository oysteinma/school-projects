# Old GITPOD.DOCKERFILE

# FROM gitpod/workspace-full-vnc

# USER gitpod

# RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh \
#              && sdk install java 16.0.1.hs-adpt \
#              && sdk default java 16.0.1.hs-adpt"

# ENV ANDROID_HOME=/home/gitpod/androidsdk \
#     FLUTTER_VERSION=2.2.3-stable

# # Install dart
# USER root
# RUN curl -fsSL https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
#     && curl -fsSL https://storage.googleapis.com/download.dartlang.org/linux/debian/dart_stable.list > /etc/apt/sources.list.d/dart_stable.list \
#     && install-packages build-essential dart libkrb5-dev gcc make gradle android-tools-adb android-tools-fastboot

# # Install flutter
# USER gitpod
# RUN cd /home/gitpod \
#     && wget https://storage.googleapis.com/flutter_infra_release/releases/stable/linux/flutter_linux_${FLUTTER_VERSION}.tar.xz \
#     && tar -xvf flutter*.tar.xz \
#     && rm -f flutter*.tar.xz
# RUN flutter/bin/flutter precache
# RUN echo 'export PATH="$PATH:/home/gitpod/flutter/bin"' >> /home/gitpod/.bashrc

# # Install Open JDK
# USER root
# RUN apt update \
#     && apt install openjdk-8-jdk -y \
#     && update-java-alternatives --set java-1.8.0-openjdk-amd64

# # Install Android SDK
# USER gitpod
# RUN  wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip \
#     && mkdir -p $ANDROID_HOME/cmdline-tools/latest \
#     && unzip commandlinetools-linux-*.zip -d $ANDROID_HOME \
#     && rm -f commandlinetools-linux-*.zip \
#     && mv $ANDROID_HOME/cmdline-tools/bin $ANDROID_HOME/cmdline-tools/latest \
#     && mv $ANDROID_HOME/cmdline-tools/lib $ANDROID_HOME/cmdline-tools/latest
# RUN echo "export ANDROID_HOME=$ANDROID_HOME" >> /home/gitpod/.bashrc \
#     && echo 'export PATH=$ANDROID_HOME/emulator:$ANDROID_HOME/tools:$ANDROID_HOME/cmdline-tools/bin:$ANDROID_HOME/platform-tools:$PATH' >> /home/gitpod/.bashrc

# # Install Google chrome
# USER root
# RUN apt-get update \
#   && apt-get install -y apt-transport-https \
#   && curl -sSL https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
#   && echo "deb [arch=amd64] https://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
#   && apt-get update \
#   && sudo apt-get install -y google-chrome-stable

# # Install Miscellaneous dependencies
# RUN apt-get install -y \
#   libasound2-dev \
#   libgtk-3-dev \
#   libnss3-dev \
#   fonts-noto

# # Add alias to open flutter client in chrome
# RUN alias run-client='flutter run -d web-server --web-port=8081'

# SHELL ["/bin/bash", "-c"]