package com.ubs.cpt.infra.util;

import java.util.*;

/**
 * @author Amar Pandav
 */
public class ListSplitter<T> {
    public static final int MAXIMUM_NUMBER_OF_COLLECTION_ITEMS = 900;

    private final int blockSize;
    private final List<T> ids;

    private final int numberOfBlocks;

    public ListSplitter(List<T> ids, int blockSize) {
        this.ids = ids;
        this.blockSize = blockSize;
        this.numberOfBlocks = (ids == null || ids.isEmpty()) ? -1 : (int) Math.ceil((double) ids.size() / blockSize);

    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getNumberOfBlocks() {
        return numberOfBlocks;
    }

    public Iterator<List<T>> iterator() {
        return new Iterator<List<T>>() {
            private int page = 0;

            @Override
            public boolean hasNext() {
                return page < numberOfBlocks;
            }

            @Override
            public List<T> next() {
                if (page == numberOfBlocks) {
                    throw new NoSuchElementException();
                }

                int currentPage = page++;
                return ids.subList(blockSize * currentPage, Math.min(blockSize * (currentPage + 1), ids.size()));
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static <T> Iterator<List<T>> splitCollection(Collection<T> collection) {
        return splitCollection(new ArrayList<>(collection), MAXIMUM_NUMBER_OF_COLLECTION_ITEMS);
    }

    public static <T> Iterator<List<T>> splitCollection(List<T> list, int blockSize) {
        // A negative block size doesn't make any sense. Very unlikely, but let's be paranoid here.
        return new ListSplitter<T>(list, Math.max(blockSize, 1)).iterator();
    }
}
