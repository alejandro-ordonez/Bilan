# Bilan
Backend for Ministerio de Educación Nacional

## Follow contribution guidelines:

> https://gist.github.com/lichblitz/2bdffe469171b1a6987fa2af41321a98

## Deployment (docker)

- Add file Anexo3.csv to ./anexo_script_ddl dir.
- Check docker-compose.yml file for env vars setup.
- Run `docker-compose up d` or run `setup.sh`. Just make sure the .jar file is already generated.
- Optional: Before running docker-compose, run `./core/mvnw build package` to generate jar file.

