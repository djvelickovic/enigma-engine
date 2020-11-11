response=$(curl \
  -d client_id=enigma-user \
  -d client_secret=2secure4u \
  -d grant_type=client_credentials \
  --cacert keys/keycloak.pub \
	https://localhost:12443/auth/realms/enigma/protocol/openid-connect/token -s)

access_token=$(jq -r '.access_token' <(echo "$response"))