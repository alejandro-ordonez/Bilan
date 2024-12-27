#!/bin/bash

#################### Functions #########################
# Function to check if the script is run with sudo
check_sudo() {
    if [ "$EUID" -ne 0 ]; then
        echo "This script requires sudo permissions. Please run it with sudo."
        exit 1
    else
        echo "You have sudo permissions."
    fi
}

# Function to check if a package is installed
check_package() {
    if rpm -q $1 &> /dev/null; then
        echo "Package '$1' is installed."
        return 0
    else
        echo "Package '$1' is not installed."
        return 1
    fi
}

check_file_exists(){
    if [ -f "$1" ]; then
        echo "The file '$1' exists."
        return 0
    else
        echo "The file '$1' does not exist."
        return 1
    fi
}

create_folder_if_not_exists(){
    if [ -d "$1" ]; then 
        echo "The folder '$1' exists." 
    else 
        echo "The folder '$1' does not exist." 
        mkdir $1
    fi
}
############################################################

check_sudo

echo "Starting installation...."

####################  Prepare dependencies  #######################

echo "Configuring dependencies"

# CURL
CURL="curl"

if check_package $CURL; then
    echo "Skipping $CURL, it's already isntalled"
else
    yum install curl
    echo "curl installed"
fi

echo ""

# Maven
UNZIP="unzip"
if check_package $UNZIP; then
    echo "$UNZIP is already installed"
else
    yum install $UNZIP
    echo "$UNZIP installed"
fi

echo ""

# SDKMAN
SDKMAN="sdk"

if check_package $SDKMAN; then
    echo "Skipping $SDKMAN, it's already isntalled"
else
    curl -s "https://get.sdkman.io" | bash
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    echo "sdkman installed"
fi

echo ""

# Java
JAVA="java"
if check_package $JAVA; then
    echo "Java is already installed"
else
    sdk install java 17.0.8-zulu
    echo "Java installed"
fi

echo ""

# Maven
MAVEN="maven"
if check_package $MAVEN; then
    echo "MAVEN is already installed"
else
    sdk install maven
    echo "Maven installed"
fi

echo ""
##################################################


############# Assign permissions #################
echo "Assigning permissions to scripts..."
chmod +x mvnw 
echo ""
##################################################



############### Compile ##########################

echo "Compilation started..."

./mvnw clean
./mvnw versions:set -DnewVersion=1.0
./mvnw -B package --update-snapshots --no-transfer-progress --file pom.xml  -Dmaven.test.skip=true -Pstage

SERVICE_NAME="bilan.service"

# Check if the service is running
if systemctl is-active --quiet "$SERVICE_NAME"; then
    echo "The service '$SERVICE_NAME' is running, stopping it"
    systemctl stop $SERVICE_NAME
else
    echo "The service '$SERVICE_NAME' is not running."
fi

echo "Copying output files..."
create_folder_if_not_exists /var/bilan
create_folder_if_not_exists /var/bilan/target

cp ./target/bilan-backend-1.0.jar /var/bilan/target
echo ""
################################################

############### Add host names #################
echo "Configuración de la base de datos, selecciones según el entorno (producción o certificación)"
echo "Ingrese la IP del servidor de la base de datos"
read IP_ADDRESS
echo "Ingrese el nombre DNS configurado"
read HOSTNAME

# Check if the entry already exists
if grep -q "$IP_ADDRESS $HOSTNAME" /etc/hosts; then
    echo "Entry already exists."
else
    # Add the new entry
    echo "$IP_ADDRESS $HOSTNAME" | sudo tee -a /etc/hosts > /dev/null
    echo "Entry added successfully. $IP_ADDRESS $HOSTNAME"
fi

echo ""
################################################


############### Configure service ##############
echo "Configuring application as a service"
cp ./resources/bilan.service /etc/systemd/system/

echo ""

# Check if the service is enabled
if systemctl is-enabled --quiet "$SERVICE_NAME"; then
    echo "The service '$SERVICE_NAME' is enabled."
else
    echo "The service '$SERVICE_NAME' is not enabled, enabling"
    systemctl enable $SERVICE_NAME
fi

systemctl daemon-reload
systemctl start $SERVICE_NAME

echo ""

echo "Preparing..."
sleep 10s
echo "Status:"
systemctl status bilan.service
################################################

