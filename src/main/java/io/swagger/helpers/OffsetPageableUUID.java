package io.swagger.helpers;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetPageableUUID implements Pageable {
    private int limit;
    private int offset;

    // Constructor could be expanded if sorting is needed
    private Sort sort = new Sort(Sort.Direction.DESC, "uuid");
    public OffsetPageableUUID(Integer limit, Integer offset) {
        if(offset == null)
            offset = 0;
        if(limit == null)
            limit = 9999;

        if (limit < 1)
            throw new IllegalArgumentException("Limit must not be less than one!");

        if (offset < 0)
            throw new IllegalArgumentException("Offset index must not be less than zero!");

        this.limit = limit;
        this.offset = offset;
    }
    @Override
    public int getPageNumber() {
        return offset / limit;
    }
    @Override
    public int getPageSize() {
        return limit;
    }
    @Override
    public long getOffset() {
        return offset;
    }
    @Override
    public Sort getSort() {
        return sort;
    }
    @Override
    public Pageable next() {
        // Typecast possible because number of entries cannot be bigger than integer (primary key is integer)
        return new OffsetPageableUUID(getPageSize(), (int) (getOffset() + getPageSize()));
    }
    public Pageable previous() {
        // The integers are positive. Subtracting does not let them become bigger than integer.
        return hasPrevious() ?
                new OffsetPageableUUID(getPageSize(), (int) (getOffset() - getPageSize())): this;
    }
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }
    @Override
    public Pageable first() {
        return new OffsetPageableUUID(getPageSize(), 0);
    }
    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

}
