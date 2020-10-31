### keycloak preparation
* Run `./build-keycloak.sh`
* Start server `./keycloak-11.0.2/bin/standalone.sh -Djboss.socket.binding.port-offset=4000`
* Create user `admin:admin` and import realm placed in `config/` folder
* create user `test:test` and add `enigma_*` roles

`CREATE ROLE rolename LOGIN SUPERUSER PASSWORD 'passwordstring';`

