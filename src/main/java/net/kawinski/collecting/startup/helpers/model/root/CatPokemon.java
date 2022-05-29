package net.kawinski.collecting.startup.helpers.model.root;

import net.kawinski.collecting.data.model.AttributeType;
import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.startup.helpers.StartupResources;
import net.kawinski.collecting.startup.helpers.base.StartupCategory;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.CatRoot;
import net.kawinski.collecting.startup.helpers.model.root.pokemon.ColPokedex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CatPokemon extends StartupCategory<CatRoot> {
    public final ElementAttributeTemplate eleAttrNationalNumber;
    public final ElementAttributeTemplate eleAttrType;
    public final ElementAttributeTemplate eleAttrSpecies;
    public final ElementAttributeTemplate eleAttrHeight;
    public final ElementAttributeTemplate eleAttrWeight;
    public final ElementAttributeTemplate eleAttrAbility;
    public final ElementAttributeTemplate eleAttrEvolvedFrom;
    public final ElementAttributeTemplate eleAttrEvolvesInto;
    public final ElementAttributeTemplate eleAttrBaseHp;
    public final ElementAttributeTemplate eleAttrBaseAttack;
    public final ElementAttributeTemplate eleAttrBaseDefense;
    public final ElementAttributeTemplate eleAttrBaseAttackSpeed;
    public final ElementAttributeTemplate eleAttrBaseDefenseSpeed;
    public final ElementAttributeTemplate eleAttrBaseSpeed;
    public final ElementAttributeTemplate eleAttrBaseTotal;

    public final ColPokedex colPokedex;

    public final StartupCategory<CatPokemon> catPokemons1;
    public final StartupCategory<CatPokemon> catPokemons2;
    public final StartupCategory<CatPokemon> catPokemons3;
    public final List<StartupCategory<CatPokemon>> subcategories = new ArrayList<>();
    public final List<StartupCollection<?>> testCollections = new ArrayList<>();
    public final List<Collection> testCollectionModels = new ArrayList<>();

    public CatPokemon(final CatRoot parent) {
        super(parent, "Pokémon", true);
        setIcon("categories/catPokemon.png");

        eleAttrNationalNumber = newEleAttr("Międzynarodowy numer identyfikacyjny", AttributeType.INTEGER, baseDispOrder + 1, true);
        eleAttrType = newEleAttr("Rodzaj", AttributeType.STRING, baseDispOrder + 2, true);
        eleAttrSpecies = newEleAttr("Gatunek", AttributeType.STRING, baseDispOrder + 3, true);
        eleAttrHeight = newEleAttr("Wysokość [m]", AttributeType.DECIMAL, baseDispOrder + 4, true);
        eleAttrWeight = newEleAttr("Waga [kg]", AttributeType.DECIMAL, baseDispOrder + 5, true);
        eleAttrAbility = newEleAttr("Umiejętność", AttributeType.STRING, baseDispOrder + 6, true);
        eleAttrEvolvedFrom = newEleAttr("Wyewoluowany z", AttributeType.STRING, baseDispOrder + 7, true);
        eleAttrEvolvesInto = newEleAttr("Ewoluuje w", AttributeType.STRING, baseDispOrder + 8, true);
        eleAttrBaseHp = newEleAttr("Początkowe HP", AttributeType.INTEGER, baseDispOrder + 11, true);
        eleAttrBaseAttack = newEleAttr("Początkowa siła ataku", AttributeType.INTEGER, baseDispOrder + 12, true);
        eleAttrBaseDefense = newEleAttr("Początkowa siła obrony", AttributeType.INTEGER, baseDispOrder + 13, true);
        eleAttrBaseAttackSpeed = newEleAttr("Początkowa szybkość ataku ", AttributeType.INTEGER, baseDispOrder + 14, true);
        eleAttrBaseDefenseSpeed = newEleAttr("Początkowa szybkość obrony", AttributeType.INTEGER, baseDispOrder + 15, true);
        eleAttrBaseSpeed = newEleAttr("Początkowa szybkość", AttributeType.INTEGER, baseDispOrder + 16, true);
        eleAttrBaseTotal = newEleAttr("Suma atrybutów początkowych", AttributeType.INTEGER, baseDispOrder + 19, true);

        colPokedex = new ColPokedex(this);

        catPokemons1 = new StartupCategory<CatPokemon>(this, "pokemons1", true) {};
        catPokemons2 = new StartupCategory<CatPokemon>(this, "pokemons2", true) {};
        catPokemons3 = new StartupCategory<CatPokemon>(this, "pokemons3", true) {};
        subcategories.addAll(Arrays.asList(catPokemons1, catPokemons2, catPokemons3));

        int testUserI = 0;
        for(User testUser : ca.testUsers) {
            generateTestUserCollections(testUserI++, testUser);
        }
    }

    private void generateTestUserCollections(int userIndex, User testUser) {
        int numCollections = 1;
        if(userIndex % 5 == 0) {
            numCollections++;
        }
        if(userIndex % 20 == 0) {
            numCollections++;
        }

        for(int colIndex = 0; colIndex < numCollections; ++colIndex) {
            generateTestUserCollection(colIndex, testUser);
        }
    }

    private void generateTestUserCollection(int colIndex, User testUser) {
        String colName = testUser.getName().split(" ")[0] + " pokemons " + (colIndex+1);
        StartupCategory<CatPokemon> category = subcategories.get(colIndex);
        StartupCollection<StartupCategory<CatPokemon>> testCol = new StartupCollection<StartupCategory<CatPokemon>>(
                testUser, category, colName, colPokedex.getModel()
        ) {};
        testCollections.add(testCol);
        testCollectionModels.add(testCol.getModel());
        testCol.setImage(StartupResources.getRandomCatImagePathForUpload());
        testCol.setIcon(null);
        if(random.nextBoolean()) {
            long randomEpoch = Math.abs(random.nextLong()) % new Date().getTime();
            Date randomDate = new Date(randomEpoch);
            testCol.addAttribute(root.colAttrFound, AttributeType.DATETIME.getDatetimeAttributeType().toString(randomDate));
        }
        if(random.nextBoolean()) {
            testCol.addAttribute(root.colAttrOnSale, AttributeType.BOOLEAN.getBooleanAttributeType().toString(random.nextBoolean()));
        }
        if(random.nextBoolean()) {
            testCol.addAttribute(root.colAttrDetails, "abcdef" + random.nextInt());
        }

        List<StartupElement<ColPokedex>> shuffledPokedexElements = new ArrayList<>(colPokedex.pokemonsByNN.values());
        Collections.shuffle(shuffledPokedexElements);

        int numElements = random.nextInt(100);
        if(random.nextInt(10) != 0) {
            numElements /= 5;
        }
        for(int elementIndex = 0; elementIndex < numElements; ++elementIndex) {
            generateElement(testCol, shuffledPokedexElements.get(elementIndex));
        }
    }

    private void generateElement(StartupCollection<StartupCategory<CatPokemon>> testCol, StartupElement<ColPokedex> eleBase) {
        StartupElement<StartupCollection<StartupCategory<CatPokemon>>> element = new StartupElement<>(
                testCol, eleBase.getModel().getName(), eleBase.getModel()
        );
    }
}
