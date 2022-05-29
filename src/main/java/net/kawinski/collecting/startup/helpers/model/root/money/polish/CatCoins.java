package net.kawinski.collecting.startup.helpers.model.root.money.polish;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

public class CatCoins extends StartupCategory<CatPolish> {
    public final ElementAttributeTemplate eleAttrRadius;

    public CatCoins(final CatPolish parent) {
        super(parent, "Monety", true);
        setIcon("categories/catCoins.png");

        eleAttrRadius = newEleAttr("Średnica", AttributeType.STRING, baseDispOrder + 1, true);
    }
}
