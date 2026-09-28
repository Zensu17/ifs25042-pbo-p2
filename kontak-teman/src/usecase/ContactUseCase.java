package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.repository.IContactRepository;
import java.util.List;
import java.util.Optional;

public class ContactUseCase {
    private final IContactRepository repository;

    public ContactUseCase(IContactRepository repository) {
        this.repository = repository;
    }

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Contact addContact(String name, String phone, String email) {
        return repository.save(name, phone, email);
    }

    public boolean removeContact(int id) {
        return repository.deleteById(id);
    }

    /** Parameter null berarti field tidak diubah. @return true jika kontak ditemukan */
    public boolean updateContact(int id, String name, String phone, String email) {
        Optional<Contact> found = repository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Contact contact = found.get();
        if (name != null) {
            contact.changeName(name);
        }
        if (phone != null) {
            contact.changePhone(phone);
        }
        if (email != null) {
            contact.changeEmail(email);
        }

        repository.update(contact);
        return true;
    }

    public List<Contact> searchContacts(String keyword) {
        String lower = keyword.toLowerCase();
        return repository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(lower))
                .toList();
    }

    public List<Contact> sortContacts(SortOption option) {
        return repository.findAll().stream().sorted(option.comparator()).toList();
    }
}
