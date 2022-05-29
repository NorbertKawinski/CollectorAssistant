package net.kawinski.collecting.startup.helpers.model.root;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.CatRoot;
import net.kawinski.collecting.startup.helpers.model.root.money.CatForeign;
import net.kawinski.collecting.startup.helpers.model.root.money.CatMetal;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

public class CatMoney extends StartupCategory<CatRoot> {
    public final CollectionAttributeTemplate colAttrCountry;
    public final CollectionAttributeTemplate colAttrYearFrom;
    public final CollectionAttributeTemplate colAttrYearTo;
    public final ElementAttributeTemplate eleAttrValue;
    public final ElementAttributeTemplate eleAttrReleaseYear;
    public final ElementAttributeTemplate eleAttrFront;
    public final ElementAttributeTemplate eleAttrBack;
    public final ElementAttributeTemplate eleAttrMaterial;
    public final ElementAttributeTemplate eleAttrMint;

    public final CatPolish catPolish;
    public final CatForeign catForeign;
    public final CatMetal catMetal;

    public CatMoney(final CatRoot parent) {
        super(parent, "Pieniądze", true);
        setIcon("categories/catMoney.png");

        colAttrCountry = newColAttr("Kraj", AttributeType.LONG_STRING, baseDispOrder + 1, true);
        colAttrYearFrom = newColAttr("Rok wydania (od)", AttributeType.INTEGER, baseDispOrder + 2, true);
        colAttrYearTo = newColAttr("Rok wydania (do)", AttributeType.INTEGER, baseDispOrder + 3, true);
        eleAttrValue = newEleAttr("Wartość", AttributeType.INTEGER, baseDispOrder + 1, true);
        eleAttrReleaseYear = newEleAttr("Rok wydania", AttributeType.INTEGER, baseDispOrder + 2, true);
        eleAttrFront = newEleAttr("Awers", AttributeType.STRING, baseDispOrder + 3, true);
        eleAttrBack = newEleAttr("Rewers", AttributeType.STRING, baseDispOrder + 4, true);
        eleAttrMaterial = newEleAttr("Materiał", AttributeType.STRING, baseDispOrder + 5, true);
        eleAttrMint = newEleAttr("Mennica", AttributeType.STRING, baseDispOrder + 6, true);

        catPolish = new CatPolish(this);
        catForeign = new CatForeign(this);
        catMetal = new CatMetal(this);
    }
}
