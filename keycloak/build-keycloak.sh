curl -O https://downloads.jboss.org/keycloak/11.0.2/keycloak-11.0.2.zip
unzip -oq keycloak-11.0.2
unzip -oq postgres-module.zip
cp -R ./org ./keycloak-11.0.2/modules/system/layers/base
cp -R ./application.keystore ./keycloak-11.0.2/standalone/configuration/application.keystore
cp -R ./standalone.xml ./keycloak-11.0.2/standalone/configuration/standalone.xml

