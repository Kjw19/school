package sm.school.domain.enumType;

import lombok.Getter;

@Getter
public enum MemRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    SUSPENDED("ROLE_SUSPENDED");

    MemRole(String value) {
        this.value = value;
    }

    private String value;
}
