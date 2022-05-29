package net.kawinski.collecting;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;

import java.io.File;

public class CaTestHelper {

    public static Archive<?> createFullTestArchive() {
        // We want to reproduce the production build as closely as possible,
        // so we're referencing production pom.xml
        return ShrinkWrap.create(MavenImporter.class)
                .loadPomFromFile("pom.xml")
                .importBuildOutput()
                .as(WebArchive.class)
                // Once we imported pom.xml, we can now replace some files if needed
                // We need to replace persistence.xml to make sure that deploying artifact will cause tables to recreate
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
        ;
    }

//    public static Archive<?> createTestArchive2() {
//        return createTestArchive(false);
//    }
//
//    public static Archive<?> createTestArchive(final boolean includeWeb) {
//        final File WEBAPP_SRC = new File("src/main/webapp");
//        final File WEBAPP_WEB_INF = new File(WEBAPP_SRC, "WEB-INF");
//        final File BEANS_XML = new File(WEBAPP_WEB_INF, "beans.xml");
//
//        final WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war")
//                .addPackages(true, "net.kawinski")
//                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
////                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsWebInfResource(BEANS_XML, "beans.xml")
//
//                // If needed, we can switch from default in-memory database to something else:
////            .addAsWebInfResource("test-ds.xml") // Deploy our test datasource
//                ;
//
//        if(includeWeb)
//            archive.addAsWebResource(WEBAPP_SRC);
//
//        return archive;
//    }

    public enum ApplicationTemplate {
        /**
         * Totally empty server with no data.
         * Good for unit testing
         */
        EMPTY,

        /**
         * Setup as if it's a fresh installation.
         * Contains default setup that exists in all server instances just after installation.
         */
        DEFAULT,

        /**
         * Contains some data as if the server was in production for a while.
         */
        EXAMPLE1
    }

    public static void setupApplication(final ApplicationTemplate template) {
        switch (template) {
            case EMPTY:
                // TODO: setup
                break;
            case DEFAULT:
                // TODO: setup def
                break;
            case EXAMPLE1:
                // TODO: setup ex1
                break;
        }
    }

}
