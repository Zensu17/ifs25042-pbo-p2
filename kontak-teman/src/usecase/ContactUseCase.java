package usecase;

import domain.entity.Contact;
import domain.entity.SortOption;
import domain.repository.IContactRepository;
import java.util.List;
import java.util.Locale;

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
        return repository.findById(id)
                .map(contact -> contact.withChanges(name, phone, email))
                .map(repository::update)
                .orElse(false);
    }

    public List<Contact> searchContacts(String keyword) {
        String lower = keyword.toLowerCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(c -> c.getName().toLowerCase(Locale.ROOT).contains(lower))
                .toList();
    }

    public List<Contact> sortContacts(SortOption option) {
        return repository.findAll().stream().sorted(option.comparator()).toList();
    }
}
