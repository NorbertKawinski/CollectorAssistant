import os
import time

from PIL import Image
from bs4 import BeautifulSoup, Tag
import requests

base_url = "https://pokemondb.net"
out_dir = "C:\\tmp\\20200919-pokemon"
out_file = f"{out_dir}\\out.txt"
scrape_start = 0
scrape_limit = 200
nat_ids_scrapped = set()

class Pokemon:
    # national_number
    # description
    pass


def escaped(s: str):
    return s \
        .replace("\"", "\\\"")


def scrape_pokemon(name, icon_url, details_url):
    pokemon = Pokemon()
    pokemon.name = name

    page_resp = requests.get(f"{base_url}{details_url}")
    page_text = page_resp.text
    soup = BeautifulSoup(page_text)
    main = soup.find("main")

    ###
    # Description
    # Conveniently, while no id/class is available, the description is located in the first <p> tag
    # description = main.children[2 lub 3].text
    pokemon.description = main.find("p").text

    ###
    # First table
    vitals_table = soup.find("table", {"class": "vitals-table"})
    vitals_table_body: Tag = vitals_table.find("tbody")
    # vitals_rows = vitals_table_body.find_all("tr")
    vitals_tds = vitals_table_body.find_all("td")

    pokemon.national_number = vitals_tds[0].text
    p_types = vitals_tds[1].find_all("a")
    pokemon.types = [a.text for a in p_types]
    pokemon.species = vitals_tds[2].text
    pokemon.height = vitals_tds[3].text.split(" ")[0]
    pokemon.height = pokemon.height[0:len(pokemon.height) - 2]  # Strip " m"
    pokemon.weight = vitals_tds[4].text.split(" ")[0]
    pokemon.weight = pokemon.weight[0:len(pokemon.weight) - 3]  # Strip " kg"
    p_abilities = vitals_tds[5].find_all("a")
    pokemon.abilities = [a.text for a in p_abilities]

    ###
    # Image
    img_out_path = f"{out_dir}\\pokemon_{pokemon.national_number}.png"
    if not os.path.exists(img_out_path):
        tabset_basics = main.find("div", {"class": "tabset-basics"})
        # img_a = tabset_basics.find("a")
        # img = img_a.children[0]
        img = tabset_basics.find("img")
        img_url = img["src"]
        img_file = Image.open(requests.get(img_url, stream=True).raw)
        # resize while keeping aspect ratio (bigger side = 128px)
        img_w, img_h = img_file.size
        img_aspect_ratio = float(img_w) / float(img_h)
        if img_w > img_h:
            img_file = img_file.resize((128, round(128 / img_aspect_ratio)))
        else:
            img_file = img_file.resize((round(128 * img_aspect_ratio), 128))
        img_file.save(img_out_path)

    ###
    # Icon
    icon_out_path = f"{out_dir}\\pokemon_{pokemon.national_number}_icon.png"
    if not os.path.exists(icon_out_path):
        icon_img = Image.open(requests.get(icon_url, stream=True).raw)
        icon_img.save(icon_out_path)

    ###
    # Base stats
    stats_table_header = soup.find("h2", string="Base stats")
    stats_table_parent = stats_table_header.parent
    stats_table = stats_table_parent.find("table", {"class": "vitals-table"})
    stats_table_body: Tag = stats_table.find("tbody")
    stats_rows = stats_table_body.find_all("tr")
    pokemon.hp = stats_rows[0].find_all("td")[0].text
    pokemon.attack = stats_rows[1].find_all("td")[0].text
    pokemon.defense = stats_rows[2].find_all("td")[0].text
    pokemon.attack_speed = stats_rows[3].find_all("td")[0].text
    pokemon.defense_speed = stats_rows[4].find_all("td")[0].text
    pokemon.speed = stats_rows[5].find_all("td")[0].text
    stats_table_footer: Tag = stats_table.find("tfoot")
    stats_footer_rows = stats_table_footer.find_all("tr")
    pokemon.total = stats_footer_rows[0].find_all("td")[0].text

    ###
    # Evolution
    evo_div = soup.find("div", {"class": "infocard-list-evo"})
    evo_stages = evo_div.find_all("div", {"class": "infocard"})
    if len(evo_stages) == 3:
        pokemon.evolved_from = "None"
        pokemon.evolves_into = "None"
        stage_1 = evo_stages[0].find("a", {"class": "ent-name"}).text
        stage_2 = evo_stages[1].find("a", {"class": "ent-name"}).text
        stage_3 = evo_stages[2].find("a", {"class": "ent-name"}).text
        if pokemon.name == stage_1:
            pokemon.evolves_into = stage_2
        elif pokemon.name == stage_2:
            pokemon.evolved_from = stage_1
            pokemon.evolves_into = stage_3
        elif pokemon.name == stage_3:
            pokemon.evolved_from = stage_2
    else:
        pokemon.evolved_from = "Unknown"
        pokemon.evolves_into = "Unknown"

    ###
    # To file
    with open(f"{out_dir}\\template.txt", "r") as template_f:
        template = template_f.read()
        out = template \
            .replace("%POKEMON_NATIONAL_NUMBER%", pokemon.national_number) \
            .replace("%POKEMON_NAME%", pokemon.name) \
            .replace("%POKEMON_TYPES%", "\"" + "\", \"".join(pokemon.types) + "\"") \
            .replace("%POKEMON_SPECIES%", pokemon.species) \
            .replace("%POKEMON_HEIGHT%", pokemon.height) \
            .replace("%POKEMON_WEIGHT%", pokemon.weight) \
            .replace("%POKEMON_ABILITIES%", "\"" + "\", \"".join(pokemon.abilities) + "\"") \
            .replace("%POKEMON_EVOLVED_FROM%", pokemon.evolved_from) \
            .replace("%POKEMON_EVOLVES_INTO%", pokemon.evolves_into) \
            .replace("%POKEMON_BASE_TOTAL%", pokemon.total) \
            .replace("%POKEMON_BASE_HP%", pokemon.hp) \
            .replace("%POKEMON_BASE_ATTACK%", pokemon.attack) \
            .replace("%POKEMON_BASE_DEFENSE%", pokemon.defense) \
            .replace("%POKEMON_BASE_ATTACK_SPEED%", pokemon.attack_speed) \
            .replace("%POKEMON_BASE_DEFENSE_SPEED%", pokemon.defense_speed) \
            .replace("%POKEMON_BASE_SPEED%", pokemon.speed) \
            .replace("%POKEMON_DESCRIPTION%", escaped(pokemon.description))
        # A little trick to convert from whatever encoding this currently is to utf-8
        out = out.encode(encoding="utf-8").decode(encoding="utf-8")
        with open(f"{out_file}", "a", encoding="utf-8") as f:
            f.write(out)


