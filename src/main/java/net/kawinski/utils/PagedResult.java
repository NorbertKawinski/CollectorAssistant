package net.kawinski.utils;

import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class PagedResult<T> {
    List<T> list;
    int pageSize;
    int recordsTotal;

    int firstPage;
    int lastPage;
    int currentPage;

    int previousPage;
    int nextPage;

    int recordsFrom;
    int recordsTo;

    public PagedResult(List<T> list, Page page, long records) {
        this.list = list;
        this.pageSize = page.getSize();
        this.recordsTotal = (int) records;

        this.firstPage = 1;
        if (recordsTotal == 0) {
            this.lastPage = 1;
        } else if (recordsTotal % page.getSize() == 0) {
            this.lastPage = recordsTotal / page.getSize();
        } else {
            this.lastPage = (recordsTotal / page.getSize()) + 1;
        }

//        this.currentPage = Math.max(firstPage, Math.min(lastPage, page.getNumber()));
        this.currentPage = page.getNumber();

        if (currentPage == firstPage) {
            previousPage = currentPage;
        } else {
            previousPage = currentPage - 1;
        }
        if (currentPage == lastPage) {
            nextPage = currentPage;
        } else {
            nextPage = currentPage + 1;
        }

        if (recordsTotal == 0) {
            recordsFrom = 0;
        } else {
            recordsFrom = page.firstElementNumber();
        }
        if (currentPage == lastPage) {
            recordsTo = recordsTotal;
        } else {
            recordsTo = (recordsFrom - 1) + pageSize;
        }
    }

    public List<Integer> getNeighborPages() {
        Set<Integer> pages = new HashSet<>();

        pages.add(currentPage);

        int neighborPages = 4;
        for(int i = 1; neighborPages > 0; ++i) {
            boolean assigned = false;

            if(currentPage - i >= firstPage) {
                pages.add(currentPage - i);
                neighborPages--;
                assigned = true;
            }

            if(currentPage + i <= lastPage) {
                pages.add(currentPage + i);
                neighborPages--;
                assigned = true;
            }

            if(!assigned) {
                break;
            }
        }

        if(pages.contains(firstPage + 1)) {
            pages.add(firstPage);
        }

        return pages.stream()
                .filter(p -> p >= firstPage && p <= lastPage)
                .sorted()
                .collect(Collectors.toList());
    }
}
