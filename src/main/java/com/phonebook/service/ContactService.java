package com.phonebook.service;

import com.phonebook.controller.ContactFilter;
import com.phonebook.model.Contact;
import com.phonebook.repository.ContactFilterQuery;
import com.phonebook.repository.ContactRepository;
import com.phonebook.utils.exception.Exception;
import com.phonebook.utils.exception.Exception.EmptyPhonesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

	@Autowired
	private ContactRepository repository;

	public Contact addNewContact(Contact contact) {
		if (contact.getPhone() == null || contact.getPhone().isEmpty()) {
			throw new EmptyPhonesException();
		}
		return repository.save(contact);
	}

	public Page<Contact> listContacts(final ContactFilter filter, final Pageable pageable) {
		return repository.findAll(new ContactFilterQuery(filter).buildFilter(), pageable);
	}

	public void deleteContact(Long id) {
		repository.deleteById(id);
	}

	public Contact editContact(Contact contactEdited) {
		if (contactEdited.getPhone() == null || contactEdited.getPhone().isEmpty()) {
			throw new Exception.EmptyPhonesException();
		}

		Contact contactSaved = repository.getOne(contactEdited.getId());

		contactSaved.setEmail(contactEdited.getEmail());
		contactSaved.setFirstName(contactEdited.getFirstName());
		contactSaved.setLastName(contactEdited.getLastName());
		contactSaved.setPhone(contactEdited.getPhone());

		return repository.save(contactSaved);
	}
}
