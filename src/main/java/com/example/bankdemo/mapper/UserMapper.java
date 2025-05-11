package com.example.bankdemo.mapper;

import com.example.bankdemo.dto.UserDto;
import com.example.bankdemo.entity.EmailData;
import com.example.bankdemo.entity.PhoneData;
import com.example.bankdemo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "emails", source = "emails", qualifiedByName = "emailsToStrings")
    @Mapping(target = "phones", source = "phones", qualifiedByName = "phonesToStrings")
    default UserDto toDto(User user) {
        return null;
    }

    @Named("emailsToStrings")
    default Set<String> emailsToStrings(Set<EmailData> emails) {
        if (emails == null) return null;
        return emails.stream()
                .map(EmailData::getEmail)
                .collect(Collectors.toSet());
    }

    @Named("phonesToStrings")
    default Set<String> phonesToStrings(Set<PhoneData> phones) {
        if (phones == null) return null;
        return phones.stream()
                .map(PhoneData::getPhone)
                .collect(Collectors.toSet());
    }
}