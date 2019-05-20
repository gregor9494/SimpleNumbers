package com.gg.simplenumbers.presentation.numberslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.gg.simplenumbers.R
import com.gg.simplenumbers.databinding.ActivityNumbersListBinding
import com.gg.simplenumbers.presentation.numberslist.list.NumbersAdapter
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
        activityNumberListBinding.numbersList.adapter = NumbersAdapter()
    }
}
