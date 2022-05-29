package net.kawinski.collecting.startup.helpers.model.root.money.polish;

import lombok.ToString;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.money.CatPolish;

@ToString
public class ColMyMoney3 extends StartupCollection<CatPolish> {
    public final StartupElement<ColMyMoney3> element1;

    public ColMyMoney3(final CatPolish category) {
        super(ca.usr3, category, "Jego hajs");
        setImage("sample/sample_pln100v2.jpg");
        addAttribute(category.getParent().colAttrCountry, "Polska");
        addAttribute(category.getParent().colAttrYearFrom, "1981");
        addAttribute(category.getParent().colAttrYearFrom, "2011");
        addAttribute(root.colAttrDetails, "Znalezione na chodniku w drodze do pracy w okresie 1991-2011");

        class Element1 extends StartupElement<ColMyMoney3> {
            public Element1(final ColMyMoney3 collection) {
                super(collection, "Pisiont groszy");
                addAttribute(category.getParent().eleAttrValue, "50");
                addAttribute(category.getParent().eleAttrReleaseYear, "1995");
                addAttribute(root.eleAttrDetails, "Klasyk");
            }
        }
        element1 = new Element1(this);
    }
}
