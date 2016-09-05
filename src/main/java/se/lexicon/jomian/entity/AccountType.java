package se.lexicon.jomian.entity;

/**
 * @author Johan Gustafsson
 * @since 2016-09-05.
 */
public enum AccountType {
    STUDENT('S'),
    TEACHER('T'),
    ADMIN('A');
    private char id;

    AccountType(char id) {
        this.id = id;
    }

    public static AccountType getType(Character id) {
        for (AccountType accountType : AccountType.values()) {
            if (id.equals(accountType.getId())) {
                return accountType;
            }
        }
        throw new IllegalArgumentException("No matching type for id: " + id);
    }

    private char getId() {
        return id;
    }
}
