package net.kawinski.collecting.startup.helpers.model.root.stamps;

import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatStamps;

public class CatForeign extends StartupCategory<CatStamps> {

    public CatForeign(final CatStamps parent) {
        super(parent, "Zagraniczne", true);
    }
}
