package net.kawinski.collecting.startup.helpers.model.root.money;

import lombok.ToString;
import net.kawinski.collecting.Resources;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.model.root.CatMoney;
import net.kawinski.collecting.startup.helpers.model.root.money.polish.CatBanknotes;
import net.kawinski.collecting.startup.helpers.model.root.money.polish.CatCoins;
import net.kawinski.collecting.startup.helpers.model.root.money.polish.ColMyMoney1;
import net.kawinski.collecting.startup.helpers.model.root.money.polish.ColMyMoney2;
import net.kawinski.collecting.startup.helpers.model.root.money.polish.ColMyMoney3;

@ToString
public class CatPolish extends StartupCategory<CatMoney> {
    public final CatCoins catCoins;
    public final CatBanknotes catBanknotes;
    public final ColMyMoney1 colMyMoney1;
    public final ColMyMoney2 colMyMoney2;
    public final ColMyMoney3 colMyMoney3;

    public CatPolish(final CatMoney parent) {
        super(parent, "Polskie", true);
        this.catCoins = new CatCoins(this);
        this.catBanknotes = new CatBanknotes(this);
        this.colMyMoney1 = new ColMyMoney1(this);
        this.colMyMoney2 = new ColMyMoney2(this);
        this.colMyMoney3 = new ColMyMoney3(this);
    }
}
