package net.kawinski.collecting.startup.helpers.model.root.pokemon;

import lombok.ToString;
import net.kawinski.collecting.startup.helpers.base.StartupCollection;
import net.kawinski.collecting.startup.helpers.base.StartupElement;
import net.kawinski.collecting.startup.helpers.model.root.CatPokemon;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("java:S1192") // Most data is scrapped and because of that - duplicated
@ToString
public class ColPokedex extends StartupCollection<CatPokemon> {
    public final Map<String, StartupElement<ColPokedex>> pokemonsByNN = new HashMap<>();

    public ColPokedex(final CatPokemon category) {
        super(ca.usrAdmin, category, "Pokédex", true);
        setIcon("pokemon/pokedex_icon.png");
        setImage("pokemon/pokedex.png");
        addAttribute(root.colAttrBarcode, "1500100900");
        addAttribute(root.colAttrFound, "1996-02-01T06:42:56+00:00");
        addAttribute(root.colAttrOnSale, "false");
        addAttribute(root.colAttrDetails, "Oficjalna lista wszystkich dotąd poznanych Pokémonów");
        addAttribute(root.colAttrExtraImage, newUpload("pokemon/pokedex-extra.jpg").getFileName());

        addPokemons();
    }

    private static class ElePokemonBase extends StartupElement<ColPokedex> {
        public ElePokemonBase(final ColPokedex collection, final String name) {
            super(collection, name);
        }

        protected final void addBase(final String icon, final String image, final String nationalNumber, final String species, final String height, final String weight) {
            setIcon(icon);
            setImage(image);
            addAttribute(getCollection().getCategory().eleAttrNationalNumber, nationalNumber);
            addAttribute(getCollection().getCategory().eleAttrSpecies, species);
            addAttribute(getCollection().getCategory().eleAttrHeight, height);
            addAttribute(getCollection().getCategory().eleAttrWeight, weight);
        }

        protected final void addTypes(final String... types) {
            addAttribute(getCollection().getCategory().eleAttrType, types);
        }

        protected final void addAbilities(final String... abilities) {
            addAttribute(getCollection().getCategory().eleAttrAbility, abilities);
        }

        protected final void addEvolution(final String from, final String into) {
            addAttribute(getCollection().getCategory().eleAttrEvolvedFrom, from);
            addAttribute(getCollection().getCategory().eleAttrEvolvesInto, into);
        }

        protected final void addStats(final String total, final String hp, final String att, final String def, final String attSpeed, final String defSpeed, final String speed) {
            addAttribute(getCollection().getCategory().eleAttrBaseTotal, total);
            addAttribute(getCollection().getCategory().eleAttrBaseHp, hp);
            addAttribute(getCollection().getCategory().eleAttrBaseAttack, att);
            addAttribute(getCollection().getCategory().eleAttrBaseDefense, def);
            addAttribute(getCollection().getCategory().eleAttrBaseAttackSpeed, attSpeed);
            addAttribute(getCollection().getCategory().eleAttrBaseDefenseSpeed, defSpeed);
            addAttribute(getCollection().getCategory().eleAttrBaseSpeed, speed);
        }

        protected final void addDetails(final String details) {
            addAttribute(getCollection().getCategory().getParent().eleAttrDetails, details);
        }
    }

