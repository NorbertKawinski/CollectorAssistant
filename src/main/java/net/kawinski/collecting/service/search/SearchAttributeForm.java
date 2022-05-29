package net.kawinski.collecting.service.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kawinski.collecting.data.search.SearchAttributeComparator;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAttributeForm {
    private String name;
    private SearchAttributeComparator comparator;
    private String value;
    private List<String> values;

    public SearchAttributeForm(String name, SearchAttributeComparator comparator, String value) {
        this(name, comparator, value, null);
    }

    public SearchAttributeForm(String name, SearchAttributeComparator comparator, List<String> values) {
        this(name, comparator, null, values);
    }
}
