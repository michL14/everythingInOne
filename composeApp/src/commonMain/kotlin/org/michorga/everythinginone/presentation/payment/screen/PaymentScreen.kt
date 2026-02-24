package org.michorga.everythinginone.presentation.payment.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michorga.everythinginone.presentation.payment.component.ExpenseToolBar
import org.michorga.everythinginone.presentation.payment.vm.PaymentViewModel


@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    modifier: Modifier = Modifier
) {
//    val expenses by viewModel.expenses.collectAsState()
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Row(
            modifier = Modifier
                .padding(vertical = 15.dp)
        ){
            ExpenseToolBar(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

//            items(expenses) { expense ->
////                ExpenseItem(
////                    expenseCategory = expense.category,
////                    expenseAmount = expense.amount
////                )
//            }
        }
    }

}

