package com.jirapave.cli.config;

import com.jirapave.cli.persistence.store.PaymentStore;
import com.jirapave.cli.persistence.store.impl.InMemoryPaymentStore;
import com.jirapave.cli.services.PaymentService;
import com.jirapave.cli.services.impl.BasicPaymentService;
import dagger.Module;
import dagger.Provides;

/**
 * DI configuration for Dagger
 */
@Module(library = true, injects = { PaymentService.class, PaymentStore.class })
public class DependencyConfig {

    @Provides
    public PaymentStore obtainStore(){
        return new InMemoryPaymentStore();
    }

    @Provides
    public PaymentService obtainPaymentService(){
        return new BasicPaymentService(obtainStore());
    }

}
