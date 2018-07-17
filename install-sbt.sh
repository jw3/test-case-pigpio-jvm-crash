#!/usr/bin/env bash

readonly JAVA_VERSION_MAJOR=8
readonly JAVA_VERSION_MINOR=181
readonly JAVA_VERSION_BUILD=13
readonly JAVA_PACKAGE=jdk
readonly ARCH=32
readonly JAVA_HOME=/usr/local/java
readonly LANG=C.UTF-8

source /etc/os-release

echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt.list
echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu $VERSION_CODENAME main" | sudo tee /etc/apt/sources.list.d/oracle-jdk.list

apt-get update
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-key EEA14886

apt-get install -y --no-install-recommends oracle-java8-installer sbt
