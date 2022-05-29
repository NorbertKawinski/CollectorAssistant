package net.kawinski.collecting.startup.helpers.model;


import net.kawinski.collecting.Resources;
import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatCaps;
import net.kawinski.collecting.startup.helpers.model.root.CatMoney;
import net.kawinski.collecting.startup.helpers.model.root.CatPokemon;
import net.kawinski.collecting.startup.helpers.model.root.CatStamps;

/**
 * A helper category which makes "join fetch" a bit easier in JPA.
 * It's a category that can be used for easier "tree-like" category management
 */
public class CatRoot extends StartupCategory<CatNull> {
    public final CollectionAttributeTemplate colAttrBarcode;
    public final CollectionAttributeTemplate colAttrFound;
    public final CollectionAttributeTemplate colAttrOnSale;
    public final CollectionAttributeTemplate colAttrDetails;
    public final CollectionAttributeTemplate colAttrExtraImage;
    public final ElementAttributeTemplate eleAttrBarcode;
    public final ElementAttributeTemplate eleAttrFound;
    public final ElementAttributeTemplate eleAttrOnSale;
    public final ElementAttributeTemplate eleAttrDetails;
    public final ElementAttributeTemplate eleAttrExtraImage;

    public final CatCaps catCaps;
    public final CatMoney catMoney;
    public final CatStamps catStamps;
    public final CatPokemon catPokemon;

    public CatRoot() {
        super(null, "Wszystkie", false);
        // I know it's bad. But it's one-time initialization only
        //noinspection AssignmentToStaticFieldFromInstanceMethod,AssignmentToSuperclassField,ThisEscapedInObjectConstruction
        root = this;

        colAttrBarcode = newColAttr("Kod kreskowy", AttributeType.BARCODE, baseDispOrder + 1, true);
        colAttrFound = newColAttr("Znaleziono", AttributeType.DATETIME, baseDispOrder + 2, true);
        colAttrOnSale = newColAttr("Na sprzedaż", AttributeType.BOOLEAN, baseDispOrderEnd - 9, true);
        colAttrDetails = newColAttr("Szczegóły", AttributeType.LONG_STRING, baseDispOrderEnd - 8, false);
        colAttrExtraImage = newColAttr("Zdjęcie", AttributeType.IMAGE, baseDispOrderEnd - 7, false);

        eleAttrBarcode = newEleAttr("Kod kreskowy", AttributeType.BARCODE, baseDispOrder + 1, true);
        eleAttrFound = newEleAttr("Znaleziono", AttributeType.DATETIME, baseDispOrder + 2, true);
        eleAttrOnSale = newEleAttr("Na sprzedaż", AttributeType.BOOLEAN, baseDispOrderEnd - 9, true);
        eleAttrDetails = newEleAttr("Szczegóły", AttributeType.LONG_STRING, baseDispOrderEnd - 8, false);
        eleAttrExtraImage = newEleAttr("Zdjęcie", AttributeType.IMAGE, baseDispOrderEnd - 7, false);

        catCaps = new CatCaps(this);
        catMoney = new CatMoney(this);
        catStamps = new CatStamps(this);
        catPokemon = new CatPokemon(this);
    }
}
