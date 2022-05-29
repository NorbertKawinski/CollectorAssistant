package net.kawinski.collecting.startup.helpers.model.root.stamps;

import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatStamps;

public class CatOther extends StartupCategory<CatStamps> {

    public CatOther(final CatStamps parent) {
        super(parent, "Pozostałe", true);
    }
}
