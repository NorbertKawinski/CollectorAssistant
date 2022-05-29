package net.kawinski.collecting.presentation.controllers.security;

import net.kawinski.collecting.data.model.Collection;
import net.kawinski.collecting.data.model.CollectionAttribute;
import net.kawinski.collecting.data.model.CollectionVisibility;
import net.kawinski.collecting.data.model.Element;
import net.kawinski.collecting.data.model.ElementAttribute;
import net.kawinski.collecting.data.model.Group;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.presentation.controllers.user.LoggedInUser;
import net.kawinski.utils.PagedResult;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class AccessController implements Serializable {

    @Inject
    private LoggedInUser loggedInUser;

    public boolean isLoggedIn() {
        return loggedInUser.isLoggedIn();
    }

    public void permitLoggedIn() {
        if(!isLoggedIn()) {
            throw new RuntimeException("This feature is only accessible to logged-in users");
        }
    }

    public boolean isAdmin() {
        return loggedInUser.is(Group.ADMIN);
    }

    public void permitAdmin() {
        if(!isAdmin()) {
            throw new RuntimeException("This feature is only accessible for service administrators");
        }
    }

    public void permitCanSee(Collection collection) {
        if(!canSee(collection)) {
            throw new RuntimeException("Not authorized to see collection #" + collection.getId());
        }
    }

    public boolean canSee(Collection collection) {
        if(collection.getVisibility() == CollectionVisibility.PUBLIC)
            return true;
        return canEdit(collection);
    }

    public PagedResult<Collection> filterCanSeeCollections(PagedResult<Collection> collections) {
        collections.getList()
                .removeIf(collection -> !canSee(collection));
        return collections;
    }

    public List<Collection> filterCanSeeCollections(List<Collection> collections) {
        return collections.stream()
                .filter(this::canSee)
                .collect(Collectors.toList());
    }

    public void permitCanSeeCollections(List<Collection> collections) {
        if(!collections.stream().allMatch(this::canSee))
            throw new RuntimeException("Cannot see all entries in the provided list");
    }

    public void permitCanSee(CollectionAttribute attribute) {
        permitCanSee(attribute.getCollection());
    }

    public boolean canSee(CollectionAttribute attribute) {
        return canSee(attribute.getCollection());
    }

    public void permitCanSee(Element element) {
        if(!canSee(element)) {
            throw new RuntimeException("Not authorized to see element #" + element.getId());
        }
    }

    public boolean canSee(Element element) {
        return canSee(element.getPresentIn());
    }

    public PagedResult<Element> filterCanSeeElements(PagedResult<Element> elements) {
        elements.getList()
                .removeIf(element -> !canSee(element));
        return elements;
    }

    public List<Element> filterCanSeeElements(List<Element> elements) {
        return elements.stream()
                .filter(this::canSee)
                .collect(Collectors.toList());
    }

    public void permitCanSee(ElementAttribute attribute) {
        permitCanSee(attribute.getElement());
    }

    public boolean canSee(ElementAttribute attribute) {
        return canSee(attribute.getElement());
    }

    public void permitCanEdit(Collection collection) {
        if(!canEdit(collection)) {
            throw new RuntimeException("Not authorized to edit collection #" + collection.getId());
        }
    }

    public boolean canEdit(Collection collection) {
        return isAdmin() || isOwner(collection);
    }

    public void permitCanEdit(CollectionAttribute attribute) {
        permitCanEdit(attribute.getCollection());
    }

    public boolean canEdit(CollectionAttribute attribute) {
        return canEdit(attribute.getCollection());
    }

    public void permitCanEdit(Element element) {
        if(!canEdit(element)) {
            throw new RuntimeException("Not authorized to edit element #" + element.getId());
        }
    }

    public boolean canEdit(Element element) {
        return canEdit(element.getPresentIn());
    }

    public void permitCanEdit(ElementAttribute attribute) {
        permitCanEdit(attribute.getElement());
    }

    public boolean canEdit(ElementAttribute attribute) {
        return canEdit(attribute.getElement());
    }

    public boolean isOwner(Collection collection) {
        return loggedInUser.getLoggedInUser()
                .map(user -> user.equalsId(collection.getOwner()))
                .orElse(false);
    }

    public boolean isOwner(Element element) {
        return isOwner(element.getPresentIn());
    }

    public boolean canEdit(User user) {
        return isAdmin() || isUser(user);
    }

    public void permitCanEdit(User user) {
        if(!canEdit(user)) {
            throw new RuntimeException("Not authorized to access personal content of user " + user.getId());
        }
    }

    public boolean isUser(User user) {
        return loggedInUser.getLoggedInUser()
                .filter(logged -> logged.equalsId(user))
                .isPresent();
    }
}
