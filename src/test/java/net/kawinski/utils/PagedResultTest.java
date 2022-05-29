package net.kawinski.utils;

import org.junit.Test;
import org.wildfly.security.util.EnumerationIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PagedResultTest {
    private static final List<Object> list_0 = generate(0);
    private static final List<Object> list_1 = generate(1);
    private static final List<Object> list_9 = generate(9);
    private static final List<Object> list_10 = generate(10);
    private static final List<Object> list_11 = generate(11);
    private static final List<Object> list_55 = generate(55);
    private static final int PAGE_SIZE = 10;

    private static List<Object> generate(int records) {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < records; ++i) {
            list.add(new Object());
        }
        return list;
    }

    private static PagedResult<Object> getResult(List<Object> list, int page) {
        return new PagedResult<>(list.stream()
                .limit(PAGE_SIZE)
                .collect(Collectors.toList()),
                new Page(page, PAGE_SIZE),
                list.size()
        );
    }

    @Test
    public void test_list0() {
        PagedResult<Object> result = getResult(list_0, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(1));
        assertThat(result.getNextPage(), equalTo(1));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(0));
        assertThat(result.getRecordsTo(), equalTo(0));
    }

    @Test
    public void test_list1() {
        PagedResult<Object> result = getResult(list_1, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(1));
        assertThat(result.getNextPage(), equalTo(1));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(1));
        assertThat(result.getRecordsTo(), equalTo(1));
    }

    @Test
    public void test_list9() {
        PagedResult<Object> result = getResult(list_9, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(1));
        assertThat(result.getNextPage(), equalTo(1));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(1));
        assertThat(result.getRecordsTo(), equalTo(9));
    }

    @Test
    public void test_list10() {
        PagedResult<Object> result = getResult(list_10, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(1));
        assertThat(result.getNextPage(), equalTo(1));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(1));
        assertThat(result.getRecordsTo(), equalTo(10));
    }

    @Test
    public void test_list11_page1() {
        PagedResult<Object> result = getResult(list_11, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(2));
        assertThat(result.getNextPage(), equalTo(2));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(1));
        assertThat(result.getRecordsTo(), equalTo(10));
    }

    @Test
    public void test_list11_page2() {
        PagedResult<Object> result = getResult(list_11, 2);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(2));
        assertThat(result.getNextPage(), equalTo(2));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(11));
        assertThat(result.getRecordsTo(), equalTo(11));
    }

    @Test
    public void test_list55_page1() {
        PagedResult<Object> result = getResult(list_55, 1);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(6));
        assertThat(result.getNextPage(), equalTo(2));
        assertThat(result.getPreviousPage(), equalTo(1));
        assertThat(result.getRecordsFrom(), equalTo(1));
        assertThat(result.getRecordsTo(), equalTo(10));
    }

    @Test
    public void test_list55_page3() {
        PagedResult<Object> result = getResult(list_55, 3);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(6));
        assertThat(result.getNextPage(), equalTo(4));
        assertThat(result.getPreviousPage(), equalTo(2));
        assertThat(result.getRecordsFrom(), equalTo(21));
        assertThat(result.getRecordsTo(), equalTo(30));
    }

    @Test
    public void test_list55_page6() {
        PagedResult<Object> result = getResult(list_55, 6);
        assertThat(result.getFirstPage(), equalTo(1));
        assertThat(result.getLastPage(), equalTo(6));
        assertThat(result.getNextPage(), equalTo(6));
        assertThat(result.getPreviousPage(), equalTo(5));
        assertThat(result.getRecordsFrom(), equalTo(51));
        assertThat(result.getRecordsTo(), equalTo(55));
    }

    @Test
    public void test_incrementOutOfBoundsPageNumber() {
        PagedResult<Object> result = getResult(list_55, 0);
        assertThat(result.getCurrentPage(), equalTo(1));
    }

    @Test
    public void test_decrementOutOfBoundsPageNumber() {
        PagedResult<Object> result = getResult(list_55, 7);
        assertThat(result.getCurrentPage(), equalTo(6));
    }

}
