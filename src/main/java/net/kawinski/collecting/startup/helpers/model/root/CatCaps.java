package net.kawinski.collecting.startup.helpers.model.root;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.CatRoot;
import net.kawinski.collecting.startup.helpers.model.root.caps.CatAlcoholic;
import net.kawinski.collecting.startup.helpers.model.root.caps.CatOther;
import net.kawinski.collecting.startup.helpers.model.root.caps.ColCaps;

public class CatCaps extends StartupCategory<CatRoot> {
    public final CollectionAttributeTemplate colAttrCountry;
    public final CollectionAttributeTemplate colAttrYearFrom;
    public final CollectionAttributeTemplate colAttrYearTo;
    public final CollectionAttributeTemplate colAttrBrewery;
    public final ElementAttributeTemplate eleAttrCountry;
    public final ElementAttributeTemplate eleAttrYearFrom;
    public final ElementAttributeTemplate eleAttrYearTo;
    public final ElementAttributeTemplate eleAttrBrewery;
    public final ElementAttributeTemplate eleAttrText;

    public final CatAlcoholic catAlcoholic;
    public final CatOther catOther;

    public CatCaps(final CatRoot parent) {
        super(parent, "Kapsle", true);
        setIcon("categories/catCaps.png");

        colAttrCountry = newColAttr("Kraj", AttributeType.LONG_STRING, baseDispOrder + 1, true);
        colAttrYearFrom = newColAttr("Rok produkcji (od)", AttributeType.INTEGER, baseDispOrder + 2, true);
        colAttrYearTo = newColAttr("Rok produkcji (do)", AttributeType.INTEGER, baseDispOrder + 3, true);
        colAttrBrewery = newColAttr("Browar", AttributeType.INTEGER, baseDispOrder + 4, true);

        eleAttrCountry = newEleAttr("Kraj", AttributeType.LONG_STRING, baseDispOrder + 1, true);
        eleAttrYearFrom = newEleAttr("Rok produkcji (od)", AttributeType.INTEGER, baseDispOrder + 2, true);
        eleAttrYearTo = newEleAttr("Rok produkcji (do)", AttributeType.INTEGER, baseDispOrder + 3, true);
        eleAttrBrewery = newEleAttr("Browar", AttributeType.INTEGER, baseDispOrder + 4, true);
        eleAttrText = newEleAttr("Tekst", AttributeType.STRING, baseDispOrder + 5, true);

        catAlcoholic = new CatAlcoholic(this);
        catOther = new CatOther(this);

//        new ColCaps(this);
    }
}
