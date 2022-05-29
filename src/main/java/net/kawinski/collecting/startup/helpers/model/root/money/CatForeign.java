package net.kawinski.collecting.startup.helpers.model.root.money;

import net.kawinski.collecting.Resources;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatMoney;
import net.kawinski.collecting.startup.helpers.model.root.money.foreign.ColMyForeignMoney1;
import net.kawinski.collecting.startup.helpers.model.root.money.foreign.ColMyForeignMoney2;
import net.kawinski.collecting.startup.helpers.model.root.money.foreign.ColMyForeignMoney3;

public class CatForeign extends StartupCategory<CatMoney> {
    public final ColMyForeignMoney1 colMyForeignMoney1;
    public final ColMyForeignMoney2 colMyForeignMoney2;
    public final ColMyForeignMoney3 colMyForeignMoney3;

    public CatForeign(final CatMoney parent) {
        super(parent, "Zagraniczne", true);
        colMyForeignMoney1 = new ColMyForeignMoney1(this);
        colMyForeignMoney2 = new ColMyForeignMoney2(this);
        colMyForeignMoney3 = new ColMyForeignMoney3(this);
    }
}
