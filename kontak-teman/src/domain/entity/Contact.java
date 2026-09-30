package domain.entity;

import java.util.Objects;

/** Entity immutable: perubahan menghasilkan instance baru. */
public class Contact {
    private final int id;
    private final String name;
    private final String phone;
    private final String email;

    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /** Parameter null berarti field dipertahankan. */
    public Contact withChanges(String name, String phone, String email) {
        return new Contact(
                id,
                Objects.requireNonNullElse(name, this.name),
                Objects.requireNonNullElse(phone, this.phone),
                Objects.requireNonNullElse(email, this.email));
    }
}
