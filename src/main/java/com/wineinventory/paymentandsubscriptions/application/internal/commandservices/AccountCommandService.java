package com.wineinventory.paymentandsubscriptions.application.internal.commandservices;

import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.entities.Account;
import com.wineinventory.paymentandsubscriptions.domain.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountCommandService {

    private final AccountRepository accountRepository;

    public AccountCommandService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account handle(CreateAccountCommand command) {
        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId, command.role(), command.businessId());
        return accountRepository.save(account);
    }
}
