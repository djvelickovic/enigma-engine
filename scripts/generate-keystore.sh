#!/bin/bash

ALIAS=${1}
PASSWORD=${2}

keytool -genkeypair -alias "${ALIAS}" -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore "${ALIAS}".p12 -validity 3650 -storepass "${PASSWORD}" -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,ip:127.0.0.1

keytool -export -alias "${ALIAS}" -file "${ALIAS}".crt -keystore "${ALIAS}".p12 -storepass "${PASSWORD}"