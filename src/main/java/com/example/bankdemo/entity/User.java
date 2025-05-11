package com.example.bankdemo.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmailData> emails = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PhoneData> phones = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    // По желанию конструктор с обязательными полями
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Set<EmailData> getEmails() {
        return emails;
    }

    public Set<PhoneData> getPhones() {
        return phones;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addEmail(EmailData emailData) {
        emails.add(emailData);
        emailData.setUser(this);
    }

    public void removeEmail(EmailData emailData) {
        emails.remove(emailData);
        emailData.setUser(null);
    }

    public void addPhone(PhoneData phoneData) {
        phones.add(phoneData);
        phoneData.setUser(this);
    }

    public void removePhone(PhoneData phoneData) {
        phones.remove(phoneData);
        phoneData.setUser(null);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setUser(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setUser(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmails(Set<EmailData> emails) {
        this.emails = emails;
    }

    public void setPhones(Set<PhoneData> phones) {
        this.phones = phones;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
