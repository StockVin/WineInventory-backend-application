package com.wineinventory.paymentandsubscriptions.application.external.acl;

import com.wineinventory.paymentandsubscriptions.application.internal.commandservices.AccountCommandService;
import com.wineinventory.paymentandsubscriptions.application.internal.queryservices.SubscriptionQueryService;
import com.wineinventory.paymentandsubscriptions.application.internal.queryservices.AccountQueryService;
import com.wineinventory.paymentandsubscriptions.domain.model.entities.Account;
import com.wineinventory.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.GetPlanProductsLimitByAccountIdQuery;
import com.wineinventory.paymentandsubscriptions.domain.model.queries.GetAccountByIdQuery;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PaymentAndSubscriptionsFacade {

    private final AccountCommandService accountCommandService;
    private final SubscriptionQueryService subscriptionQueryService;
    private final AccountQueryService accountQueryService;

    public PaymentAndSubscriptionsFacade(
            AccountCommandService accountCommandService,
            SubscriptionQueryService subscriptionQueryService,
            AccountQueryService accountQueryService
    ) {
        this.accountCommandService = accountCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
        this.accountQueryService = accountQueryService;
    }


    public Optional<Account> createAccount(String role, String businessId) {
        try {
            var command = new CreateAccountCommand(businessId, role);
            var account = accountCommandService.handle(command);
            return Optional.ofNullable(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public Optional<Integer> getPlanProductsLimitByAccountId(String accountId) {
        try {
            var query = new GetPlanProductsLimitByAccountIdQuery(accountId);
            var result = subscriptionQueryService.handle(query);
            return Optional.ofNullable(result);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    public Optional<Account> findAccountByIdAsync(String accountId) {
        try {
            var query = new GetAccountByIdQuery(accountId);
            var account = accountQueryService.handle(query);
            return Optional.ofNullable(account);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
