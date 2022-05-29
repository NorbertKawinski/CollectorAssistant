package net.kawinski.collecting.startup.helpers.model.root.money.polish;

import lombok.ToString;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

@ToString
public class ColMyMoney2 extends StartupCollection<CatPolish> {
    public final StartupElement<ColMyMoney2> element1;
    public final StartupElement<ColMyMoney2> element2;

    public ColMyMoney2(final CatPolish category) {
        super(ca.usr1, category, "Więcej mojego hajsu");
        setImage("sample/sample_pln200.jpg");
        addAttribute(category.getParent().colAttrCountry, "Polska");
        addAttribute(category.getParent().colAttrYearFrom, "2004");
        addAttribute(category.getParent().colAttrYearTo, "2008");
        addAttribute(root.colAttrOnSale, "true");
        addAttribute(root.colAttrDetails, "Specjalna kolekcja z lat 2004-2008");

        class Element1 extends StartupElement<ColMyMoney2> {
            public Element1(final ColMyMoney2 collection) {
                super(collection, "Dziesięć złotych");
                addAttribute(category.getParent().eleAttrValue, "10");
                addAttribute(category.getParent().eleAttrReleaseYear, "2005");
                addAttribute(root.eleAttrDetails, "$$$");
            }
        }
        element1 = new Element1(this);

        class Element2 extends StartupElement<ColMyMoney2> {
            public Element2(final ColMyMoney2 collection) {
                super(collection, "Dziesięć złotych");
                addAttribute(category.getParent().eleAttrValue, "10");
                addAttribute(category.getParent().eleAttrReleaseYear, "2006");
                addAttribute(root.eleAttrDetails, "Druga dyszka. W końcu mogę mieć dwie!");
            }
        }
        element2 = new Element2(this);
    }
}
