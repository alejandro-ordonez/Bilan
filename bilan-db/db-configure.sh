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


check_user_exists(){
    # Prompt for the username and host to check
    USER_TO_CHECK=$1
    HOST_TO_CHECK=$2

    # Check if the user with the specified host exists
    RESULT=$(mariadb -sN -e "SELECT User FROM mysql.user WHERE User='$USER_TO_CHECK' AND Host='$HOST_TO_CHECK';")

    if [[ $RESULT == $USER_TO_CHECK ]]; then
        echo "User '$USER_TO_CHECK'@'$HOST_TO_CHECK' exists in MariaDB."
        return 0
    else
        echo "User '$USER_TO_CHECK'@'$HOST_TO_CHECK' does not exist in MariaDB."
        return 1
    fi
}

grant_permissions(){
    # Define variables
    USER=$1
    HOST=$2
    PASSWORD=$3
    DATABASE="*"

    # SQL Commands
    GRANT_PRIVILEGES="GRANT ALL PRIVILEGES ON ${DATABASE}.* TO '${USER}'@'${HOST}' WITH GRANT OPTION;"
    FLUSH_PRIVILEGES="FLUSH PRIVILEGES;"

    # Execute Commands
    mariadb -e "${GRANT_PRIVILEGES}"
    mariadb -e "${FLUSH_PRIVILEGES}"

    echo "User ${USER} created and granted privileges successfully!"
    echo ""
}

create_user(){
    # Define variables
    USER=$1
    HOST=$2
    PASSWORD=$3

    echo "Adding $USER at $HOST..."

    # Commands
    CREATE_USER="CREATE USER '${USER}'@'${HOST}' IDENTIFIED BY '${USER_PASSWORD}';"

    echo "Creating user: '${USER}'@'${HOST}'"
    # Execute the commands
    mariadb -e "${CREATE_USER};"

    echo "User ${USER} created successfully!"
    echo ""
}

create_user_and_grant(){
    # Define variables
    USER=$1
    HOST=$2
    PASSWORD=$3

    if check_user_exists $DB_USER $HOST; then
        echo "User already created, granting permissions"
        grant_permissions $DB_USER $HOST $DB_PASSWORD
    else
        echo "User not created, creating it"
        create_user $DB_USER $HOST $DB_PASSWORD
        echo ""
        echo "Granting permisssions"
        grant_permissions $DB_USER $HOST $DB_PASSWORD
    fi
}

############################################################
echo ""

check_sudo

echo ""
echo "Starting DB Configuration...."
echo ""

####################  Prepare dependencies  #######################
echo ""
echo "###############  Configuring dependencies"

# CURL
CURL="curl"

if check_package $CURL; then
    echo "Skipping $CURL, it's already isntalled"
else
    yum install curl
    echo "curl installed"
fi

echo ""


# MariaDB
MARIADB="MariaDB"
MARIADB_SERVER="${MARIADB}-server"
MARIADB_CLIENT="${MARIADB}-client"
MARIADB_BACKUP="${MARIADB}-backup"

curl -LsS https://r.mariadb.com/downloads/mariadb_repo_setup | sudo bash -s -- --mariadb-server-version="mariadb-11.4.5"

if check_package $MARIADB_SERVER; then
    echo "Skipping $MARIADB_SERVER, it's already isntalled"
else
    yum install $MARIADB_SERVER
    echo "$MARIADB_SERVER installed"
fi

echo ""

if check_package $MARIADB_CLIENT; then
    echo "Skipping $MARIADB_CLIENT, it's already isntalled"
else
    yum install $MARIADB_CLIENT
    echo "$MARIADB_CLIENT installed"
fi

echo ""

if check_package $MARIADB_BACKUP; then
    echo "Skipping $MARIADB_BACKUP, it's already isntalled"
else
    yum install $MARIADB_BACKUP
    echo "$MARIADB_BACKUP installed"
fi

############################################################


###################### Enable service #######################

# Check if the service is enabled
MARIADB_SERVICE="mariadb"

if systemctl is-enabled --quiet "$MARIADB_SERVICE"; then
    echo "The service '$MARIADB_SERVICE' is enabled."
else
    echo "The service '$MARIADB_SERVICE' is not enabled, enabling"
    systemctl enable $MARIADB_SERVICE
fi

systemctl daemon-reload
systemctl start $MARIADB_SERVICE


##############################################################


######################### Create DB ##########################
mariadb -e "CREATE DATABASE bilan;"
echo "Database bilan created;"
##############################################################


###################### Configure Users #######################
echo ""
echo "######### Configure user application in the database ........."
echo ""

read -p "Db User: " DB_USER;
read -s -p "Db Password: " DB_PASSWORD;

echo ""
echo "######### Remote access configuration........."
echo ""

read -p "IP SERVER 1: " IP_SERVER_1;
read -p "IP SERVER 2: " IP_SERVER_2;

echo ""
echo "######## Creating user for $IP_SERVER_1 "
echo ""

create_user_and_grant $DB_USER $IP_SERVER_1 $DB_PASSWORD

echo ""
echo "######## Creating user for $IP_SERVER_2 "
echo ""
create_user_and_grant $DB_USER $IP_SERVER_2 $DB_PASSWORD
##############################################################

echo "Database configured successfully"