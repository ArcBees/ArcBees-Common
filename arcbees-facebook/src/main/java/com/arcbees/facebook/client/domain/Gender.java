package com.arcbees.facebook.client.domain;

public enum Gender {
    MALE,
    FEMALE,
    UNSPECIFIED;

    public static Gender getGender(String gender) {
        try {
            return valueOf(gender.toUpperCase());
        } catch (Exception e) {
            return UNSPECIFIED;
        }
    }

    public boolean isMale() {
        return MALE.equals(this);
    }

    public boolean isFemale() {
        return this.equals(FEMALE);
    }
}
