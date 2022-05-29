package net.kawinski.collecting.startup.helpers.model.root.money;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatMoney;

public class CatMetal extends StartupCategory<CatMoney> {
    final ElementAttributeTemplate eleAttrAlloy;
    final ElementAttributeTemplate eleAttrWeight;

    public CatMetal(final CatMoney parent) {
        super(parent, "Sztabki i monety bulionowe", true);
        eleAttrAlloy = newEleAttr("Stop", AttributeType.STRING, baseDispOrder + 1, true);
        eleAttrWeight = newEleAttr("Waga", AttributeType.INTEGER, baseDispOrder + 2, true);
    }
}
