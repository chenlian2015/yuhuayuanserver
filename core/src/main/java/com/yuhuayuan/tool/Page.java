package com.yuhuayuan.tool;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by cl on 2017/3/10.
 */

public class Page<T> implements Iterable<T>, Serializable {
    private final int number;
    private final int size;
    private long total;
    private List<T> content;

    public Page(int number, int size) {
        if(number > 0 && size > 0) {
            this.number = number;
            this.size = size;
            this.content = Collections.emptyList();
        } else {
            throw new IllegalArgumentException("number and size must gt 0!");
        }
    }

    public static <T> Page.PageBuilder<T> pageBuilder(int number, int size) {
        return new Page.PageBuilder(number, size);
    }

    public static <T> Page.PageBuilder<T> pageBuilder(Pageable pageable) {
        return new Page.PageBuilder(pageable.getNumber(), pageable.getSize());
    }

    public int getOffset() {
        return (this.number - 1) * this.size;
    }

    public int getTotalPages() {
        return (int)((this.total + (long)this.size - 1L) / (long)this.size);
    }

    public boolean hasNextPage() {
        return this.number < this.getTotalPages();
    }

    public boolean isLastPage() {
        return !this.hasNextPage();
    }

    public boolean hasPreviousPage() {
        return this.getNumber() > 1;
    }

    public boolean isFirstPage() {
        return !this.hasPreviousPage();
    }

    public boolean isEmpty() {
        return this.content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    public void setContent(List<T> concent) {
        if(concent == null) {
            this.content = Collections.emptyList();
        }

        this.content = concent;
    }

    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    public <R> Page<R> transform(Function<T, R> function) {
        Page page = new Page(this.number, this.size);
        page.setTotal(this.total);
        page.setContent((List)this.content.parallelStream().map(function).collect(Collectors.toList()));
        return page;
    }

    public int getNumber() {
        return this.number;
    }

    public int getSize() {
        return this.size;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Page)) {
            return false;
        } else {
            Page other = (Page)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getNumber() != other.getNumber()) {
                return false;
            } else if(this.getSize() != other.getSize()) {
                return false;
            } else if(this.getTotal() != other.getTotal()) {
                return false;
            } else {
                List this$content = this.getContent();
                List other$content = other.getContent();
                if(this$content == null) {
                    if(other$content != null) {
                        return false;
                    }
                } else if(!this$content.equals(other$content)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Page;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + this.getNumber();
        result1 = result1 * 59 + this.getSize();
        long $total = this.getTotal();
        result1 = result1 * 59 + (int)($total >>> 32 ^ $total);
        List $content = this.getContent();
        result1 = result1 * 59 + ($content == null?43:$content.hashCode());
        return result1;
    }

    public String toString() {
        return "Page(number=" + this.getNumber() + ", size=" + this.getSize() + ", total=" + this.getTotal() + ", content=" + this.getContent() + ")";
    }

    public static class PageBuilder<T> {
        private Page<T> page;

        public PageBuilder(int number, int size) {
            this.page = new Page(number, size);
        }

        public Page.PageBuilder<T> total(long total) {
            this.page.setTotal(total);
            return this;
        }

        public Page.PageBuilder<T> content(List<T> content) {
            this.page.setContent(content);
            return this;
        }

        public Page<T> build() {
            return this.page;
        }
    }
}
