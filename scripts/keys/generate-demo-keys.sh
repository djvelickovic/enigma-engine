#!/usr/bin/env bash

#generisanje RSA 4096 za enigma servis
keytool -genkeypair -alias enigmaserver -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore enigmaserver.p12 -validity 3650 -storepass 2secure4u -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,ip:127.0.0.1
keytool -export -alias enigmaserver -file enigmaserver.crt -keystore enigmaserver.p12 -storepass 2secure4u

#generisanje RSA 4096 za enigma klijente
keytool -genkeypair -alias enigmaclient -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore enigmaclient.p12 -validity 3650 -storepass 2secure4u -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,ip:127.0.0.1
keytool -export -alias enigmaclient -file enigmaclient.crt -keystore enigmaclient.p12 -storepass 2secure4u

#generisanje RSA 4096 za enigma backoffice
keytool -genkeypair -alias enigmabackoffice -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore enigmabackoffice.p12 -validity 3650 -storepass 2secure4u -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,ip:127.0.0.1
keytool -export -alias enigmabackoffice -file enigmabackoffice.crt -keystore enigmabackoffice.p12 -storepass 2secure4u

#generisanje RSA 4096 za keycloak
keytool -genkeypair -alias server -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore server.p12 -validity 3650 -storepass password -dname "CN=Djordje Velickovic OU=RAF O=RAF L=Belgrade ST=Serbia C=RS" -ext SAN=dns:localhost,ip:127.0.0.1
keytool -export -alias server -file server.crt -keystore server.p12 -storepass password

#importovanje sertifikata enigma klijenta u keystore enigma servera da bi klijent bio trustovan od strane servera
keytool -import -alias enigmaclient -file enigmaclient.crt -keystore enigmaserver.p12 -storepass 2secure4u -noprompt

#importovanje sertifikata enigma servera u keystore enigma klijenta da bi server bio trustovan od strane klijenta
keytool -import -alias enigmaserver -file enigmaserver.crt -keystore enigmaclient.p12 -storepass 2secure4u -noprompt

#importovanje sertifikata keycloak-a u keystore-ove enigma servera, klijenta i backoffice aplikacije da bi keycloak bio trustovan
keytool -import -alias server -file server.crt -keystore enigmaserver.p12 -storepass 2secure4u -noprompt
keytool -import -alias server -file server.crt -keystore enigmabackoffice.p12 -storepass 2secure4u -noprompt
keytool -import -alias server -file server.crt -keystore enigmaclient.p12 -storepass 2secure4u -noprompt


openssl pkcs12 -in enigmaclient.p12 -nocerts -nodes -out enigmaclient.pem
openssl pkcs12 -in enigmaclient.p12 -clcerts -nokeys -nodes -out enigmaclient.pub
openssl pkcs12 -in enigmaserver.p12 -clcerts -nokeys -nodes -out enigmaserver.pub
