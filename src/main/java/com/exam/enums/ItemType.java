package com.exam.enums;

public enum ItemType {
	ONE("1"), TWO("2"), THREE("3");

    private final String value;

    ItemType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ItemType fromValue(String value) {
        for (ItemType type : ItemType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }
}
