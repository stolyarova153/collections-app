package ru.spbpu.collections.model;

public class CollectionFilterValues {

    private static final String POPULARITY = "popularity";
    private static final String SIZE = "size";

    public static String getProperty(final String filter) {

        return switch (filter) {
            case POPULARITY -> "global.likes";
            case SIZE -> "itemsCount";
            default -> "name";
        };
    }
}
