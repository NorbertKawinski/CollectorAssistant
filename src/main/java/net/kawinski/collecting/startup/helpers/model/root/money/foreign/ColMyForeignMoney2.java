package net.kawinski.collecting.startup.helpers.model.root.money.foreign;

import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.money.CatForeign;

public class ColMyForeignMoney2 extends StartupCollection<CatForeign> {
    public final StartupElement<ColMyForeignMoney2> element1;

    public ColMyForeignMoney2(final CatForeign category) {
        super(ca.usr2, category, "Dolary");
        setImage("sample/sample_dollars.jpg");
        addAttribute(category.getParent().colAttrCountry, "USA");
        addAttribute(category.getParent().colAttrYearFrom, "1792");
        addAttribute(root.colAttrOnSale, "true"); // Dollars are always on sale
        addAttribute(root.colAttrDetails, "Dolary rulz");

        class Element1 extends StartupElement<ColMyForeignMoney2> {
            public Element1(final ColMyForeignMoney2 collection) {
                super(collection, "1 dolar");
                setImage("sample/sample_dollars_1.jpg");
                addAttribute(category.getParent().eleAttrValue, "1");
                addAttribute(category.getParent().eleAttrReleaseYear, "1915");
                addAttribute(root.eleAttrDetails, "$$$");
            }
        }
        element1 = new Element1(this);

    }
}
