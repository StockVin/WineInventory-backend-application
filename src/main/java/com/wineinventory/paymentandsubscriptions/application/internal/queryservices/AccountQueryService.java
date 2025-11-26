package com.wineinventory.paymentandsubscriptions.application.internal.queryservices;

import com.wineinventory.paymentandsubscriptions.domain.model.entities.Account;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.GetAccountByIdQuery;
import com.wineinventory.paymentandsubscriptions.domain.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryService {

    private final AccountRepository accountRepository;

    public AccountQueryService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account handle(GetAccountByIdQuery query) {
        return accountRepository.findByAccountId(query.accountId()).orElse(null);
    }
}
