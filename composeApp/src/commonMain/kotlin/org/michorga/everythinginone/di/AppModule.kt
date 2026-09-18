package org.michorga.everythinginone.di

import org.koin.dsl.module
import org.michorga.everythinginone.data.local.database.AppDatabase
import org.michorga.everythinginone.domain.repository.CategoryRepository
import org.michorga.everythinginone.domain.repository.CategoryRepositoryImpl
import org.michorga.everythinginone.domain.repository.PaymentRepository
import org.michorga.everythinginone.domain.repository.PaymentRepositoryImpl
import org.michorga.everythinginone.domain.repository.SalaryRepository
import org.michorga.everythinginone.domain.repository.SalaryRepositoryImpl
import org.michorga.everythinginone.domain.repository.SubscriptionRepository
import org.michorga.everythinginone.domain.repository.SubscriptionRepositoryImpl
import org.michorga.everythinginone.domain.usecase.category.AddCategoryUseCase
import org.michorga.everythinginone.domain.usecase.category.CategoryUseCases
import org.michorga.everythinginone.domain.usecase.category.DeleteCategoryUseCase
import org.michorga.everythinginone.domain.usecase.category.GetAllCategoriesUseCase
import org.michorga.everythinginone.domain.usecase.salary.AddSalaryUseCase
import org.michorga.everythinginone.domain.usecase.salary.ApplyRecurringSalaryUseCase
import org.michorga.everythinginone.domain.usecase.salary.DeleteSalaryUseCase
import org.michorga.everythinginone.domain.usecase.salary.GetCurrentBalanceUseCase
import org.michorga.everythinginone.domain.usecase.salary.GetSalariesUseCase
import org.michorga.everythinginone.domain.usecase.salary.SalaryUseCases
import org.michorga.everythinginone.domain.usecase.subscription.AddSubscriptionUseCase
import org.michorga.everythinginone.domain.usecase.subscription.ApplyDueSubscriptionsUseCase
import org.michorga.everythinginone.domain.usecase.subscription.DeleteSubscriptionUseCase
import org.michorga.everythinginone.domain.usecase.subscription.GetSubscriptionChargesUseCase
import org.michorga.everythinginone.domain.usecase.subscription.GetSubscriptionsUseCase
import org.michorga.everythinginone.domain.usecase.subscription.MarkSubscriptionPaidUseCase
import org.michorga.everythinginone.domain.usecase.subscription.MarkSubscriptionUnpaidUseCase
import org.michorga.everythinginone.domain.usecase.subscription.SetSubscriptionActiveUseCase
import org.michorga.everythinginone.domain.usecase.subscription.SubscriptionUseCases
import org.michorga.everythinginone.domain.usecase.subscription.UpdateSubscriptionUseCase
import org.michorga.everythinginone.domain.usecase.transaction.AddTransactionUseCase
import org.michorga.everythinginone.domain.usecase.transaction.DeleteTransactionUseCase
import org.michorga.everythinginone.domain.usecase.transaction.GetTransactionsUseCase
import org.michorga.everythinginone.domain.usecase.transaction.TransactionUseCases
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel
import org.michorga.everythinginone.presentation.subscription.vm.UpcomingPaymentViewModel


val sharedModule = module {
    single { AppDatabase(driver = get()) }
    single { get<AppDatabase>().appQueries }

    // Repository
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
    single<SalaryRepository> { SalaryRepositoryImpl(get()) }
    single<SubscriptionRepository> { SubscriptionRepositoryImpl(get()) }

    // UseCases
    single { GetTransactionsUseCase(get()) }
    single { AddTransactionUseCase(get()) }
    single { DeleteTransactionUseCase(get()) }
    single { TransactionUseCases(get(), get(), get()) }

    single { CategoryUseCases(get(), get(), get()) }
    single { GetAllCategoriesUseCase(get()) }
    single { AddCategoryUseCase(get()) }
    single { DeleteCategoryUseCase(get()) }

    single { GetCurrentBalanceUseCase(get()) }
    single { AddSalaryUseCase(get()) }
    single { ApplyRecurringSalaryUseCase(get()) }
    single { GetSalariesUseCase(get()) }
    single { DeleteSalaryUseCase(get()) }
    single { SalaryUseCases(get(), get(), get(), get(), get()) }

    single { GetSubscriptionsUseCase(get()) }
    single { AddSubscriptionUseCase(get()) }
    single { UpdateSubscriptionUseCase(get()) }
    single { SetSubscriptionActiveUseCase(get()) }
    single { DeleteSubscriptionUseCase(get()) }
    single { ApplyDueSubscriptionsUseCase(get()) }
    single { MarkSubscriptionPaidUseCase(get()) }
    single { MarkSubscriptionUnpaidUseCase(get()) }
    single { GetSubscriptionChargesUseCase(get()) }
    single { SubscriptionUseCases(get(), get(), get(), get(), get(), get(), get(), get(), get()) }

    // ViewModels
    factory { PaymentViewModel(get(), get(), get()) }
    factory { HomeViewModel(get(), get(), get()) }
    factory { UpcomingPaymentViewModel(get()) }
}

