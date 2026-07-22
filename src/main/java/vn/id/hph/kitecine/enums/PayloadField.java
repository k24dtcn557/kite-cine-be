package vn.id.hph.kitecine.enums;

import lombok.Getter;

@Getter
public enum PayloadField {
    TITLE("title"),
    CONTENT("content");

    private final String value;

    PayloadField(String value) {
        this.value = value;
    }
}
