package net.kawinski.collecting.startup.helpers.model.root.money.polish;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

public class CatBanknotes extends StartupCategory<CatPolish> {
    public final ElementAttributeTemplate eleAttrSerialNum;
    public final ElementAttributeTemplate eleAttrDimensions;

    public CatBanknotes(final CatPolish parent) {
        super(parent, "Banknoty", true);
        setIcon("categories/catBanknotes.png");

        eleAttrSerialNum = newEleAttr("Numer seryjny", AttributeType.STRING, baseDispOrder + 1, true);
        // TODO: Szerokosc/Wysokosc/Grubosc???
        eleAttrDimensions = newEleAttr("Wymiary", AttributeType.STRING, baseDispOrder + 2, true);
    }
}