def scrape_pokedex_row(row: Tag):
    cells: Tag = row.find_all("td")

    idx = cells[0].text
    name = cells[1].find("a").text
    print(f"- Name: {name}")
    if idx in nat_ids_scrapped:
        print("- Already scrapped")
        return  # Already scrapped. Probably some "Maga" variant of the same Pokemon
    nat_ids_scrapped.add(idx)

    icon: Tag = cells[0].find("span", {"class": "img-fixed"})
    icon_url = icon["data-src"]

    details_url = cells[1].find("a")["href"]
    scrape_pokemon(name, icon_url, details_url)

    print("- OK")


def scrape_pokedex():
    print("Scraping pokedex")
    root_page_resp = requests.get(f"{base_url}/pokedex/all")
    root_page_text = root_page_resp.text
    root_soup = BeautifulSoup(root_page_text)
    pokedex_table: Tag = root_soup.find("table", {"id": "pokedex"})
    pokedex_table_body: Tag = pokedex_table.find("tbody")
    pokedex_rows = pokedex_table_body.find_all("tr")

    # 0..10 OK
    # 10..100 OK
    for i in range(scrape_start, min(scrape_limit, len(pokedex_rows))):
        print(f"Scraping next ({i}) row")
        scrape_pokedex_row(pokedex_rows[i])
        time.sleep(0.1)



# main()
if os.path.exists(out_file):
    os.remove(out_file)
scrape_pokedex()
