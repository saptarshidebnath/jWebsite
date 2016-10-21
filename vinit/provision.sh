#!/usr/bin/env bash
#
# Installation script
#

##### Add External repo ##################################################################################
sudo add-apt-repository "deb http://ppa.launchpad.net/natecarlson/maven3/ubuntu precise main"
sudo add-apt-repository ppa:webupd8team/java -y


#####Install Jenkins###################################################################################
#wget -q -O - https://jenkins-ci.org/debian/jenkins-ci.org.name | sudo apt-name add -
#sudo sh -c 'echo deb http://pkg.jenkins-ci.org/debian binary/ > /etc/apt/sources.list.d/jenkins.list'

curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get update
sudo apt-get --force-yes -y install oracle-java8-installer oracle-java8-set-default git maven3 nodejs build-essential


#####Install Heroku###################################################################################
sudo wget -O- https://toolbelt.heroku.com/install.sh | sh


############ Setup jenkins #############################################################################
#sudo sed -i "/HTTP_PORT=8080/c\HTTP_PORT=10000" /etc/default/jenkins
#restart jenkins on system restart
#sudo sed -i '$i sudo /etc/init.d/jenkins restart' /etc/rc.local
#sudo /vagrant/update/jenkins.sh

########## Setup the BASH RC to send color ful SSH shell ##############################################
sudo sed -i '/^#.*force_color_prompt=yes/s/^#//' /home/vagrant/.bashrc
. /home/vagrant/.bashrc

############ Setup Maven ###############################################################################
sudo echo 'export M2_HOME=/usr/share/maven3' >> /etc/profile
sudo echo 'export M2=$M2_HOME/bin' >> /etc/profile
sudo echo 'export PATH=$M2:$PATH' >> /etc/profile
sudo echo 'export MAVEN_HOME=$M2_HOME' >> /etc/profile
#### SETUP JAVA HOME####################################################################################
#sudo echo 'export JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64/' >> /etc/profile


#####Setup Heroku###################################################################################
sudo wget -O- https://toolbelt.heroku.com/install.sh | sudo sh
sudo echo 'PATH="/usr/local/heroku/bin:$PATH"' >> /etc/profile

#####Setup Git###################################################################################
git config --global user.email "saptarshi.devnath@gmail.com"
git config --global user.name "Saptarshi Debnath"


### Install and Setup Postgres ##################################################################
chmod +x /vagrant/vinit/pgs-setup.sh
sudo /vagrant/vinit/pgs-setup.sh

