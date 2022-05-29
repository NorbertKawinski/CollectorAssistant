package net.kawinski.collecting.data.repository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kawinski.collecting.data.model.BaseAttributeTemplate;
import net.kawinski.collecting.data.model.Category;
import net.kawinski.collecting.data.model.CollectionAttributeTemplate;
import net.kawinski.collecting.data.model.ElementAttributeTemplate;
import net.kawinski.utils.jee.NkBaseRepository;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

// No-args constructor is required for EJBs; injection still works fine with the @Inject constructor though (in case if needed).
@NoArgsConstructor(onConstructor_ = {@Deprecated})
@Stateless // The @Stateless annotation eliminates the need for manual transaction demarcation
@Slf4j
public class CategoryRepository extends NkBaseRepository<Long, Category> {

    public Category findRoot(final boolean joinSubcategories) {
        // Using the simple Adjacency Model where each row contains a reference to its parents
        //     which will refer to another row in same table doesn't co-operate well with JPA.
        // This is because JPA doesn't have support for generating queries using the Oracle CONNECT BY clause or the SQL standard WITH statement.
        // Without either of those 2 clauses its not really possible to make the Adjacency Model useful.
        // Category is using parent reference and so is prone to this issue.
        // Since we don't have many categories, we can recursively 'get subCategories()' on each node.
        // This way we'll produce a lot of selects, but since categories don't change often, we can just cache the result if needed.
        final Category root = getEntityManager().createQuery(
                "select cat from Category cat " +
                        "where cat.parent is null " +
                        "order by cat.name asc", Category.class)
                .getSingleResult();

        // Since we've only selected roots in the above query, we must now, recursively, expand ("join") subcategories
        if(joinSubcategories)
            //noinspection ResultOfMethodCallIgnored
            root.getSubCategories().size();
        return root;
    }

    public Category findRootJoinSubcategoriesRecursive() {
        final Category root = findRoot(true);
        expandSubCategories(root);
        return root;
    }

    private static void expandSubCategories(final Category parent) {
        for(final Category child : parent.getSubCategories())
            expandSubCategories(child);
    }

    public Optional<Category> findById(final long id, final boolean joinSubcategories, final boolean joinCollections, final boolean joinCollectionTemplate, final boolean joinElementTemplate) {
        final Optional<Category> result = getEntityManager().createQuery("select cat from Category cat " +
                "left join fetch cat.parent " + // Left join because not all categories have a parent (root categories)
//                We can't join many bags. We'll join them manually later.
                (joinSubcategories ? "left join fetch cat.subCategories " : "") +
//                (joinCollections ? "left join fetch cat.collections " : "") +
//                (joinCollectionTemplate ? "left join fetch cat.collectionTemplate " : "") +
//                (joinElementTemplate ? "left join fetch cat.elementTemplate " : "") +
                "where cat.id = :id", Category.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();

        // We're 'joining' collections manually here.
        // We can't 'join fetch' two associations in a single query (to avoid MultipleBagFetchException and Cartesian Product if using Set<>)
        if(joinCollections)
            //noinspection ResultOfMethodCallIgnored
            result.ifPresent(category -> category.getCollections().size());
        if(joinCollectionTemplate)
            //noinspection ResultOfMethodCallIgnored
            result.ifPresent(category -> category.getCollectionTemplate().size());
        if(joinElementTemplate)
            //noinspection ResultOfMethodCallIgnored
            result.ifPresent(category -> category.getElementTemplate().size());

        return result;
    }

    public Category findByIdOrThrow(final long id, final boolean joinSubcategories, final boolean joinCollections, final boolean joinCollectionTemplate, final boolean joinElementTemplate) {
        return findById(id, joinSubcategories, joinCollections, joinCollectionTemplate, joinElementTemplate)
                .orElseThrow(() -> new IllegalStateException("Couldn't find Category with id: " + id));
    }

    public List<CollectionAttributeTemplate> getCollectionAttributeTemplates(final Category categoryArg) {
        final Category category = manage(categoryArg); // We might receive this from unmanaged context

        final List<CollectionAttributeTemplate> result = new ArrayList<>(category.getCollectionTemplate());
        // Attribute templates are stored recursively
        category.getParent().ifPresent(parent -> result.addAll(getCollectionAttributeTemplates(parent)));

        result.sort(Comparator.comparingInt(BaseAttributeTemplate::getDisplayOrder));
        return result;
    }

    public List<ElementAttributeTemplate> getElementAttributeTemplates(final Category categoryArg) {
        final Category category = manage(categoryArg); // We might receive this from unmanaged context

        final List<ElementAttributeTemplate> result = new ArrayList<>(category.getElementTemplate());
        // Attribute templates are stored recursively
        category.getParent().ifPresent(parent -> result.addAll(getElementAttributeTemplates(parent)));

        result.sort(Comparator.comparingInt(BaseAttributeTemplate::getDisplayOrder));
        return result;
    }
}
