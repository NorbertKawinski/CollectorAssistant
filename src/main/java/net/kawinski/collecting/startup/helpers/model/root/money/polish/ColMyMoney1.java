package net.kawinski.collecting.startup.helpers.model.root.money.polish;

import lombok.ToString;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

@ToString
public class ColMyMoney1 extends StartupCollection<CatPolish> {
    public final StartupElement<ColMyMoney1> element1;
    public final StartupElement<ColMyMoney1> element2;
    public final StartupElement<ColMyMoney1> element3;

    public ColMyMoney1(final CatPolish category) {
        super(ca.usr1, category, "Mój hajs");
        setImage("sample/sample_pln100.jpg");
        addAttribute(category.getParent().colAttrCountry, "Polska");
        addAttribute(category.getParent().colAttrYearFrom, "1900");
        addAttribute(root.colAttrDetails, "Moja piękna kolekcja banknotów, które do tej pory zebrałem (Ciągle rozwijana)");

        class Element1 extends StartupElement<ColMyMoney1> {
            public Element1(final ColMyMoney1 collection) {
                super(collection, "Złotówka");
                addAttribute(category.getParent().eleAttrValue, "1");
                addAttribute(category.getParent().eleAttrReleaseYear, "2003");
            }
        }
        element1 = new Element1(this);

        class Element2 extends StartupElement<ColMyMoney1> {
            public Element2(final ColMyMoney1 collection) {
                super(collection, "Dwa złote");
                addAttribute(category.getParent().eleAttrValue, "2");
                addAttribute(category.getParent().eleAttrReleaseYear, "2003");
            }
        }
        element2 = new Element2(this);

        class Element3 extends StartupElement<ColMyMoney1> {
            public Element3(final ColMyMoney1 collection) {
                super(collection, "Piątak");
                addAttribute(category.getParent().eleAttrValue, "5");
                addAttribute(category.getParent().eleAttrReleaseYear, "2001");
            }
        }
        element3 = new Element3(this);
    }
}
