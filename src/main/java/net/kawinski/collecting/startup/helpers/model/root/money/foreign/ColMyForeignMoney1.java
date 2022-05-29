package net.kawinski.collecting.startup.helpers.model.root.money.foreign;

import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.money.CatForeign;

public class ColMyForeignMoney1 extends StartupCollection<CatForeign> {
    public final StartupElement<ColMyForeignMoney1> element1;
    public final StartupElement<ColMyForeignMoney1> element2;

    public ColMyForeignMoney1(final CatForeign category) {
        super(ca.usr1, category, "Hajs z zagranicy");
        setImage("sample/sample_banknotes.jpg");
        addAttribute(category.getParent().colAttrCountry, "Świat");
        addAttribute(root.colAttrDetails, "Kolekcja wszystkiego co tylko mam");

        class Element1 extends StartupElement<ColMyForeignMoney1> {
            public Element1(final ColMyForeignMoney1 collection) {
                super(collection, "10 euro");
                setImage("sample/sample_euro_10.jpg");
                addAttribute(category.getParent().eleAttrValue, "10");
                addAttribute(category.getParent().eleAttrReleaseYear, "1991");
                addAttribute(root.eleAttrDetails, "Oto dziesięć euro");
            }
        }
        element1 = new Element1(this);

        class Element2 extends StartupElement<ColMyForeignMoney1> {
            public Element2(final ColMyForeignMoney1 collection) {
                super(collection, "200 euro");
                setImage("sample/sample_euro_200.jpg");
                addAttribute(category.getParent().eleAttrValue, "200");
                addAttribute(category.getParent().eleAttrReleaseYear, "1992");
                addAttribute(root.eleAttrDetails, "Oto dwieście euro");
            }
        }
        element2 = new Element2(this);

    }
}
