package springboard.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"pageable", "sort"})
public class PageWrapper<T> implements Page<T>, Serializable {

    public static final int DEFAULT_PAGE_SIZE = 20;

    List<T> content;
    int number;
    int size = DEFAULT_PAGE_SIZE;
    long totalElements;
    int totalPages;

    public PageWrapper() { }

    public PageWrapper(List<T> content, int number, int size, long totalElements) {
        this.content = content;
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        if(size <= 0) size = (int)totalElements;
        totalPages = (int)(totalElements / size);
        if(totalElements % size > 0) totalPages += 1;
        if(number < 0) number = 0;
        if(number >= totalPages) number = totalPages - 1;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getNumberOfElements() {
        return number * size;
    }


    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean hasContent() {
        return content != null && content.size() > 0;
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public boolean isFirst() {
        return number <= 0;
    }

    @Override
    public boolean isLast() {
        return number >= getTotalPages() - 1;
    }

    @Override
    public boolean hasNext() {
        return number < getTotalPages() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return number > 0 && getTotalPages() > 0;
    }

    @Override
    public Pageable nextPageable() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public Pageable previousPageable() {
        throw new RuntimeException("Not Implemented");
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> mapper) {
        List content = this.content.stream().map(e -> mapper.apply(e)).collect(Collectors.toList());
        return new PageWrapper<>(content, number, size, totalElements);
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public boolean isEmpty() {
        return !hasContent();
    }

    @Override
    public String toString() {
        return "PageWrapper{" +
                "content=" + content +
                ", number=" + number +
                ", size=" + size +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }

}
