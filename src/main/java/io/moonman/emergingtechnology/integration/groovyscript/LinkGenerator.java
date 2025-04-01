package io.moonman.emergingtechnology.integration.groovyscript;

import com.cleanroommc.groovyscript.documentation.linkgenerator.BasicLinkGenerator;
import io.moonman.emergingtechnology.Tags;

public class LinkGenerator extends BasicLinkGenerator {
    @Override
    public String id() {
        return Tags.MOD_ID;
    }

    @Override
    protected String domain() {
        return "https://github.com/Ender-Development/Re-Emerging-Technology/";
    }
}
