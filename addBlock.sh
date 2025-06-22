#!/bin/sh
[ "$1" ] || { echo "Usage: $0 <block-name> [incl-facing]"; exit 1; }
[ "$2" ] && facing='"facing": {
	"north": {},
	"east": {"y": 90},
	"south": {"y": 180},
	"west": {"y": 270},
}'
echo '{
	"forge_marker": 1,
	"defaults": {
		"model": "emergingtechnology:'"$1"'"
	},
	"variants": {
		"normal": [{}],
		"inventory": [{}]
	},
	'"$facing"'
}' >"src/main/resources/assets/emergingtechnology/blockstates/$1.json"
echo '{}' >"src/main/resources/assets/emergingtechnology/models/block/$1.json"
echo You\'re left with models/block/$1.json and textures/blocks/$1


# {
# 	"parent": "block/cube_all",
# 	"textures": {
#		"all": "emergingtechnology:blocks/"
#	 }
# }
