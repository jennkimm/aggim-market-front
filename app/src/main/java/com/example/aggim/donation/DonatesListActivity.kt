package com.example.aggim.donation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.aggim.R
import com.example.aggim.api.response.ApiResponse
import com.example.aggim.api.response.DonateListItemResponse

class DonatesListActivity : AppCompatActivity() {
    private val newDonateActivityRequestCode = 1
    private val donatesListViewModel by viewModels<DonatesListViewModel> {
        DonatesListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_list_activity_main)


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val donatesAdapter = DonatesAdapter {donateListItemResponse -> adapterOnClick(donateListItemResponse)}
        val headerAdapter = HeaderAdapter()
        val concatAdapter = ConcatAdapter(headerAdapter, donatesAdapter)
        var amt = 0
        recyclerView.adapter = concatAdapter


        donatesListViewModel.donatesLiveData.observe(this, {
            it?.let {
                donatesAdapter.submitList(it as MutableList<DonateListItemResponse>)
                for (i in it) {
                    amt += i.donatedVal
                }
                headerAdapter.updateAmountDonated(amt)
            }
        })
    }

    private fun adapterOnClick(donate: DonateListItemResponse) {
        //기부 정보 디테일 activity
        Toast.makeText(this, "Detail", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == newDonateActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                //val flowerName = data.getStringExtra(FLOWER_NAME)
                //val flowerDescription = data.getStringExtra(FLOWER_DESCRIPTION)

                //donatesListViewModel.insertFlower(flowerName, flowerDescription)
            }
        }
    }
}