response=$(curl \
  -d client_id=enigma-user \
  -d client_secret=2secure4u \
  -d grant_type=client_credentials \
  --cacert keys/keycloak.pub \
	https://localhost:12443/auth/realms/enigma/protocol/openid-connect/token -s)

access_token=$(jq -r '.access_token' <(echo "$response"))

crypto_response=$(curl -d "{\"val\":\"${2}\"}" \
  --cacert keys/enigmaserver.pub \
	--cert keys/enigmaclient.pub \
	--key keys/enigmaclient.key \
	-H 'Content-Type: application/json' \
	-H "Authorization: Bearer $access_token" \
	https://localhost:11443/enigma/crypto/map/encrypt/${1} -s)

jq -r '.val' <(echo "$crypto_response")