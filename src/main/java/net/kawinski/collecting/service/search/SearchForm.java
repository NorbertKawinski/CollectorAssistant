package net.kawinski.collecting.service.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchForm extends BaseSearchForm {
    private Long categoryId;
    private String name;
    private String owner;
    private String collection;
    private String category;
    private SearchCollectionVisibility visibility;
    private List<SearchAttributeForm> attributes;

    public SearchForm() {
        visibility = SearchCollectionVisibility.PUBLIC;
        attributes = new ArrayList<>();
    }

    public void addAttribute(SearchAttributeForm attribute) {
        attributes.add(attribute);
    }
}
