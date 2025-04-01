
// Auto generated groovyscript example file
// MODS_LOADED: emergingtechnology

log.info 'mod \'emergingtechnology\' detected, running script'

// groovyscript.wiki.emergingtechnology.algae_bioreactor.title:
// groovyscript.wiki.emergingtechnology.algae_bioreactor.description.

// mods.emergingtechnology.algae_bioreactor.removeAll()

mods.emergingtechnology.algae_bioreactor.getRecipeBuilder()
    .input(item('minecraft:apple'))
    .output(item('minecraft:bone'))
    .register()


