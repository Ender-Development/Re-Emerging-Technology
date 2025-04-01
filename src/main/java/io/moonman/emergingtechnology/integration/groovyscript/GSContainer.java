package io.moonman.emergingtechnology.integration.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import io.moonman.emergingtechnology.integration.groovyscript.machines.AlgaeBioreactor;

public class GSContainer extends GroovyPropertyContainer {
    public AlgaeBioreactor algaeBioreactor = new AlgaeBioreactor();

    public GSContainer() {
        addProperty(algaeBioreactor);
    }
}
