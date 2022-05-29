package net.kawinski.collecting.startup.helpers.model.root.caps;

import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.base.StartupObject;
import net.kawinski.collecting.startup.helpers.model.root.CatCaps;

import java.util.Random;

public class ColCaps {
    private final Random random = new Random(42);
    private final CatCaps catCaps;
    private final StartupCollection<CatCaps> collection1;
    private final StartupCollection<CatCaps> collection2;
    private final StartupCollection<CatCaps> collection3;

    public ColCaps(final CatCaps catCaps) {
        this.catCaps = catCaps;
        collection1 = newCollection(StartupObject.ca.usr1, "Moje kapsle", "Caps collection small", false);
        collection2 = newCollection(StartupObject.ca.usr2, "Kapsle usr2", "Caps collection small (2)", false);
        collection3 = newCollection(StartupObject.ca.usr3, "Kolekcja kapsli", "Caps collection small (3)", true);
        createElements();
    }

    private void createElements() {
        newElement(collection1, "1 na 100");
        newElement(collection1, "Almdudler");
        newElement(collection1, "B");
        newElement(collection1, "Bacardi Green Aple", true);
        newElement(collection1, "Bacardi Pineapple");
        newElement(collection1, "Becks (2)");
        newElement(collection1, "Becks");
        newElement(collection1, "Birra Moretti (2)");
        newElement(collection1, "Birra Moretti");
        newElement(collection1, "Blue");
        newElement(collection1, "Brewdog black", true);
        newElement(collection1, "Brewdog green", true);
        newElement(collection1, "Brinkhoffs");
        newElement(collection1, "Brok");
        newElement(collection1, "Camparisoda");
        newElement(collection1, "CH");
        newElement(collection1, "clover sun");
        newElement(collection1, "clover white red");
        newElement(collection1, "clover white");
        newElement(collection1, "Coca Cola Classic");
        newElement(collection2, "Coca Cola Zero", true);
        newElement(collection2, "Cornelius Black", true);
        newElement(collection2, "Cornelius Yellow");
        newElement(collection2, "Corona Extra");
        newElement(collection2, "Crodino Twist");
        newElement(collection2, "Crodino");
        newElement(collection2, "Desperados Black");
        newElement(collection2, "Desperados Red");
        newElement(collection2, "Dziki Sad", true);
        newElement(collection2, "Efes");
        newElement(collection2, "Erdinger Weissbrau zero");
        newElement(collection2, "Erdinger Weissbrau");
        newElement(collection2, "Green");
        newElement(collection2, "Grimbergen");
        newElement(collection2, "Haake Beck Pils");
        newElement(collection2, "Halleroder");
        newElement(collection2, "Heineken (2)", true);
        newElement(collection2, "Heineken", true);
        newElement(collection2, "Hoegaarden", true);
        newElement(collection2, "Jever Pilsener");
        newElement(collection2, "Johannes");
        newElement(collection2, "Karmi (2)");
        newElement(collection2, "Karmi");
        newElement(collection2, "Kinley");
        newElement(collection2, "Ksiazece (2)", true);
        newElement(collection2, "Ksiazece (3)");
        newElement(collection2, "Ksiazece");
        newElement(collection2, "Lete");
        newElement(collection2, "Lindener Spezial");
        newElement(collection2, "Lipton Ice Tea", true);
        newElement(collection3, "Lwowek");
        newElement(collection3, "Manufaktura Piwna");
        newElement(collection3, "Namyslow Pils");
        newElement(collection3, "Okocim");
        newElement(collection3, "P");
        newElement(collection3, "Paulaner Munchen");
        newElement(collection3, "Pepsi (2)");
        newElement(collection3, "Pepsi");
        newElement(collection3, "Prickelnd");
        newElement(collection3, "Primator P");
        newElement(collection3, "Primator W");
        newElement(collection3, "Przymierz sie do nagrod");
        newElement(collection3, "Rauch");
        newElement(collection3, "Redds");
        newElement(collection3, "Rotlicht", true);
        newElement(collection3, "Schweppes Red", true);
        newElement(collection3, "Schweppes Yellow");
        newElement(collection3, "Somersby (2)");
        newElement(collection3, "Somersby");
        newElement(collection3, "Sprite Blue", true);
        newElement(collection3, "Sprite Green", true);
        newElement(collection3, "Tequila Flavoured Gold");
        newElement(collection3, "Tequila Flavoured Green");
        newElement(collection3, "Tequila Flavoured Red");
        newElement(collection3, "Toma");
        newElement(collection3, "Twist Off Black", true);
        newElement(collection3, "Twist Off Blue", true);
        newElement(collection3, "Twist Off White");
        newElement(collection3, "Tyskie Gronie");
        newElement(collection3, "Tyskie Przejdzmy na TY");
        newElement(collection3, "Tyskie");
        newElement(collection3, "Velkopopovicky Kozel");
        newElement(collection3, "Warka Classic", true);
        newElement(collection3, "Warka Radler");
        newElement(collection3, "White");
        newElement(collection3, "X 1603639732541");
        newElement(collection3, "X 1603639734077");
        newElement(collection3, "X 1603639740585");
        newElement(collection3, "X Bull");
        newElement(collection3, "X Flower");
        newElement(collection3, "X Hop");
        newElement(collection3, "Yellow (2)");
        newElement(collection3, "Yellow");
        newElement(collection3, "Zipfer Marzen");
        newElement(collection3, "Zlote Lwy");
        newElement(collection3, "Zubr (2)", true);
        newElement(collection3, "Zubr (3)", true);
        newElement(collection3, "Zubr (4)");
        newElement(collection3, "Zubr (5)");
        newElement(collection3, "Zubr");

    }

    private StartupCollection<CatCaps> newCollection(final User owner, final String name, final String imageName, boolean onSale) {
        final StartupCollection<CatCaps> collection = new StartupCollection<CatCaps>(owner, catCaps, name) {};
        collection.setImage("caps/" + imageName + ".jpg");
        final int from = random.nextInt(100) + 1900;
        final int to = random.nextInt(20) + from;
        collection.addAttribute(catCaps.colAttrYearFrom, Integer.toString(from));
        collection.addAttribute(catCaps.colAttrYearTo, Integer.toString(to));
        if(onSale) {
            collection.addAttribute(StartupObject.root.colAttrOnSale, "true");
        }
//        collections.add(collection);
        return collection;
    }

    private void newElement(final StartupCollection<CatCaps> owner, final String imageName) {
        newElement(owner, imageName, false);
    }

    private void newElement(final StartupCollection<CatCaps> owner, final String imageName, boolean onSale) {
        final StartupElement<StartupCollection<CatCaps>> element = new StartupElement<StartupCollection<CatCaps>>(owner, imageName) {};
        element.setImage("caps/" + imageName + ".jpg");
        final int from = random.nextInt(100) + 1900;
        final int to = random.nextInt(20) + from;
        element.addAttribute(catCaps.eleAttrYearFrom, Integer.toString(from));
        element.addAttribute(catCaps.eleAttrYearFrom, Integer.toString(to));
        if(onSale) {
            element.addAttribute(StartupObject.root.eleAttrOnSale, "true");
        }
//        elements.add(element);
    }

}
