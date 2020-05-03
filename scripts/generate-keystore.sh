#!/bin/bash

ALIAS=${1}
PASSWORD=${2}
STORE_TYPE=${3} #PKCS12, JKS
K8S_DNS=${4}

if [ "$STORE_TYPE" = "PKCS12" ]
then
  KEYSTORE_EXTENSION="p12"
elif [ "$STORE_TYPE" = "JKS" ]
then
  KEYSTORE_EXTENSION="jks"
fi

keytool -genkeypair -alias "${ALIAS}" -keyalg RSA -keysize 4096 -storetype "${STORE_TYPE}" -keystore "${ALIAS}".${KEYSTORE_EXTENSION} -validity 3650 -storepass "${PASSWORD}" -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,dns:"${K8S_DNS}",ip:127.0.0.1

keytool -export -alias "${ALIAS}" -file "${ALIAS}".crt -keystore "${ALIAS}".${KEYSTORE_EXTENSION} -storepass "${PASSWORD}"