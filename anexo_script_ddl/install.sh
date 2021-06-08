#!/usr/bin/env bash
default_anexo_path="./Anexo3.csv" 
default_sql_path="./bilandb.sql" 

if [ ! -r "$default_anexo_path" ]; then
    echo "Usually the anenexed file should be in the path /tmp please check this out";
    exit 1;
fi

read -p "Enter Db user: " username;
read -s -p "Enter Db password: " password;

echo -e "\nAssinging the annexed path...";
sed -i "s|[%]\{1\}InFileAnexoPath[%]\{1\}|$default_anexo_path|gI" "$default_sql_path";
echo "Done!";

echo "Running the sql file..."
mariadb -u$username -p$password << EOF
source $default_sql_path;
exit
commit;
EOF
echo "Done!"
