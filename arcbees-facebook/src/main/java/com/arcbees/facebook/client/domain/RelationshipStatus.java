package com.arcbees.facebook.client.domain;

public enum RelationshipStatus {
    SINGLE("single"),
    IN_RELATIONSHIP("relationship"),
    ENGAGED("engaged"),
    MARRIED("married"),
    COMPLICATED("complicated"),
    OPEN_RELATIONSHIP("open"),
    WIDOWED("widowed"),
    SEPARATED("separated"),
    DIVORCED("divorced"),
    CIVIL_UNION("civil union"),
    DOMESTIC_PARTNERSHIP("domestic"),
    UNSPECIFIED("unspecified");

    private final String keyword;

    public static RelationshipStatus getRelationshipStatus(String status) {
        if (status != null) {
            for (RelationshipStatus relationshipStatus : values()) {
                if (status.contains(relationshipStatus.keyword)) {
                    return relationshipStatus;
                }
            }
        }

        return UNSPECIFIED;
    }

    RelationshipStatus(String keyword) {
        this.keyword = keyword;
    }
}
