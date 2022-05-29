package net.kawinski.collecting.startup.helpers.model.root.money.foreign;

import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.model.root.money.CatForeign;

public class ColMyForeignMoney3 extends StartupCollection<CatForeign> {

    public ColMyForeignMoney3(final CatForeign category) {
        super(ca.usr3, category, "Jeny");
        addAttribute(category.getParent().colAttrCountry, "Japonia");
        addAttribute(category.getParent().colAttrYearFrom, "1000");
        addAttribute(root.colAttrDetails, "Lubie Jeny");
    }
}
