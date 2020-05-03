#!/usr/bin/env bash

PASSWORD="2secure4u"

./generate-keystore.sh enigmaserver ${PASSWORD} PKCS12 enigma-engine-svc
./generate-keystore.sh enigmaclient ${PASSWORD} PKCS12 enigma-engine-svc
./generate-keystore.sh enigmabackoffice ${PASSWORD} PKCS12 enigma-backoffice-svc
./generate-keystore.sh server password PKCS12 enigma-keycloak-svc

keytool -import -alias enigmaclient -file enigmaclient.crt -keystore enigmaserver.p12 -storepass ${PASSWORD} -noprompt
keytool -import -alias enigmaserver -file enigmaserver.crt -keystore enigmaclient.p12 -storepass ${PASSWORD} -noprompt

keytool -import -alias server -file server.crt -keystore enigmaserver.p12 -storepass ${PASSWORD} -noprompt
keytool -import -alias server -file server.crt -keystore enigmabackoffice.p12 -storepass ${PASSWORD} -noprompt
