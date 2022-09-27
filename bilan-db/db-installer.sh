#!/usr/bin/bash

tmp_path="/tmp/bilan-db"
sql_db_artifacts=("db" "views" "procedures"  
"roles" "user-info" "teachers" "context" "tribes" "questions" "actions" "answers" "challenges" "privilages" "courses" "departamento-municipio" "colleges" "post-install")

for i in "${sql_db_artifacts[@]}"; do
    if [ ! -r "$tmp_path/bilan-$i.sql" ]; then
      echo "ERROR: bilan-$i.sql file not found inside $tmp_path";
      exit 1;
    fi
done

read -p "Db User: " username;
read -s -p "Db Password: " password;

echo "********"

for i in "${sql_db_artifacts[@]}"; do
  echo "Configuring $i";
  response=$(mariadb -u$username -p$password < "$tmp_path/bilan-$i.sql" | grep -v '+' | cut -d' ' -f2; echo "${PIPESTATUS[0]}")
  if [ $response -ne 0 ]; then
    break;
  fi;
  echo "$i configured successfully ";
  echo "";
done
