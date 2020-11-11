curl \
  --cacert keys/enigmaserver.pub \
	--cert keys/enigmaclient.pub \
	--key keys/enigmaclient.key \
	https://localhost:11443/ -v
