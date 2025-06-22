#!/bin/sh
[ "$1" ] || { echo "Usage: $0 <item-name> [old EMT name]"; exit 1; }
echo '{
	"parent": "item/generated",
	"textures": {
		"layer0": "emergingtechnology:items/'"$1"'"
	}
}' >"src/main/resources/assets/emergingtechnology/models/item/$1.json"
[ "$2" ] && cp -v "../Re-Emerging-Technology/src/main/resources/assets/emergingtechnology/textures/items/$2.png" "src/main/resources/assets/emergingtechnology/textures/items/$1.png"
