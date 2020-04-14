#!/usr/bin/env bash

PASSWORD="2secure4u"

./generate-keystore.sh enigmaserver ${PASSWORD}
./generate-keystore.sh enigmaclient ${PASSWORD}

/usr/bin/keytool -import -alias enigmaclient -file enigmaclient.crt -keystore enigmaserver.p12 -storepass ${PASSWORD} -noprompt
/usr/bin/keytool -import -alias enigmaserver -file enigmaserver.crt -keystore enigmaclient.p12 -storepass ${PASSWORD} -noprompt