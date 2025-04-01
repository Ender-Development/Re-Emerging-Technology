package io.moonman.emergingtechnology.integration.groovyscript;

import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import io.moonman.emergingtechnology.integration.groovyscript.machines.*;

public class GSContainer extends GroovyPropertyContainer {
    public AlgaeBioreactor algaeBioreactor = new AlgaeBioreactor();
    public Biomass biomass = new Biomass();
    public Cooker cooker = new Cooker();
    public Processor processor = new Processor();
    public Scaffolder scaffolder = new Scaffolder();
    public Scrubber scrubber = new Scrubber();
    public Shredder shredder = new Shredder();

    public GSContainer() {
        addProperty(algaeBioreactor);
        addProperty(biomass);
        addProperty(cooker);
        addProperty(processor);
        addProperty(scaffolder);
        addProperty(scrubber);
        addProperty(shredder);
    }
}
