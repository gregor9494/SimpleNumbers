package com.gg.simplenumbers.presentation.numberslist

import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gg.simplenumbers.R
import com.gg.simplenumbers.databinding.ActivityNumbersListBinding
import com.gg.simplenumbers.presentation.common.EventObserver
import com.gg.simplenumbers.presentation.numberslist.list.NumbersAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_numbers_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NumbersListActivity : AppCompatActivity() {
    private val numbersListViewModel: NumbersListViewModel by viewModel()
    private lateinit var activityNumberListBinding: ActivityNumbersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNumberListBinding = DataBindingUtil.setContentView(this, R.layout.activity_numbers_list)
        activityNumberListBinding.lifecycleOwner = this
        activityNumberListBinding.viewModel = numbersListViewModel
        activityNumberListBinding.loadMoreListener = numbersListViewModel
        numbersList.adapter = NumbersAdapter()
        observeErrorMessage()
    }

    fun showAddNumberDialog(v: View) {
        val dialog = AlertDialog.Builder(this)
            .setView(R.layout.enter_number_dialog)
            .setNegativeButton(R.string.enter_number_dialog_cancel_button_text, { dialog, which -> })
            .setPositiveButton(R.string.enter_number_dialog_add_button_text) { dialog, which ->
                with((dialog as AlertDialog).findViewById<EditText>(R.id.etNumber)?.text.toString().toIntOrNull()) {
                    numbersListViewModel.addNewNumber(this)
                }
            }
            .create()
        dialog.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
    }

    fun observeErrorMessage() {
        numbersListViewModel.errorMessage.observe(this, EventObserver {
            Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
        })
    }

}
