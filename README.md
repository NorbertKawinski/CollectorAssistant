
# TODO:
- (VHIGH) Limit number of collections/elements/attribute/upload for a user
- (HIGH) Add "import from backup" admin function
- (HIGH) Add "resynchronize elasticsearch" admin function
- (HIGH) Uzupelnic brakujace testy (E2E tests with REST only. Web layer is too dynamic for now)
- (LOW) https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
  The issue with Hibernate is that ID is not available before persisting
  Because of that we cannot use ID in equals
  Workaround that with:
    - DON'T persist model, create modelDraft instead (mapped into model by MapStruct right before persisting)
    - Once persisted, we can use IDs as usual :>
    - Create appropriate CaBaseModel which contains Generated id and appropriate equals method (by id only)
    - Also don't check isInstance because Hibernate creates proxies and messes everything :<
- (LOW) - Dodać kolumnę "valueNumeric" żeby komparatory potrafiły wyszukiwać <=>
    * DATE zmienic ze stringa na "valueNumeric"
- (LOW) Zamiast rzucac wszedzie wyjatki - redirect na "userError.xhtml" z alertem z info co jest nie tak.
  	--> Obsluga "throw new UserErrorException("message")" przez ExceptionMapper
- (LOW) Check if findById(id, joinXXX) joins unnecessary things (like createAttributeTemplate searches for category and joins collections...)
- (VLOW) Move entities over to MapStruct, example: Category -> { CategoryModel, Category, CategoryRepresentation }
    --> Separate SQL queries (and DTOs) for CollectionLite, CollectionDetails, Collection<scenarioName>
- (VLOW-Marcin) User->Excel export microservice
- (VLOW) Jakakolwiek logika ukrywania kolekcji, ktore nie maja zdefiniowanych wszystkich wymaganych atrybutow
- (VLOW) Sort search results by category,owner,name,id (in this order by default)
- (VLOW) BUG: Uploads stay on disk even if transaction fails. TODO: Delete upload on transaction rollback
- (VLOW) Add ability to delete custom icon (so that it will automatically generate from image again)
    --> When uploading image *ALWAYS* generate icon from it? Then user can manually change that icon
    mapped by MapStruct
- (VVVLOW) 'NULL' should find collection with the attribute not-set
  --> Maybe later, at the moment I can't even think of the SQL query that would work for that.
  We're quering for all collection attributes and finding a match in any of them. And that works
  But I can't think of a way to find a "no-match" in a list
- (VVVLOW) Move "missing elements from collection" to a subpage to allow easy paging
- (VVVLOW) Skontrolowac query jakie Hibernate wykonuje w aplikacji
    --> Dziala i sa wazniejsze rzeczy, ale docelowo dobrze byloby zoptymalizowac conieco
    szczegolnie odswiezanie kategorii zrobic jako jedno query, w ktorym potem drzewko kategorii jest skladane recznie w aplikacji
- (VVVLOW) i18n
    --> Duzo roboty, na razie nie potrzebne
- (VVVLOW) REST API
    --> Duzo roboty, na razie nie potrzebne
- (VVVLOW) New attribute type allowing to reference other collections/elements/profiles
    --> Duzo roboty, user moze w najgorszym razie po prostu dac link w opisie
- (LOWEST) Improve barcodes:
    * Add "Random" and "Same as ID" buttons when creating new attribute 
        --> Add @elvariable id="parentId" to the template, when this variable is present, 'same as parent' button is generated
- (IDEA) Tags instead of categories. Some collections/elements can belong to more than one category 
- (COSMETIC) HomePage: A few sentences describing the application
- (COSMETIC) Display news, popular collections, your collections, whatever on the HomePage
- (COSMETIC) Ability to create collections from "My collections" page
- (COSMETIC) Render "user" next to collection in "category overview" page
- (COSMETIC) Add optional "description" field for categories. This allows to let the users know what the category is about.
- (COSMETIC-M) Description in "Help" page
- (COSMETIC-M) Description of "Rules" page

# Collector Assistant

Collector Assistant is an app for managing collections.

Yada yada **TODO: More description**

# Run (DockerHub)
To run the Collector Assistant, you have to install:
- **Docker** --> https://www.docker.com/

Internally the server is running on a JEE stack using WildFly as an application server and a database for storage.
Setting all this up from ground-up is a lot of hassle but don\'t worry, you can setup the server using Docker with just a single command.
> WORK IN PROGRESS, NOT AVAILABLE YET: docker pull nkawinski/CollectorAssistant

> docker-compose up -d

To shutdown the server:
> docker-compose down

To force rebuild the server
> docker-compose up -d --build app_server



# Compile

To build the CollectorAssistant.war yourself, you\'ll additionally need to install:

- **JDK** (version **11**) --> https://adoptopenjdk.net/
  - Remember to set JAVA_HOME variable as well since because it's used by Maven
- **Maven** --> http://maven.apache.org/

Compilling CollectorAssistant is equally simple. You can do it with just a single click (double, actually) by running the following script:

> @build.cmd

This script will:
* Compile Collector Assistant
* Run automatic tests
* Setup and start an application server and a database
with Collector Assistant installed on it
* Open Collector Assistant

You can customize the behavior of this script by modifying appropriate variables in the build.cmd file.