    private void addPokemons() {
        class Pokemon001 extends ElePokemonBase {
            public Pokemon001(final ColPokedex collection) {
                super(collection, "Bulbasaur");
                addBase("pokemon/pokemon_001_icon.png", "pokemon/pokemon_001.png", "001", "Seed Pokémon", "0.7", "6.9");
                addTypes("Grass", "Poison");
                addAbilities("Overgrow", "Chlorophyll");
                addEvolution("None", "Ivysaur");
                addStats("318", "45", "49", "49", "65", "65", "45");
                addDetails("Bulbasaur is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Seed Pokémon.");
            }
        }
        pokemonsByNN.put("001", new Pokemon001(this));

        class Pokemon002 extends ElePokemonBase {
            public Pokemon002(final ColPokedex collection) {
                super(collection, "Ivysaur");
                addBase("pokemon/pokemon_002_icon.png", "pokemon/pokemon_002.png", "002", "Seed Pokémon", "1.0", "13.0");
                addTypes("Grass", "Poison");
                addAbilities("Overgrow", "Chlorophyll");
                addEvolution("Bulbasaur", "Venusaur");
                addStats("405", "60", "62", "63", "80", "80", "60");
                addDetails("Ivysaur is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Seed Pokémon.");
            }
        }
        pokemonsByNN.put("002", new Pokemon002(this));

        class Pokemon003 extends ElePokemonBase {
            public Pokemon003(final ColPokedex collection) {
                super(collection, "Venusaur");
                addBase("pokemon/pokemon_003_icon.png", "pokemon/pokemon_003.png", "003", "Seed Pokémon", "2.0", "100.0");
                addTypes("Grass", "Poison");
                addAbilities("Overgrow", "Chlorophyll");
                addEvolution("Ivysaur", "None");
                addStats("525", "80", "82", "83", "100", "100", "80");
                addDetails("Venusaur is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Seed Pokémon.");
            }
        }
        pokemonsByNN.put("003", new Pokemon003(this));

        class Pokemon004 extends ElePokemonBase {
            public Pokemon004(final ColPokedex collection) {
                super(collection, "Charmander");
                addBase("pokemon/pokemon_004_icon.png", "pokemon/pokemon_004.png", "004", "Lizard Pokémon", "0.6", "8.5");
                addTypes("Fire");
                addAbilities("Blaze", "Solar Power");
                addEvolution("None", "Charmeleon");
                addStats("309", "39", "52", "43", "60", "50", "65");
                addDetails("Charmander is a Fire type Pokémon introduced in Generation 1. It is known as the Lizard Pokémon.");
            }
        }
        pokemonsByNN.put("004", new Pokemon004(this));

        class Pokemon005 extends ElePokemonBase {
            public Pokemon005(final ColPokedex collection) {
                super(collection, "Charmeleon");
                addBase("pokemon/pokemon_005_icon.png", "pokemon/pokemon_005.png", "005", "Flame Pokémon", "1.1", "19.0");
                addTypes("Fire");
                addAbilities("Blaze", "Solar Power");
                addEvolution("Charmander", "Charizard");
                addStats("405", "58", "64", "58", "80", "65", "80");
                addDetails("Charmeleon is a Fire type Pokémon introduced in Generation 1. It is known as the Flame Pokémon.");
            }
        }
        pokemonsByNN.put("005", new Pokemon005(this));

        class Pokemon006 extends ElePokemonBase {
            public Pokemon006(final ColPokedex collection) {
                super(collection, "Charizard");
                addBase("pokemon/pokemon_006_icon.png", "pokemon/pokemon_006.png", "006", "Flame Pokémon", "1.7", "90.5");
                addTypes("Fire", "Flying");
                addAbilities("Blaze", "Solar Power");
                addEvolution("Charmeleon", "None");
                addStats("534", "78", "84", "78", "109", "85", "100");
                addDetails("Charizard is a Fire/Flying type Pokémon introduced in Generation 1. It is known as the Flame Pokémon.");
            }
        }
        pokemonsByNN.put("006", new Pokemon006(this));

        class Pokemon007 extends ElePokemonBase {
            public Pokemon007(final ColPokedex collection) {
                super(collection, "Squirtle");
                addBase("pokemon/pokemon_007_icon.png", "pokemon/pokemon_007.png", "007", "Tiny Turtle Pokémon", "0.5", "9.0");
                addTypes("Water");
                addAbilities("Torrent", "Rain Dish");
                addEvolution("None", "Wartortle");
                addStats("314", "44", "48", "65", "50", "64", "43");
                addDetails("Squirtle is a Water type Pokémon introduced in Generation 1. It is known as the Tiny Turtle Pokémon.");
            }
        }
        pokemonsByNN.put("007", new Pokemon007(this));

        class Pokemon008 extends ElePokemonBase {
            public Pokemon008(final ColPokedex collection) {
                super(collection, "Wartortle");
                addBase("pokemon/pokemon_008_icon.png", "pokemon/pokemon_008.png", "008", "Turtle Pokémon", "1.0", "22.5");
                addTypes("Water");
                addAbilities("Torrent", "Rain Dish");
                addEvolution("Squirtle", "Blastoise");
                addStats("405", "59", "63", "80", "65", "80", "58");
                addDetails("Wartortle is a Water type Pokémon introduced in Generation 1. It is known as the Turtle Pokémon.");
            }
        }
        pokemonsByNN.put("008", new Pokemon008(this));

        class Pokemon009 extends ElePokemonBase {
            public Pokemon009(final ColPokedex collection) {
                super(collection, "Blastoise");
                addBase("pokemon/pokemon_009_icon.png", "pokemon/pokemon_009.png", "009", "Shellfish Pokémon", "1.6", "85.5");
                addTypes("Water");
                addAbilities("Torrent", "Rain Dish");
                addEvolution("Wartortle", "None");
                addStats("530", "79", "83", "100", "85", "105", "78");
                addDetails("Blastoise is a Water type Pokémon introduced in Generation 1. It is known as the Shellfish Pokémon.");
            }
        }
        pokemonsByNN.put("009", new Pokemon009(this));

        class Pokemon010 extends ElePokemonBase {
            public Pokemon010(final ColPokedex collection) {
                super(collection, "Caterpie");
                addBase("pokemon/pokemon_010_icon.png", "pokemon/pokemon_010.png", "010", "Worm Pokémon", "0.3", "2.9");
                addTypes("Bug");
                addAbilities("Shield Dust", "Run Away");
                addEvolution("None", "Metapod");
                addStats("195", "45", "30", "35", "20", "20", "45");
                addDetails("Caterpie is a Bug type Pokémon introduced in Generation 1. It is known as the Worm Pokémon.");
            }
        }
        pokemonsByNN.put("010", new Pokemon010(this));

        class Pokemon011 extends ElePokemonBase {
            public Pokemon011(final ColPokedex collection) {
                super(collection, "Metapod");
                addBase("pokemon/pokemon_011_icon.png", "pokemon/pokemon_011.png", "011", "Cocoon Pokémon", "0.7", "9.9");
                addTypes("Bug");
                addAbilities("Shed Skin");
                addEvolution("Caterpie", "Butterfree");
                addStats("205", "50", "20", "55", "25", "25", "30");
                addDetails("Metapod is a Bug type Pokémon introduced in Generation 1. It is known as the Cocoon Pokémon.");
            }
        }
        pokemonsByNN.put("011", new Pokemon011(this));

        class Pokemon012 extends ElePokemonBase {
            public Pokemon012(final ColPokedex collection) {
                super(collection, "Butterfree");
                addBase("pokemon/pokemon_012_icon.png", "pokemon/pokemon_012.png", "012", "Butterfly Pokémon", "1.1", "32.0");
                addTypes("Bug", "Flying");
                addAbilities("Compound Eyes", "Tinted Lens");
                addEvolution("Metapod", "None");
                addStats("395", "60", "45", "50", "90", "80", "70");
                addDetails("Butterfree is a Bug/Flying type Pokémon introduced in Generation 1. It is known as the Butterfly Pokémon.");
            }
        }
        pokemonsByNN.put("012", new Pokemon012(this));

        class Pokemon013 extends ElePokemonBase {
            public Pokemon013(final ColPokedex collection) {
                super(collection, "Weedle");
                addBase("pokemon/pokemon_013_icon.png", "pokemon/pokemon_013.png", "013", "Hairy Bug Pokémon", "0.3", "3.2");
                addTypes("Bug", "Poison");
                addAbilities("Shield Dust", "Run Away");
                addEvolution("None", "Kakuna");
                addStats("195", "40", "35", "30", "20", "20", "50");
                addDetails("Weedle is a Bug/Poison type Pokémon introduced in Generation 1. It is known as the Hairy Bug Pokémon.");
            }
        }
        pokemonsByNN.put("013", new Pokemon013(this));

        class Pokemon014 extends ElePokemonBase {
            public Pokemon014(final ColPokedex collection) {
                super(collection, "Kakuna");
                addBase("pokemon/pokemon_014_icon.png", "pokemon/pokemon_014.png", "014", "Cocoon Pokémon", "0.6", "10.0");
                addTypes("Bug", "Poison");
                addAbilities("Shed Skin");
                addEvolution("Weedle", "Beedrill");
                addStats("205", "45", "25", "50", "25", "25", "35");
                addDetails("Kakuna is a Bug/Poison type Pokémon introduced in Generation 1. It is known as the Cocoon Pokémon.");
            }
        }
        pokemonsByNN.put("014", new Pokemon014(this));

        class Pokemon015 extends ElePokemonBase {
            public Pokemon015(final ColPokedex collection) {
                super(collection, "Beedrill");
                addBase("pokemon/pokemon_015_icon.png", "pokemon/pokemon_015.png", "015", "Poison Bee Pokémon", "1.0", "29.5");
                addTypes("Bug", "Poison");
                addAbilities("Swarm", "Sniper");
                addEvolution("Kakuna", "None");
                addStats("395", "65", "90", "40", "45", "80", "75");
                addDetails("Beedrill is a Bug/Poison type Pokémon introduced in Generation 1. It is known as the Poison Bee Pokémon.");
            }
        }
        pokemonsByNN.put("015", new Pokemon015(this));

        class Pokemon016 extends ElePokemonBase {
            public Pokemon016(final ColPokedex collection) {
                super(collection, "Pidgey");
                addBase("pokemon/pokemon_016_icon.png", "pokemon/pokemon_016.png", "016", "Tiny Bird Pokémon", "0.3", "1.8");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Tangled Feet", "Big Pecks");
                addEvolution("None", "Pidgeotto");
                addStats("251", "40", "45", "40", "35", "35", "56");
                addDetails("Pidgey is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Tiny Bird Pokémon.");
            }
        }
        pokemonsByNN.put("016", new Pokemon016(this));

        class Pokemon017 extends ElePokemonBase {
            public Pokemon017(final ColPokedex collection) {
                super(collection, "Pidgeotto");
                addBase("pokemon/pokemon_017_icon.png", "pokemon/pokemon_017.png", "017", "Bird Pokémon", "1.1", "30.0");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Tangled Feet", "Big Pecks");
                addEvolution("Pidgey", "Pidgeot");
                addStats("349", "63", "60", "55", "50", "50", "71");
                addDetails("Pidgeotto is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Bird Pokémon.");
            }
        }
        pokemonsByNN.put("017", new Pokemon017(this));

        class Pokemon018 extends ElePokemonBase {
            public Pokemon018(final ColPokedex collection) {
                super(collection, "Pidgeot");
                addBase("pokemon/pokemon_018_icon.png", "pokemon/pokemon_018.png", "018", "Bird Pokémon", "1.5", "39.5");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Tangled Feet", "Big Pecks");
                addEvolution("Pidgeotto", "None");
                addStats("479", "83", "80", "75", "70", "70", "101");
                addDetails("Pidgeot is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Bird Pokémon.");
            }
        }
        pokemonsByNN.put("018", new Pokemon018(this));

        class Pokemon019 extends ElePokemonBase {
            public Pokemon019(final ColPokedex collection) {
                super(collection, "Rattata");
                addBase("pokemon/pokemon_019_icon.png", "pokemon/pokemon_019.png", "019", "Mouse Pokémon", "0.3", "3.5");
                addTypes("Normal");
                addAbilities("Run Away", "Guts", "Hustle");
                addEvolution("Unknown", "Unknown");
                addStats("253", "30", "56", "35", "25", "35", "72");
                addDetails("Rattata is a Normal type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("019", new Pokemon019(this));

        class Pokemon020 extends ElePokemonBase {
            public Pokemon020(final ColPokedex collection) {
                super(collection, "Raticate");
                addBase("pokemon/pokemon_020_icon.png", "pokemon/pokemon_020.png", "020", "Mouse Pokémon", "0.7", "18.5");
                addTypes("Normal");
                addAbilities("Run Away", "Guts", "Hustle");
                addEvolution("Unknown", "Unknown");
                addStats("413", "55", "81", "60", "50", "70", "97");
                addDetails("Raticate is a Normal type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("020", new Pokemon020(this));

        class Pokemon021 extends ElePokemonBase {
            public Pokemon021(final ColPokedex collection) {
                super(collection, "Spearow");
                addBase("pokemon/pokemon_021_icon.png", "pokemon/pokemon_021.png", "021", "Tiny Bird Pokémon", "0.3", "2.0");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Sniper");
                addEvolution("Unknown", "Unknown");
                addStats("262", "40", "60", "30", "31", "31", "70");
                addDetails("Spearow is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Tiny Bird Pokémon.");
            }
        }
        pokemonsByNN.put("021", new Pokemon021(this));

        class Pokemon022 extends ElePokemonBase {
            public Pokemon022(final ColPokedex collection) {
                super(collection, "Fearow");
                addBase("pokemon/pokemon_022_icon.png", "pokemon/pokemon_022.png", "022", "Beak Pokémon", "1.2", "38.0");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Sniper");
                addEvolution("Unknown", "Unknown");
                addStats("442", "65", "90", "65", "61", "61", "100");
                addDetails("Fearow is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Beak Pokémon.");
            }
        }
        pokemonsByNN.put("022", new Pokemon022(this));

        class Pokemon023 extends ElePokemonBase {
            public Pokemon023(final ColPokedex collection) {
                super(collection, "Ekans");
                addBase("pokemon/pokemon_023_icon.png", "pokemon/pokemon_023.png", "023", "Snake Pokémon", "2.0", "6.9");
                addTypes("Poison");
                addAbilities("Intimidate", "Shed Skin", "Unnerve");
                addEvolution("Unknown", "Unknown");
                addStats("288", "35", "60", "44", "40", "54", "55");
                addDetails("Ekans is a Poison type Pokémon introduced in Generation 1. It is known as the Snake Pokémon.");
            }
        }
        pokemonsByNN.put("023", new Pokemon023(this));

        class Pokemon024 extends ElePokemonBase {
            public Pokemon024(final ColPokedex collection) {
                super(collection, "Arbok");
                addBase("pokemon/pokemon_024_icon.png", "pokemon/pokemon_024.png", "024", "Cobra Pokémon", "3.5", "65.0");
                addTypes("Poison");
                addAbilities("Intimidate", "Shed Skin", "Unnerve");
                addEvolution("Unknown", "Unknown");
                addStats("448", "60", "95", "69", "65", "79", "80");
                addDetails("Arbok is a Poison type Pokémon introduced in Generation 1. It is known as the Cobra Pokémon.");
            }
        }
        pokemonsByNN.put("024", new Pokemon024(this));

        class Pokemon025 extends ElePokemonBase {
            public Pokemon025(final ColPokedex collection) {
                super(collection, "Pikachu");
                addBase("pokemon/pokemon_025_icon.png", "pokemon/pokemon_025.png", "025", "Mouse Pokémon", "0.4", "6.0");
                addTypes("Electric");
                addAbilities("Static", "Lightning Rod");
                addEvolution("Unknown", "Unknown");
                addStats("320", "35", "55", "40", "50", "50", "90");
                addDetails("Pikachu is an Electric type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("025", new Pokemon025(this));

        class Pokemon026 extends ElePokemonBase {
            public Pokemon026(final ColPokedex collection) {
                super(collection, "Raichu");
                addBase("pokemon/pokemon_026_icon.png", "pokemon/pokemon_026.png", "026", "Mouse Pokémon", "0.8", "30.0");
                addTypes("Electric");
                addAbilities("Static", "Lightning Rod");
                addEvolution("Unknown", "Unknown");
                addStats("485", "60", "90", "55", "90", "80", "110");
                addDetails("Raichu is an Electric type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("026", new Pokemon026(this));

        class Pokemon027 extends ElePokemonBase {
            public Pokemon027(final ColPokedex collection) {
                super(collection, "Sandshrew");
                addBase("pokemon/pokemon_027_icon.png", "pokemon/pokemon_027.png", "027", "Mouse Pokémon", "0.6", "12.0");
                addTypes("Ground");
                addAbilities("Sand Veil", "Sand Rush");
                addEvolution("Unknown", "Unknown");
                addStats("300", "50", "75", "85", "20", "30", "40");
                addDetails("Sandshrew is a Ground type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("027", new Pokemon027(this));

        class Pokemon028 extends ElePokemonBase {
            public Pokemon028(final ColPokedex collection) {
                super(collection, "Sandslash");
                addBase("pokemon/pokemon_028_icon.png", "pokemon/pokemon_028.png", "028", "Mouse Pokémon", "1.0", "29.5");
                addTypes("Ground");
                addAbilities("Sand Veil", "Sand Rush");
                addEvolution("Unknown", "Unknown");
                addStats("450", "75", "100", "110", "45", "55", "65");
                addDetails("Sandslash is a Ground type Pokémon introduced in Generation 1. It is known as the Mouse Pokémon.");
            }
        }
        pokemonsByNN.put("028", new Pokemon028(this));

        class Pokemon029 extends ElePokemonBase {
            public Pokemon029(final ColPokedex collection) {
                super(collection, "Nidoran♀");
                addBase("pokemon/pokemon_029_icon.png", "pokemon/pokemon_029.png", "029", "Poison Pin Pokémon", "0.4", "7.0");
                addTypes("Poison");
                addAbilities("Poison Point", "Rivalry", "Hustle");
                addEvolution("None", "Nidorina");
                addStats("275", "55", "47", "52", "40", "40", "41");
                addDetails("Nidoran♀ is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Pin Pokémon.");
            }
        }
        pokemonsByNN.put("029", new Pokemon029(this));

        class Pokemon030 extends ElePokemonBase {
            public Pokemon030(final ColPokedex collection) {
                super(collection, "Nidorina");
                addBase("pokemon/pokemon_030_icon.png", "pokemon/pokemon_030.png", "030", "Poison Pin Pokémon", "0.8", "20.0");
                addTypes("Poison");
                addAbilities("Poison Point", "Rivalry", "Hustle");
                addEvolution("Nidoran♀", "Nidoqueen");
                addStats("365", "70", "62", "67", "55", "55", "56");
                addDetails("Nidorina is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Pin Pokémon.");
            }
        }
        pokemonsByNN.put("030", new Pokemon030(this));

        class Pokemon031 extends ElePokemonBase {
            public Pokemon031(final ColPokedex collection) {
                super(collection, "Nidoqueen");
                addBase("pokemon/pokemon_031_icon.png", "pokemon/pokemon_031.png", "031", "Drill Pokémon", "1.3", "60.0");
                addTypes("Poison", "Ground");
                addAbilities("Poison Point", "Rivalry", "Sheer Force");
                addEvolution("Nidorina", "None");
                addStats("505", "90", "92", "87", "75", "85", "76");
                addDetails("Nidoqueen is a Poison/Ground type Pokémon introduced in Generation 1. It is known as the Drill Pokémon.");
            }
        }
        pokemonsByNN.put("031", new Pokemon031(this));

        class Pokemon032 extends ElePokemonBase {
            public Pokemon032(final ColPokedex collection) {
                super(collection, "Nidoran♂");
                addBase("pokemon/pokemon_032_icon.png", "pokemon/pokemon_032.png", "032", "Poison Pin Pokémon", "0.5", "9.0");
                addTypes("Poison");
                addAbilities("Poison Point", "Rivalry", "Hustle");
                addEvolution("None", "Nidorino");
                addStats("273", "46", "57", "40", "40", "40", "50");
                addDetails("Nidoran♂ is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Pin Pokémon.");
            }
        }
        pokemonsByNN.put("032", new Pokemon032(this));

        class Pokemon033 extends ElePokemonBase {
            public Pokemon033(final ColPokedex collection) {
                super(collection, "Nidorino");
                addBase("pokemon/pokemon_033_icon.png", "pokemon/pokemon_033.png", "033", "Poison Pin Pokémon", "0.9", "19.5");
                addTypes("Poison");
                addAbilities("Poison Point", "Rivalry", "Hustle");
                addEvolution("Nidoran♂", "Nidoking");
                addStats("365", "61", "72", "57", "55", "55", "65");
                addDetails("Nidorino is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Pin Pokémon.");
            }
        }
        pokemonsByNN.put("033", new Pokemon033(this));

        class Pokemon034 extends ElePokemonBase {
            public Pokemon034(final ColPokedex collection) {
                super(collection, "Nidoking");
                addBase("pokemon/pokemon_034_icon.png", "pokemon/pokemon_034.png", "034", "Drill Pokémon", "1.4", "62.0");
                addTypes("Poison", "Ground");
                addAbilities("Poison Point", "Rivalry", "Sheer Force");
                addEvolution("Nidorino", "None");
                addStats("505", "81", "102", "77", "85", "75", "85");
                addDetails("Nidoking is a Poison/Ground type Pokémon introduced in Generation 1. It is known as the Drill Pokémon.");
            }
        }
        pokemonsByNN.put("034", new Pokemon034(this));

        class Pokemon035 extends ElePokemonBase {
            public Pokemon035(final ColPokedex collection) {
                super(collection, "Clefairy");
                addBase("pokemon/pokemon_035_icon.png", "pokemon/pokemon_035.png", "035", "Fairy Pokémon", "0.6", "7.5");
                addTypes("Fairy");
                addAbilities("Cute Charm", "Magic Guard", "Friend Guard");
                addEvolution("Cleffa", "Clefable");
                addStats("323", "70", "45", "48", "60", "65", "35");
                addDetails("Clefairy is a Fairy type Pokémon introduced in Generation 1. It is known as the Fairy Pokémon.");
            }
        }
        pokemonsByNN.put("035", new Pokemon035(this));

        class Pokemon036 extends ElePokemonBase {
            public Pokemon036(final ColPokedex collection) {
                super(collection, "Clefable");
                addBase("pokemon/pokemon_036_icon.png", "pokemon/pokemon_036.png", "036", "Fairy Pokémon", "1.3", "40.0");
                addTypes("Fairy");
                addAbilities("Cute Charm", "Magic Guard", "Unaware");
                addEvolution("Clefairy", "None");
                addStats("483", "95", "70", "73", "95", "90", "60");
                addDetails("Clefable is a Fairy type Pokémon introduced in Generation 1. It is known as the Fairy Pokémon.");
            }
        }
        pokemonsByNN.put("036", new Pokemon036(this));

        class Pokemon037 extends ElePokemonBase {
            public Pokemon037(final ColPokedex collection) {
                super(collection, "Vulpix");
                addBase("pokemon/pokemon_037_icon.png", "pokemon/pokemon_037.png", "037", "Fox Pokémon", "0.6", "9.9");
                addTypes("Fire");
                addAbilities("Flash Fire", "Drought");
                addEvolution("Unknown", "Unknown");
                addStats("299", "38", "41", "40", "50", "65", "65");
                addDetails("Vulpix is a Fire type Pokémon introduced in Generation 1. It is known as the Fox Pokémon.");
            }
        }
        pokemonsByNN.put("037", new Pokemon037(this));

        class Pokemon038 extends ElePokemonBase {
            public Pokemon038(final ColPokedex collection) {
                super(collection, "Ninetales");
                addBase("pokemon/pokemon_038_icon.png", "pokemon/pokemon_038.png", "038", "Fox Pokémon", "1.1", "19.9");
                addTypes("Fire");
                addAbilities("Flash Fire", "Drought");
                addEvolution("Unknown", "Unknown");
                addStats("505", "73", "76", "75", "81", "100", "100");
                addDetails("Ninetales is a Fire type Pokémon introduced in Generation 1. It is known as the Fox Pokémon.");
            }
        }
        pokemonsByNN.put("038", new Pokemon038(this));

        class Pokemon039 extends ElePokemonBase {
            public Pokemon039(final ColPokedex collection) {
                super(collection, "Jigglypuff");
                addBase("pokemon/pokemon_039_icon.png", "pokemon/pokemon_039.png", "039", "Balloon Pokémon", "0.5", "5.5");
                addTypes("Normal", "Fairy");
                addAbilities("Cute Charm", "Competitive", "Friend Guard");
                addEvolution("Igglybuff", "Wigglytuff");
                addStats("270", "115", "45", "20", "45", "25", "20");
                addDetails("Jigglypuff is a Normal/Fairy type Pokémon introduced in Generation 1. It is known as the Balloon Pokémon.");
            }
        }
        pokemonsByNN.put("039", new Pokemon039(this));

        class Pokemon040 extends ElePokemonBase {
            public Pokemon040(final ColPokedex collection) {
                super(collection, "Wigglytuff");
                addBase("pokemon/pokemon_040_icon.png", "pokemon/pokemon_040.png", "040", "Balloon Pokémon", "1.0", "12.0");
                addTypes("Normal", "Fairy");
                addAbilities("Cute Charm", "Competitive", "Frisk");
                addEvolution("Jigglypuff", "None");
                addStats("435", "140", "70", "45", "85", "50", "45");
                addDetails("Wigglytuff is a Normal/Fairy type Pokémon introduced in Generation 1. It is known as the Balloon Pokémon.");
            }
        }
        pokemonsByNN.put("040", new Pokemon040(this));

        class Pokemon041 extends ElePokemonBase {
            public Pokemon041(final ColPokedex collection) {
                super(collection, "Zubat");
                addBase("pokemon/pokemon_041_icon.png", "pokemon/pokemon_041.png", "041", "Bat Pokémon", "0.8", "7.5");
                addTypes("Poison", "Flying");
                addAbilities("Inner Focus", "Infiltrator");
                addEvolution("None", "Golbat");
                addStats("245", "40", "45", "35", "30", "40", "55");
                addDetails("Zubat is a Poison/Flying type Pokémon introduced in Generation 1. It is known as the Bat Pokémon.");
            }
        }
        pokemonsByNN.put("041", new Pokemon041(this));

        class Pokemon042 extends ElePokemonBase {
            public Pokemon042(final ColPokedex collection) {
                super(collection, "Golbat");
                addBase("pokemon/pokemon_042_icon.png", "pokemon/pokemon_042.png", "042", "Bat Pokémon", "1.6", "55.0");
                addTypes("Poison", "Flying");
                addAbilities("Inner Focus", "Infiltrator");
                addEvolution("Zubat", "Crobat");
                addStats("455", "75", "80", "70", "65", "75", "90");
                addDetails("Golbat is a Poison/Flying type Pokémon introduced in Generation 1. It is known as the Bat Pokémon.");
            }
        }
        pokemonsByNN.put("042", new Pokemon042(this));

        class Pokemon043 extends ElePokemonBase {
            public Pokemon043(final ColPokedex collection) {
                super(collection, "Oddish");
                addBase("pokemon/pokemon_043_icon.png", "pokemon/pokemon_043.png", "043", "Weed Pokémon", "0.5", "5.4");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Run Away");
                addEvolution("Unknown", "Unknown");
                addStats("320", "45", "50", "55", "75", "65", "30");
                addDetails("Oddish is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Weed Pokémon.");
            }
        }
        pokemonsByNN.put("043", new Pokemon043(this));

        class Pokemon044 extends ElePokemonBase {
            public Pokemon044(final ColPokedex collection) {
                super(collection, "Gloom");
                addBase("pokemon/pokemon_044_icon.png", "pokemon/pokemon_044.png", "044", "Weed Pokémon", "0.8", "8.6");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Stench");
                addEvolution("Unknown", "Unknown");
                addStats("395", "60", "65", "70", "85", "75", "40");
                addDetails("Gloom is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Weed Pokémon.");
            }
        }
        pokemonsByNN.put("044", new Pokemon044(this));

        class Pokemon045 extends ElePokemonBase {
            public Pokemon045(final ColPokedex collection) {
                super(collection, "Vileplume");
                addBase("pokemon/pokemon_045_icon.png", "pokemon/pokemon_045.png", "045", "Flower Pokémon", "1.2", "18.6");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Effect Spore");
                addEvolution("Unknown", "Unknown");
                addStats("490", "75", "80", "85", "110", "90", "50");
                addDetails("Vileplume is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Flower Pokémon.");
            }
        }
        pokemonsByNN.put("045", new Pokemon045(this));

        class Pokemon046 extends ElePokemonBase {
            public Pokemon046(final ColPokedex collection) {
                super(collection, "Paras");
                addBase("pokemon/pokemon_046_icon.png", "pokemon/pokemon_046.png", "046", "Mushroom Pokémon", "0.3", "5.4");
                addTypes("Bug", "Grass");
                addAbilities("Effect Spore", "Dry Skin", "Damp");
                addEvolution("Unknown", "Unknown");
                addStats("285", "35", "70", "55", "45", "55", "25");
                addDetails("Paras is a Bug/Grass type Pokémon introduced in Generation 1. It is known as the Mushroom Pokémon.");
            }
        }
        pokemonsByNN.put("046", new Pokemon046(this));

        class Pokemon047 extends ElePokemonBase {
            public Pokemon047(final ColPokedex collection) {
                super(collection, "Parasect");
                addBase("pokemon/pokemon_047_icon.png", "pokemon/pokemon_047.png", "047", "Mushroom Pokémon", "1.0", "29.5");
                addTypes("Bug", "Grass");
                addAbilities("Effect Spore", "Dry Skin", "Damp");
                addEvolution("Unknown", "Unknown");
                addStats("405", "60", "95", "80", "60", "80", "30");
                addDetails("Parasect is a Bug/Grass type Pokémon introduced in Generation 1. It is known as the Mushroom Pokémon.");
            }
        }
        pokemonsByNN.put("047", new Pokemon047(this));

        class Pokemon048 extends ElePokemonBase {
            public Pokemon048(final ColPokedex collection) {
                super(collection, "Venonat");
                addBase("pokemon/pokemon_048_icon.png", "pokemon/pokemon_048.png", "048", "Insect Pokémon", "1.0", "30.0");
                addTypes("Bug", "Poison");
                addAbilities("Compound Eyes", "Tinted Lens", "Run Away");
                addEvolution("Unknown", "Unknown");
                addStats("305", "60", "55", "50", "40", "55", "45");
                addDetails("Venonat is a Bug/Poison type Pokémon introduced in Generation 1. It is known as the Insect Pokémon.");
            }
        }
        pokemonsByNN.put("048", new Pokemon048(this));

        class Pokemon049 extends ElePokemonBase {
            public Pokemon049(final ColPokedex collection) {
                super(collection, "Venomoth");
                addBase("pokemon/pokemon_049_icon.png", "pokemon/pokemon_049.png", "049", "Poison Moth Pokémon", "1.5", "12.5");
                addTypes("Bug", "Poison");
                addAbilities("Shield Dust", "Tinted Lens", "Wonder Skin");
                addEvolution("Unknown", "Unknown");
                addStats("450", "70", "65", "60", "90", "75", "90");
                addDetails("Venomoth is a Bug/Poison type Pokémon introduced in Generation 1. It is known as the Poison Moth Pokémon.");
            }
        }
        pokemonsByNN.put("049", new Pokemon049(this));

        class Pokemon050 extends ElePokemonBase {
            public Pokemon050(final ColPokedex collection) {
                super(collection, "Diglett");
                addBase("pokemon/pokemon_050_icon.png", "pokemon/pokemon_050.png", "050", "Mole Pokémon", "0.2", "0.8");
                addTypes("Ground");
                addAbilities("Sand Veil", "Arena Trap", "Sand Force");
                addEvolution("Unknown", "Unknown");
                addStats("265", "10", "55", "25", "35", "45", "95");
                addDetails("Diglett is a Ground type Pokémon introduced in Generation 1. It is known as the Mole Pokémon.");
            }
        }
        pokemonsByNN.put("050", new Pokemon050(this));

        class Pokemon051 extends ElePokemonBase {
            public Pokemon051(final ColPokedex collection) {
                super(collection, "Dugtrio");
                addBase("pokemon/pokemon_051_icon.png", "pokemon/pokemon_051.png", "051", "Mole Pokémon", "0.7", "33.3");
                addTypes("Ground");
                addAbilities("Sand Veil", "Arena Trap", "Sand Force");
                addEvolution("Unknown", "Unknown");
                addStats("425", "35", "100", "50", "50", "70", "120");
                addDetails("Dugtrio is a Ground type Pokémon introduced in Generation 1. It is known as the Mole Pokémon.");
            }
        }
        pokemonsByNN.put("051", new Pokemon051(this));

        class Pokemon052 extends ElePokemonBase {
            public Pokemon052(final ColPokedex collection) {
                super(collection, "Meowth");
                addBase("pokemon/pokemon_052_icon.png", "pokemon/pokemon_052.png", "052", "Scratch Cat Pokémon", "0.4", "4.2");
                addTypes("Normal");
                addAbilities("Pickup", "Technician", "Unnerve");
                addEvolution("Unknown", "Unknown");
                addStats("290", "40", "45", "35", "40", "40", "90");
                addDetails("Meowth is a Normal type Pokémon introduced in Generation 1. It is known as the Scratch Cat Pokémon.");
            }
        }
        pokemonsByNN.put("052", new Pokemon052(this));

        class Pokemon053 extends ElePokemonBase {
            public Pokemon053(final ColPokedex collection) {
                super(collection, "Persian");
                addBase("pokemon/pokemon_053_icon.png", "pokemon/pokemon_053.png", "053", "Classy Cat Pokémon", "1.0", "32.0");
                addTypes("Normal");
                addAbilities("Limber", "Technician", "Unnerve");
                addEvolution("Unknown", "Unknown");
                addStats("440", "65", "70", "60", "65", "65", "115");
                addDetails("Persian is a Normal type Pokémon introduced in Generation 1. It is known as the Classy Cat Pokémon.");
            }
        }
        pokemonsByNN.put("053", new Pokemon053(this));

        class Pokemon054 extends ElePokemonBase {
            public Pokemon054(final ColPokedex collection) {
                super(collection, "Psyduck");
                addBase("pokemon/pokemon_054_icon.png", "pokemon/pokemon_054.png", "054", "Duck Pokémon", "0.8", "19.6");
                addTypes("Water");
                addAbilities("Damp", "Cloud Nine", "Swift Swim");
                addEvolution("Unknown", "Unknown");
                addStats("320", "50", "52", "48", "65", "50", "55");
                addDetails("Psyduck is a Water type Pokémon introduced in Generation 1. It is known as the Duck Pokémon.");
            }
        }
        pokemonsByNN.put("054", new Pokemon054(this));

        class Pokemon055 extends ElePokemonBase {
            public Pokemon055(final ColPokedex collection) {
                super(collection, "Golduck");
                addBase("pokemon/pokemon_055_icon.png", "pokemon/pokemon_055.png", "055", "Duck Pokémon", "1.7", "76.6");
                addTypes("Water");
                addAbilities("Damp", "Cloud Nine", "Swift Swim");
                addEvolution("Unknown", "Unknown");
                addStats("500", "80", "82", "78", "95", "80", "85");
                addDetails("Golduck is a Water type Pokémon introduced in Generation 1. It is known as the Duck Pokémon.");
            }
        }
        pokemonsByNN.put("055", new Pokemon055(this));

        class Pokemon056 extends ElePokemonBase {
            public Pokemon056(final ColPokedex collection) {
                super(collection, "Mankey");
                addBase("pokemon/pokemon_056_icon.png", "pokemon/pokemon_056.png", "056", "Pig Monkey Pokémon", "0.5", "28.0");
                addTypes("Fighting");
                addAbilities("Vital Spirit", "Anger Point", "Defiant");
                addEvolution("Unknown", "Unknown");
                addStats("305", "40", "80", "35", "35", "45", "70");
                addDetails("Mankey is a Fighting type Pokémon introduced in Generation 1. It is known as the Pig Monkey Pokémon.");
            }
        }
        pokemonsByNN.put("056", new Pokemon056(this));

        class Pokemon057 extends ElePokemonBase {
            public Pokemon057(final ColPokedex collection) {
                super(collection, "Primeape");
                addBase("pokemon/pokemon_057_icon.png", "pokemon/pokemon_057.png", "057", "Pig Monkey Pokémon", "1.0", "32.0");
                addTypes("Fighting");
                addAbilities("Vital Spirit", "Anger Point", "Defiant");
                addEvolution("Unknown", "Unknown");
                addStats("455", "65", "105", "60", "60", "70", "95");
                addDetails("Primeape is a Fighting type Pokémon introduced in Generation 1. It is known as the Pig Monkey Pokémon.");
            }
        }
        pokemonsByNN.put("057", new Pokemon057(this));

        class Pokemon058 extends ElePokemonBase {
            public Pokemon058(final ColPokedex collection) {
                super(collection, "Growlithe");
                addBase("pokemon/pokemon_058_icon.png", "pokemon/pokemon_058.png", "058", "Puppy Pokémon", "0.7", "19.0");
                addTypes("Fire");
                addAbilities("Intimidate", "Flash Fire", "Justified");
                addEvolution("Unknown", "Unknown");
                addStats("350", "55", "70", "45", "70", "50", "60");
                addDetails("Growlithe is a Fire type Pokémon introduced in Generation 1. It is known as the Puppy Pokémon.");
            }
        }
        pokemonsByNN.put("058", new Pokemon058(this));

        class Pokemon059 extends ElePokemonBase {
            public Pokemon059(final ColPokedex collection) {
                super(collection, "Arcanine");
                addBase("pokemon/pokemon_059_icon.png", "pokemon/pokemon_059.png", "059", "Legendary Pokémon", "1.9", "155.0");
                addTypes("Fire");
                addAbilities("Intimidate", "Flash Fire", "Justified");
                addEvolution("Unknown", "Unknown");
                addStats("555", "90", "110", "80", "100", "80", "95");
                addDetails("Arcanine is a Fire type Pokémon introduced in Generation 1. It is known as the Legendary Pokémon.");
            }
        }
        pokemonsByNN.put("059", new Pokemon059(this));

        class Pokemon060 extends ElePokemonBase {
            public Pokemon060(final ColPokedex collection) {
                super(collection, "Poliwag");
                addBase("pokemon/pokemon_060_icon.png", "pokemon/pokemon_060.png", "060", "Tadpole Pokémon", "0.6", "12.4");
                addTypes("Water");
                addAbilities("Water Absorb", "Damp", "Swift Swim");
                addEvolution("Unknown", "Unknown");
                addStats("300", "40", "50", "40", "40", "40", "90");
                addDetails("Poliwag is a Water type Pokémon introduced in Generation 1. It is known as the Tadpole Pokémon.");
            }
        }
        pokemonsByNN.put("060", new Pokemon060(this));

        class Pokemon061 extends ElePokemonBase {
            public Pokemon061(final ColPokedex collection) {
                super(collection, "Poliwhirl");
                addBase("pokemon/pokemon_061_icon.png", "pokemon/pokemon_061.png", "061", "Tadpole Pokémon", "1.0", "20.0");
                addTypes("Water");
                addAbilities("Water Absorb", "Damp", "Swift Swim");
                addEvolution("Unknown", "Unknown");
                addStats("385", "65", "65", "65", "50", "50", "90");
                addDetails("Poliwhirl is a Water type Pokémon introduced in Generation 1. It is known as the Tadpole Pokémon.");
            }
        }
        pokemonsByNN.put("061", new Pokemon061(this));

        class Pokemon062 extends ElePokemonBase {
            public Pokemon062(final ColPokedex collection) {
                super(collection, "Poliwrath");
                addBase("pokemon/pokemon_062_icon.png", "pokemon/pokemon_062.png", "062", "Tadpole Pokémon", "1.3", "54.0");
                addTypes("Water", "Fighting");
                addAbilities("Water Absorb", "Damp", "Swift Swim");
                addEvolution("Unknown", "Unknown");
                addStats("510", "90", "95", "95", "70", "90", "70");
                addDetails("Poliwrath is a Water/Fighting type Pokémon introduced in Generation 1. It is known as the Tadpole Pokémon.");
            }
        }
        pokemonsByNN.put("062", new Pokemon062(this));

        class Pokemon063 extends ElePokemonBase {
            public Pokemon063(final ColPokedex collection) {
                super(collection, "Abra");
                addBase("pokemon/pokemon_063_icon.png", "pokemon/pokemon_063.png", "063", "Psi Pokémon", "0.9", "19.5");
                addTypes("Psychic");
                addAbilities("Synchronize", "Inner Focus", "Magic Guard");
                addEvolution("None", "Kadabra");
                addStats("310", "25", "20", "15", "105", "55", "90");
                addDetails("Abra is a Psychic type Pokémon introduced in Generation 1. It is known as the Psi Pokémon.");
            }
        }
        pokemonsByNN.put("063", new Pokemon063(this));

        class Pokemon064 extends ElePokemonBase {
            public Pokemon064(final ColPokedex collection) {
                super(collection, "Kadabra");
                addBase("pokemon/pokemon_064_icon.png", "pokemon/pokemon_064.png", "064", "Psi Pokémon", "1.3", "56.5");
                addTypes("Psychic");
                addAbilities("Synchronize", "Inner Focus", "Magic Guard");
                addEvolution("Abra", "Alakazam");
                addStats("400", "40", "35", "30", "120", "70", "105");
                addDetails("Kadabra is a Psychic type Pokémon introduced in Generation 1. It is known as the Psi Pokémon.");
            }
        }
        pokemonsByNN.put("064", new Pokemon064(this));

        class Pokemon065 extends ElePokemonBase {
            public Pokemon065(final ColPokedex collection) {
                super(collection, "Alakazam");
                addBase("pokemon/pokemon_065_icon.png", "pokemon/pokemon_065.png", "065", "Psi Pokémon", "1.5", "48.0");
                addTypes("Psychic");
                addAbilities("Synchronize", "Inner Focus", "Magic Guard");
                addEvolution("Kadabra", "None");
                addStats("500", "55", "50", "45", "135", "95", "120");
                addDetails("Alakazam is a Psychic type Pokémon introduced in Generation 1. It is known as the Psi Pokémon.");
            }
        }
        pokemonsByNN.put("065", new Pokemon065(this));

        class Pokemon066 extends ElePokemonBase {
            public Pokemon066(final ColPokedex collection) {
                super(collection, "Machop");
                addBase("pokemon/pokemon_066_icon.png", "pokemon/pokemon_066.png", "066", "Superpower Pokémon", "0.8", "19.5");
                addTypes("Fighting");
                addAbilities("Guts", "No Guard", "Steadfast");
                addEvolution("None", "Machoke");
                addStats("305", "70", "80", "50", "35", "35", "35");
                addDetails("Machop is a Fighting type Pokémon introduced in Generation 1. It is known as the Superpower Pokémon.");
            }
        }
        pokemonsByNN.put("066", new Pokemon066(this));

        class Pokemon067 extends ElePokemonBase {
            public Pokemon067(final ColPokedex collection) {
                super(collection, "Machoke");
                addBase("pokemon/pokemon_067_icon.png", "pokemon/pokemon_067.png", "067", "Superpower Pokémon", "1.5", "70.5");
                addTypes("Fighting");
                addAbilities("Guts", "No Guard", "Steadfast");
                addEvolution("Machop", "Machamp");
                addStats("405", "80", "100", "70", "50", "60", "45");
                addDetails("Machoke is a Fighting type Pokémon introduced in Generation 1. It is known as the Superpower Pokémon.");
            }
        }
        pokemonsByNN.put("067", new Pokemon067(this));

        class Pokemon068 extends ElePokemonBase {
            public Pokemon068(final ColPokedex collection) {
                super(collection, "Machamp");
                addBase("pokemon/pokemon_068_icon.png", "pokemon/pokemon_068.png", "068", "Superpower Pokémon", "1.6", "130.0");
                addTypes("Fighting");
                addAbilities("Guts", "No Guard", "Steadfast");
                addEvolution("Machoke", "None");
                addStats("505", "90", "130", "80", "65", "85", "55");
                addDetails("Machamp is a Fighting type Pokémon introduced in Generation 1. It is known as the Superpower Pokémon.");
            }
        }
        pokemonsByNN.put("068", new Pokemon068(this));

        class Pokemon069 extends ElePokemonBase {
            public Pokemon069(final ColPokedex collection) {
                super(collection, "Bellsprout");
                addBase("pokemon/pokemon_069_icon.png", "pokemon/pokemon_069.png", "069", "Flower Pokémon", "0.7", "4.0");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Gluttony");
                addEvolution("None", "Weepinbell");
                addStats("300", "50", "75", "35", "70", "30", "40");
                addDetails("Bellsprout is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Flower Pokémon.");
            }
        }
        pokemonsByNN.put("069", new Pokemon069(this));

        class Pokemon070 extends ElePokemonBase {
            public Pokemon070(final ColPokedex collection) {
                super(collection, "Weepinbell");
                addBase("pokemon/pokemon_070_icon.png", "pokemon/pokemon_070.png", "070", "Flycatcher Pokémon", "1.0", "6.4");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Gluttony");
                addEvolution("Bellsprout", "Victreebel");
                addStats("390", "65", "90", "50", "85", "45", "55");
                addDetails("Weepinbell is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Flycatcher Pokémon.");
            }
        }
        pokemonsByNN.put("070", new Pokemon070(this));

        class Pokemon071 extends ElePokemonBase {
            public Pokemon071(final ColPokedex collection) {
                super(collection, "Victreebel");
                addBase("pokemon/pokemon_071_icon.png", "pokemon/pokemon_071.png", "071", "Flycatcher Pokémon", "1.7", "15.5");
                addTypes("Grass", "Poison");
                addAbilities("Chlorophyll", "Gluttony");
                addEvolution("Weepinbell", "None");
                addStats("490", "80", "105", "65", "100", "70", "70");
                addDetails("Victreebel is a Grass/Poison type Pokémon introduced in Generation 1. It is known as the Flycatcher Pokémon.");
            }
        }
        pokemonsByNN.put("071", new Pokemon071(this));

        class Pokemon072 extends ElePokemonBase {
            public Pokemon072(final ColPokedex collection) {
                super(collection, "Tentacool");
                addBase("pokemon/pokemon_072_icon.png", "pokemon/pokemon_072.png", "072", "Jellyfish Pokémon", "0.9", "45.5");
                addTypes("Water", "Poison");
                addAbilities("Clear Body", "Liquid Ooze", "Rain Dish");
                addEvolution("Unknown", "Unknown");
                addStats("335", "40", "40", "35", "50", "100", "70");
                addDetails("Tentacool is a Water/Poison type Pokémon introduced in Generation 1. It is known as the Jellyfish Pokémon.");
            }
        }
        pokemonsByNN.put("072", new Pokemon072(this));

        class Pokemon073 extends ElePokemonBase {
            public Pokemon073(final ColPokedex collection) {
                super(collection, "Tentacruel");
                addBase("pokemon/pokemon_073_icon.png", "pokemon/pokemon_073.png", "073", "Jellyfish Pokémon", "1.6", "55.0");
                addTypes("Water", "Poison");
                addAbilities("Clear Body", "Liquid Ooze", "Rain Dish");
                addEvolution("Unknown", "Unknown");
                addStats("515", "80", "70", "65", "80", "120", "100");
                addDetails("Tentacruel is a Water/Poison type Pokémon introduced in Generation 1. It is known as the Jellyfish Pokémon.");
            }
        }
        pokemonsByNN.put("073", new Pokemon073(this));

        class Pokemon074 extends ElePokemonBase {
            public Pokemon074(final ColPokedex collection) {
                super(collection, "Geodude");
                addBase("pokemon/pokemon_074_icon.png", "pokemon/pokemon_074.png", "074", "Rock Pokémon", "0.4", "20.0");
                addTypes("Rock", "Ground");
                addAbilities("Rock Head", "Sturdy", "Sand Veil");
                addEvolution("None", "Graveler");
                addStats("300", "40", "80", "100", "30", "30", "20");
                addDetails("Geodude is a Rock/Ground type Pokémon introduced in Generation 1. It is known as the Rock Pokémon.");
            }
        }
        pokemonsByNN.put("074", new Pokemon074(this));

        class Pokemon075 extends ElePokemonBase {
            public Pokemon075(final ColPokedex collection) {
                super(collection, "Graveler");
                addBase("pokemon/pokemon_075_icon.png", "pokemon/pokemon_075.png", "075", "Rock Pokémon", "1.0", "105.0");
                addTypes("Rock", "Ground");
                addAbilities("Rock Head", "Sturdy", "Sand Veil");
                addEvolution("Geodude", "Golem");
                addStats("390", "55", "95", "115", "45", "45", "35");
                addDetails("Graveler is a Rock/Ground type Pokémon introduced in Generation 1. It is known as the Rock Pokémon.");
            }
        }
        pokemonsByNN.put("075", new Pokemon075(this));

        class Pokemon076 extends ElePokemonBase {
            public Pokemon076(final ColPokedex collection) {
                super(collection, "Golem");
                addBase("pokemon/pokemon_076_icon.png", "pokemon/pokemon_076.png", "076", "Megaton Pokémon", "1.4", "300.0");
                addTypes("Rock", "Ground");
                addAbilities("Rock Head", "Sturdy", "Sand Veil");
                addEvolution("Graveler", "None");
                addStats("495", "80", "120", "130", "55", "65", "45");
                addDetails("Golem is a Rock/Ground type Pokémon introduced in Generation 1. It is known as the Megaton Pokémon.");
            }
        }
        pokemonsByNN.put("076", new Pokemon076(this));

        class Pokemon077 extends ElePokemonBase {
            public Pokemon077(final ColPokedex collection) {
                super(collection, "Ponyta");
                addBase("pokemon/pokemon_077_icon.png", "pokemon/pokemon_077.png", "077", "Fire Horse Pokémon", "1.0", "30.0");
                addTypes("Fire");
                addAbilities("Run Away", "Flash Fire", "Flame Body");
                addEvolution("Unknown", "Unknown");
                addStats("410", "50", "85", "55", "65", "65", "90");
                addDetails("Ponyta is a Fire type Pokémon introduced in Generation 1. It is known as the Fire Horse Pokémon.");
            }
        }
        pokemonsByNN.put("077", new Pokemon077(this));

        class Pokemon078 extends ElePokemonBase {
            public Pokemon078(final ColPokedex collection) {
                super(collection, "Rapidash");
                addBase("pokemon/pokemon_078_icon.png", "pokemon/pokemon_078.png", "078", "Fire Horse Pokémon", "1.7", "95.0");
                addTypes("Fire");
                addAbilities("Run Away", "Flash Fire", "Flame Body");
                addEvolution("Unknown", "Unknown");
                addStats("500", "65", "100", "70", "80", "80", "105");
                addDetails("Rapidash is a Fire type Pokémon introduced in Generation 1. It is known as the Fire Horse Pokémon.");
            }
        }
        pokemonsByNN.put("078", new Pokemon078(this));

        class Pokemon079 extends ElePokemonBase {
            public Pokemon079(final ColPokedex collection) {
                super(collection, "Slowpoke");
                addBase("pokemon/pokemon_079_icon.png", "pokemon/pokemon_079.png", "079", "Dopey Pokémon", "1.2", "36.0");
                addTypes("Water", "Psychic");
                addAbilities("Oblivious", "Own Tempo", "Regenerator");
                addEvolution("None", "Slowbro");
                addStats("315", "90", "65", "65", "40", "40", "15");
                addDetails("Slowpoke is a Water/Psychic type Pokémon introduced in Generation 1. It is known as the Dopey Pokémon.");
            }
        }
        pokemonsByNN.put("079", new Pokemon079(this));

        class Pokemon080 extends ElePokemonBase {
            public Pokemon080(final ColPokedex collection) {
                super(collection, "Slowbro");
                addBase("pokemon/pokemon_080_icon.png", "pokemon/pokemon_080.png", "080", "Hermit Crab Pokémon", "1.6", "78.5");
                addTypes("Water", "Psychic");
                addAbilities("Oblivious", "Own Tempo", "Regenerator");
                addEvolution("Slowpoke", "Slowking");
                addStats("490", "95", "75", "110", "100", "80", "30");
                addDetails("Slowbro is a Water/Psychic type Pokémon introduced in Generation 1. It is known as the Hermit Crab Pokémon.");
            }
        }
        pokemonsByNN.put("080", new Pokemon080(this));

        class Pokemon081 extends ElePokemonBase {
            public Pokemon081(final ColPokedex collection) {
                super(collection, "Magnemite");
                addBase("pokemon/pokemon_081_icon.png", "pokemon/pokemon_081.png", "081", "Magnet Pokémon", "0.3", "6.0");
                addTypes("Electric", "Steel");
                addAbilities("Magnet Pull", "Sturdy", "Analytic");
                addEvolution("None", "Magneton");
                addStats("325", "25", "35", "70", "95", "55", "45");
                addDetails("Magnemite is an Electric/Steel type Pokémon introduced in Generation 1. It is known as the Magnet Pokémon.");
            }
        }
        pokemonsByNN.put("081", new Pokemon081(this));

        class Pokemon082 extends ElePokemonBase {
            public Pokemon082(final ColPokedex collection) {
                super(collection, "Magneton");
                addBase("pokemon/pokemon_082_icon.png", "pokemon/pokemon_082.png", "082", "Magnet Pokémon", "1.0", "60.0");
                addTypes("Electric", "Steel");
                addAbilities("Magnet Pull", "Sturdy", "Analytic");
                addEvolution("Magnemite", "Magnezone");
                addStats("465", "50", "60", "95", "120", "70", "70");
                addDetails("Magneton is an Electric/Steel type Pokémon introduced in Generation 1. It is known as the Magnet Pokémon.");
            }
        }
        pokemonsByNN.put("082", new Pokemon082(this));

        class Pokemon083 extends ElePokemonBase {
            public Pokemon083(final ColPokedex collection) {
                super(collection, "Farfetch'd");
                addBase("pokemon/pokemon_083_icon.png", "pokemon/pokemon_083.png", "083", "Wild Duck Pokémon", "0.8", "15.0");
                addTypes("Normal", "Flying");
                addAbilities("Keen Eye", "Inner Focus", "Defiant");
                addEvolution("Unknown", "Unknown");
                addStats("377", "52", "90", "55", "58", "62", "60");
                addDetails("Farfetch'd is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Wild Duck Pokémon.");
            }
        }
        pokemonsByNN.put("083", new Pokemon083(this));

        class Pokemon084 extends ElePokemonBase {
            public Pokemon084(final ColPokedex collection) {
                super(collection, "Doduo");
                addBase("pokemon/pokemon_084_icon.png", "pokemon/pokemon_084.png", "084", "Twin Bird Pokémon", "1.4", "39.2");
                addTypes("Normal", "Flying");
                addAbilities("Run Away", "Early Bird", "Tangled Feet");
                addEvolution("Unknown", "Unknown");
                addStats("310", "35", "85", "45", "35", "35", "75");
                addDetails("Doduo is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Twin Bird Pokémon.");
            }
        }
        pokemonsByNN.put("084", new Pokemon084(this));

        class Pokemon085 extends ElePokemonBase {
            public Pokemon085(final ColPokedex collection) {
                super(collection, "Dodrio");
                addBase("pokemon/pokemon_085_icon.png", "pokemon/pokemon_085.png", "085", "Triple Bird Pokémon", "1.8", "85.2");
                addTypes("Normal", "Flying");
                addAbilities("Run Away", "Early Bird", "Tangled Feet");
                addEvolution("Unknown", "Unknown");
                addStats("470", "60", "110", "70", "60", "60", "110");
                addDetails("Dodrio is a Normal/Flying type Pokémon introduced in Generation 1. It is known as the Triple Bird Pokémon.");
            }
        }
        pokemonsByNN.put("085", new Pokemon085(this));

        class Pokemon086 extends ElePokemonBase {
            public Pokemon086(final ColPokedex collection) {
                super(collection, "Seel");
                addBase("pokemon/pokemon_086_icon.png", "pokemon/pokemon_086.png", "086", "Sea Lion Pokémon", "1.1", "90.0");
                addTypes("Water");
                addAbilities("Thick Fat", "Hydration", "Ice Body");
                addEvolution("Unknown", "Unknown");
                addStats("325", "65", "45", "55", "45", "70", "45");
                addDetails("Seel is a Water type Pokémon introduced in Generation 1. It is known as the Sea Lion Pokémon.");
            }
        }
        pokemonsByNN.put("086", new Pokemon086(this));

        class Pokemon087 extends ElePokemonBase {
            public Pokemon087(final ColPokedex collection) {
                super(collection, "Dewgong");
                addBase("pokemon/pokemon_087_icon.png", "pokemon/pokemon_087.png", "087", "Sea Lion Pokémon", "1.7", "120.0");
                addTypes("Water", "Ice");
                addAbilities("Thick Fat", "Hydration", "Ice Body");
                addEvolution("Unknown", "Unknown");
                addStats("475", "90", "70", "80", "70", "95", "70");
                addDetails("Dewgong is a Water/Ice type Pokémon introduced in Generation 1. It is known as the Sea Lion Pokémon.");
            }
        }
        pokemonsByNN.put("087", new Pokemon087(this));

        class Pokemon088 extends ElePokemonBase {
            public Pokemon088(final ColPokedex collection) {
                super(collection, "Grimer");
                addBase("pokemon/pokemon_088_icon.png", "pokemon/pokemon_088.png", "088", "Sludge Pokémon", "0.9", "30.0");
                addTypes("Poison");
                addAbilities("Stench", "Sticky Hold", "Poison Touch");
                addEvolution("Unknown", "Unknown");
                addStats("325", "80", "80", "50", "40", "50", "25");
                addDetails("Grimer is a Poison type Pokémon introduced in Generation 1. It is known as the Sludge Pokémon.");
            }
        }
        pokemonsByNN.put("088", new Pokemon088(this));

        class Pokemon089 extends ElePokemonBase {
            public Pokemon089(final ColPokedex collection) {
                super(collection, "Muk");
                addBase("pokemon/pokemon_089_icon.png", "pokemon/pokemon_089.png", "089", "Sludge Pokémon", "1.2", "30.0");
                addTypes("Poison");
                addAbilities("Stench", "Sticky Hold", "Poison Touch");
                addEvolution("Unknown", "Unknown");
                addStats("500", "105", "105", "75", "65", "100", "50");
                addDetails("Muk is a Poison type Pokémon introduced in Generation 1. It is known as the Sludge Pokémon.");
            }
        }
        pokemonsByNN.put("089", new Pokemon089(this));

        class Pokemon090 extends ElePokemonBase {
            public Pokemon090(final ColPokedex collection) {
                super(collection, "Shellder");
                addBase("pokemon/pokemon_090_icon.png", "pokemon/pokemon_090.png", "090", "Bivalve Pokémon", "0.3", "4.0");
                addTypes("Water");
                addAbilities("Shell Armor", "Skill Link", "Overcoat");
                addEvolution("Unknown", "Unknown");
                addStats("305", "30", "65", "100", "45", "25", "40");
                addDetails("Shellder is a Water type Pokémon introduced in Generation 1. It is known as the Bivalve Pokémon.");
            }
        }
        pokemonsByNN.put("090", new Pokemon090(this));

        class Pokemon091 extends ElePokemonBase {
            public Pokemon091(final ColPokedex collection) {
                super(collection, "Cloyster");
                addBase("pokemon/pokemon_091_icon.png", "pokemon/pokemon_091.png", "091", "Bivalve Pokémon", "1.5", "132.5");
                addTypes("Water", "Ice");
                addAbilities("Shell Armor", "Skill Link", "Overcoat");
                addEvolution("Unknown", "Unknown");
                addStats("525", "50", "95", "180", "85", "45", "70");
                addDetails("Cloyster is a Water/Ice type Pokémon introduced in Generation 1. It is known as the Bivalve Pokémon.");
            }
        }
        pokemonsByNN.put("091", new Pokemon091(this));

        class Pokemon092 extends ElePokemonBase {
            public Pokemon092(final ColPokedex collection) {
                super(collection, "Gastly");
                addBase("pokemon/pokemon_092_icon.png", "pokemon/pokemon_092.png", "092", "Gas Pokémon", "1.3", "0.1");
                addTypes("Ghost", "Poison");
                addAbilities("Levitate");
                addEvolution("None", "Haunter");
                addStats("310", "30", "35", "30", "100", "35", "80");
                addDetails("Gastly is a Ghost/Poison type Pokémon introduced in Generation 1. It is known as the Gas Pokémon.");
            }
        }
        pokemonsByNN.put("092", new Pokemon092(this));

        class Pokemon093 extends ElePokemonBase {
            public Pokemon093(final ColPokedex collection) {
                super(collection, "Haunter");
                addBase("pokemon/pokemon_093_icon.png", "pokemon/pokemon_093.png", "093", "Gas Pokémon", "1.6", "0.1");
                addTypes("Ghost", "Poison");
                addAbilities("Levitate");
                addEvolution("Gastly", "Gengar");
                addStats("405", "45", "50", "45", "115", "55", "95");
                addDetails("Haunter is a Ghost/Poison type Pokémon introduced in Generation 1. It is known as the Gas Pokémon.");
            }
        }
        pokemonsByNN.put("093", new Pokemon093(this));

        class Pokemon094 extends ElePokemonBase {
            public Pokemon094(final ColPokedex collection) {
                super(collection, "Gengar");
                addBase("pokemon/pokemon_094_icon.png", "pokemon/pokemon_094.png", "094", "Shadow Pokémon", "1.5", "40.5");
                addTypes("Ghost", "Poison");
                addAbilities("Cursed Body");
                addEvolution("Haunter", "None");
                addStats("500", "60", "65", "60", "130", "75", "110");
                addDetails("Gengar is a Ghost/Poison type Pokémon introduced in Generation 1. It is known as the Shadow Pokémon.");
            }
        }
        pokemonsByNN.put("094", new Pokemon094(this));

        class Pokemon095 extends ElePokemonBase {
            public Pokemon095(final ColPokedex collection) {
                super(collection, "Onix");
                addBase("pokemon/pokemon_095_icon.png", "pokemon/pokemon_095.png", "095", "Rock Snake Pokémon", "8.8", "210.0");
                addTypes("Rock", "Ground");
                addAbilities("Rock Head", "Sturdy", "Weak Armor");
                addEvolution("Unknown", "Unknown");
                addStats("385", "35", "45", "160", "30", "45", "70");
                addDetails("Onix is a Rock/Ground type Pokémon introduced in Generation 1. It is known as the Rock Snake Pokémon.");
            }
        }
        pokemonsByNN.put("095", new Pokemon095(this));

        class Pokemon096 extends ElePokemonBase {
            public Pokemon096(final ColPokedex collection) {
                super(collection, "Drowzee");
                addBase("pokemon/pokemon_096_icon.png", "pokemon/pokemon_096.png", "096", "Hypnosis Pokémon", "1.0", "32.4");
                addTypes("Psychic");
                addAbilities("Insomnia", "Forewarn", "Inner Focus");
                addEvolution("Unknown", "Unknown");
                addStats("328", "60", "48", "45", "43", "90", "42");
                addDetails("Drowzee is a Psychic type Pokémon introduced in Generation 1. It is known as the Hypnosis Pokémon.");
            }
        }
        pokemonsByNN.put("096", new Pokemon096(this));

        class Pokemon097 extends ElePokemonBase {
            public Pokemon097(final ColPokedex collection) {
                super(collection, "Hypno");
                addBase("pokemon/pokemon_097_icon.png", "pokemon/pokemon_097.png", "097", "Hypnosis Pokémon", "1.6", "75.6");
                addTypes("Psychic");
                addAbilities("Insomnia", "Forewarn", "Inner Focus");
                addEvolution("Unknown", "Unknown");
                addStats("483", "85", "73", "70", "73", "115", "67");
                addDetails("Hypno is a Psychic type Pokémon introduced in Generation 1. It is known as the Hypnosis Pokémon.");
            }
        }
        pokemonsByNN.put("097", new Pokemon097(this));

        class Pokemon098 extends ElePokemonBase {
            public Pokemon098(final ColPokedex collection) {
                super(collection, "Krabby");
                addBase("pokemon/pokemon_098_icon.png", "pokemon/pokemon_098.png", "098", "River Crab Pokémon", "0.4", "6.5");
                addTypes("Water");
                addAbilities("Hyper Cutter", "Shell Armor", "Sheer Force");
                addEvolution("Unknown", "Unknown");
                addStats("325", "30", "105", "90", "25", "25", "50");
                addDetails("Krabby is a Water type Pokémon introduced in Generation 1. It is known as the River Crab Pokémon.");
            }
        }
        pokemonsByNN.put("098", new Pokemon098(this));

        class Pokemon099 extends ElePokemonBase {
            public Pokemon099(final ColPokedex collection) {
                super(collection, "Kingler");
                addBase("pokemon/pokemon_099_icon.png", "pokemon/pokemon_099.png", "099", "Pincer Pokémon", "1.3", "60.0");
                addTypes("Water");
                addAbilities("Hyper Cutter", "Shell Armor", "Sheer Force");
                addEvolution("Unknown", "Unknown");
                addStats("475", "55", "130", "115", "50", "50", "75");
                addDetails("Kingler is a Water type Pokémon introduced in Generation 1. It is known as the Pincer Pokémon.");
            }
        }
        pokemonsByNN.put("099", new Pokemon099(this));

        class Pokemon100 extends ElePokemonBase {
            public Pokemon100(final ColPokedex collection) {
                super(collection, "Voltorb");
                addBase("pokemon/pokemon_100_icon.png", "pokemon/pokemon_100.png", "100", "Ball Pokémon", "0.5", "10.4");
                addTypes("Electric");
                addAbilities("Soundproof", "Static", "Aftermath");
                addEvolution("Unknown", "Unknown");
                addStats("330", "40", "30", "50", "55", "55", "100");
                addDetails("Voltorb is an Electric type Pokémon introduced in Generation 1. It is known as the Ball Pokémon.");
            }
        }
        pokemonsByNN.put("100", new Pokemon100(this));

        class Pokemon101 extends ElePokemonBase {
            public Pokemon101(final ColPokedex collection) {
                super(collection, "Electrode");
                addBase("pokemon/pokemon_101_icon.png", "pokemon/pokemon_101.png", "101", "Ball Pokémon", "1.2", "66.6");
                addTypes("Electric");
                addAbilities("Soundproof", "Static", "Aftermath");
                addEvolution("Unknown", "Unknown");
                addStats("490", "60", "50", "70", "80", "80", "150");
                addDetails("Electrode is an Electric type Pokémon introduced in Generation 1. It is known as the Ball Pokémon.");
            }
        }
        pokemonsByNN.put("101", new Pokemon101(this));

        class Pokemon102 extends ElePokemonBase {
            public Pokemon102(final ColPokedex collection) {
                super(collection, "Exeggcute");
                addBase("pokemon/pokemon_102_icon.png", "pokemon/pokemon_102.png", "102", "Egg Pokémon", "0.4", "2.5");
                addTypes("Grass", "Psychic");
                addAbilities("Chlorophyll", "Harvest");
                addEvolution("None", "Exeggutor");
                addStats("325", "60", "40", "80", "60", "45", "40");
                addDetails("Exeggcute is a Grass/Psychic type Pokémon introduced in Generation 1. It is known as the Egg Pokémon.");
            }
        }
        pokemonsByNN.put("102", new Pokemon102(this));

        class Pokemon103 extends ElePokemonBase {
            public Pokemon103(final ColPokedex collection) {
                super(collection, "Exeggutor");
                addBase("pokemon/pokemon_103_icon.png", "pokemon/pokemon_103.png", "103", "Coconut Pokémon", "2.0", "120.0");
                addTypes("Grass", "Psychic");
                addAbilities("Chlorophyll", "Harvest");
                addEvolution("Exeggcute", "Exeggutor");
                addStats("530", "95", "95", "85", "125", "75", "55");
                addDetails("Exeggutor is a Grass/Psychic type Pokémon introduced in Generation 1. It is known as the Coconut Pokémon.");
            }
        }
        pokemonsByNN.put("103", new Pokemon103(this));

        class Pokemon104 extends ElePokemonBase {
            public Pokemon104(final ColPokedex collection) {
                super(collection, "Cubone");
                addBase("pokemon/pokemon_104_icon.png", "pokemon/pokemon_104.png", "104", "Lonely Pokémon", "0.4", "6.5");
                addTypes("Ground");
                addAbilities("Rock Head", "Lightning Rod", "Battle Armor");
                addEvolution("None", "Marowak");
                addStats("320", "50", "50", "95", "40", "50", "35");
                addDetails("Cubone is a Ground type Pokémon introduced in Generation 1. It is known as the Lonely Pokémon.");
            }
        }
        pokemonsByNN.put("104", new Pokemon104(this));

        class Pokemon105 extends ElePokemonBase {
            public Pokemon105(final ColPokedex collection) {
                super(collection, "Marowak");
                addBase("pokemon/pokemon_105_icon.png", "pokemon/pokemon_105.png", "105", "Bone Keeper Pokémon", "1.0", "45.0");
                addTypes("Ground");
                addAbilities("Rock Head", "Lightning Rod", "Battle Armor");
                addEvolution("Cubone", "Marowak");
                addStats("425", "60", "80", "110", "50", "80", "45");
                addDetails("Marowak is a Ground type Pokémon introduced in Generation 1. It is known as the Bone Keeper Pokémon.");
            }
        }
        pokemonsByNN.put("105", new Pokemon105(this));

        class Pokemon106 extends ElePokemonBase {
            public Pokemon106(final ColPokedex collection) {
                super(collection, "Hitmonlee");
                addBase("pokemon/pokemon_106_icon.png", "pokemon/pokemon_106.png", "106", "Kicking Pokémon", "1.5", "49.8");
                addTypes("Fighting");
                addAbilities("Limber", "Reckless", "Unburden");
                addEvolution("Unknown", "Unknown");
                addStats("455", "50", "120", "53", "35", "110", "87");
                addDetails("Hitmonlee is a Fighting type Pokémon introduced in Generation 1. It is known as the Kicking Pokémon.");
            }
        }
        pokemonsByNN.put("106", new Pokemon106(this));

        class Pokemon107 extends ElePokemonBase {
            public Pokemon107(final ColPokedex collection) {
                super(collection, "Hitmonchan");
                addBase("pokemon/pokemon_107_icon.png", "pokemon/pokemon_107.png", "107", "Punching Pokémon", "1.4", "50.2");
                addTypes("Fighting");
                addAbilities("Keen Eye", "Iron Fist", "Inner Focus");
                addEvolution("Unknown", "Unknown");
                addStats("455", "50", "105", "79", "35", "110", "76");
                addDetails("Hitmonchan is a Fighting type Pokémon introduced in Generation 1. It is known as the Punching Pokémon.");
            }
        }
        pokemonsByNN.put("107", new Pokemon107(this));

        class Pokemon108 extends ElePokemonBase {
            public Pokemon108(final ColPokedex collection) {
                super(collection, "Lickitung");
                addBase("pokemon/pokemon_108_icon.png", "pokemon/pokemon_108.png", "108", "Licking Pokémon", "1.2", "65.5");
                addTypes("Normal");
                addAbilities("Own Tempo", "Oblivious", "Cloud Nine");
                addEvolution("Unknown", "Unknown");
                addStats("385", "90", "55", "75", "60", "75", "30");
                addDetails("Lickitung is a Normal type Pokémon introduced in Generation 1. It is known as the Licking Pokémon.");
            }
        }
        pokemonsByNN.put("108", new Pokemon108(this));

        class Pokemon109 extends ElePokemonBase {
            public Pokemon109(final ColPokedex collection) {
                super(collection, "Koffing");
                addBase("pokemon/pokemon_109_icon.png", "pokemon/pokemon_109.png", "109", "Poison Gas Pokémon", "0.6", "1.0");
                addTypes("Poison");
                addAbilities("Levitate", "Neutralizing Gas", "Stench");
                addEvolution("Unknown", "Unknown");
                addStats("340", "40", "65", "95", "60", "45", "35");
                addDetails("Koffing is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Gas Pokémon.");
            }
        }
        pokemonsByNN.put("109", new Pokemon109(this));

        class Pokemon110 extends ElePokemonBase {
            public Pokemon110(final ColPokedex collection) {
                super(collection, "Weezing");
                addBase("pokemon/pokemon_110_icon.png", "pokemon/pokemon_110.png", "110", "Poison Gas Pokémon", "1.2", "9.5");
                addTypes("Poison");
                addAbilities("Levitate", "Neutralizing Gas", "Stench");
                addEvolution("Unknown", "Unknown");
                addStats("490", "65", "90", "120", "85", "70", "60");
                addDetails("Weezing is a Poison type Pokémon introduced in Generation 1. It is known as the Poison Gas Pokémon.");
            }
        }
        pokemonsByNN.put("110", new Pokemon110(this));

        class Pokemon111 extends ElePokemonBase {
            public Pokemon111(final ColPokedex collection) {
                super(collection, "Rhyhorn");
                addBase("pokemon/pokemon_111_icon.png", "pokemon/pokemon_111.png", "111", "Spikes Pokémon", "1.0", "115.0");
                addTypes("Ground", "Rock");
                addAbilities("Lightning Rod", "Rock Head", "Reckless");
                addEvolution("None", "Rhydon");
                addStats("345", "80", "85", "95", "30", "30", "25");
                addDetails("Rhyhorn is a Ground/Rock type Pokémon introduced in Generation 1. It is known as the Spikes Pokémon.");
            }
        }
        pokemonsByNN.put("111", new Pokemon111(this));

        class Pokemon112 extends ElePokemonBase {
            public Pokemon112(final ColPokedex collection) {
                super(collection, "Rhydon");
                addBase("pokemon/pokemon_112_icon.png", "pokemon/pokemon_112.png", "112", "Drill Pokémon", "1.9", "120.0");
                addTypes("Ground", "Rock");
                addAbilities("Lightning Rod", "Rock Head", "Reckless");
                addEvolution("Rhyhorn", "Rhyperior");
                addStats("485", "105", "130", "120", "45", "45", "40");
                addDetails("Rhydon is a Ground/Rock type Pokémon introduced in Generation 1. It is known as the Drill Pokémon.");
            }
        }
        pokemonsByNN.put("112", new Pokemon112(this));

        class Pokemon113 extends ElePokemonBase {
            public Pokemon113(final ColPokedex collection) {
                super(collection, "Chansey");
                addBase("pokemon/pokemon_113_icon.png", "pokemon/pokemon_113.png", "113", "Egg Pokémon", "1.1", "34.6");
                addTypes("Normal");
                addAbilities("Natural Cure", "Serene Grace", "Healer");
                addEvolution("Happiny", "Blissey");
                addStats("450", "250", "5", "5", "35", "105", "50");
                addDetails("Chansey is a Normal type Pokémon introduced in Generation 1. It is known as the Egg Pokémon.");
            }
        }
        pokemonsByNN.put("113", new Pokemon113(this));

        class Pokemon114 extends ElePokemonBase {
            public Pokemon114(final ColPokedex collection) {
                super(collection, "Tangela");
                addBase("pokemon/pokemon_114_icon.png", "pokemon/pokemon_114.png", "114", "Vine Pokémon", "1.0", "35.0");
                addTypes("Grass");
                addAbilities("Chlorophyll", "Leaf Guard", "Regenerator");
                addEvolution("Unknown", "Unknown");
                addStats("435", "65", "55", "115", "100", "40", "60");
                addDetails("Tangela is a Grass type Pokémon introduced in Generation 1. It is known as the Vine Pokémon.");
            }
        }
        pokemonsByNN.put("114", new Pokemon114(this));

    }
}
