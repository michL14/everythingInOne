package org.michorga.everythinginone.di

import org.michorga.everythinginone.domain.repository.PaymentRepository
import org.michorga.everythinginone.domain.repository.PaymentRepositoryImpl
import org.koin.dsl.module
import org.michorga.everythinginone.data.local.database.AppDatabase
import org.michorga.everythinginone.domain.repository.CategoryRepository
import org.michorga.everythinginone.domain.repository.CategoryRepositoryImpl
import org.michorga.everythinginone.domain.usecase.category.AddCategoryUseCase
import org.michorga.everythinginone.domain.usecase.category.CategoryUseCases
import org.michorga.everythinginone.domain.usecase.category.DeleteCategoryUseCase
import org.michorga.everythinginone.domain.usecase.category.GetAllCategoriesUseCase
import org.michorga.everythinginone.domain.usecase.transaction.AddTransactionUseCase
import org.michorga.everythinginone.domain.usecase.transaction.DeleteTransactionUseCase
import org.michorga.everythinginone.domain.usecase.transaction.GetTransactionsUseCase
import org.michorga.everythinginone.domain.usecase.transaction.TransactionUseCases
import org.michorga.everythinginone.presentation.home.vm.HomeViewModel
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel


val sharedModule = module {
    single { AppDatabase(driver = get()) }
    single { get<AppDatabase>().appQueries }

    // Repository
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }

    // UseCases
    single { GetTransactionsUseCase(get()) }
    single { AddTransactionUseCase(get()) }
    single { DeleteTransactionUseCase(get()) }

    single { CategoryUseCases(get(), get(), get()) }
    single { GetAllCategoriesUseCase(get()) }
    single { AddCategoryUseCase(get()) }
    single { DeleteCategoryUseCase(get()) }

    // ViewModels
    factory { PaymentViewModel(get(), get()) }
    factory { HomeViewModel() }
}

