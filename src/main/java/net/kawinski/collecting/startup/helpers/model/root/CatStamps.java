package net.kawinski.collecting.startup.helpers.model.root;

import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.CatRoot;
import net.kawinski.collecting.startup.helpers.model.root.stamps.CatForeign;
import net.kawinski.collecting.startup.helpers.model.root.stamps.CatOther;
import net.kawinski.collecting.startup.helpers.model.root.stamps.CatPolish;

public class CatStamps extends StartupCategory<CatRoot> {

    public final CatPolish catPolish;
    public final CatForeign catForeign;
    public final CatOther catOther;

    public CatStamps(final CatRoot parent) {
        super(parent, "Znaczki Pocztowe", true);
        setIcon("categories/catStamps.png");

        catPolish = new CatPolish(this);
        catForeign = new CatForeign(this);
        catOther = new CatOther(this);
    }
}
