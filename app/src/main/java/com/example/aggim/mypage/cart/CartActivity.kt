package com.example.aggim.mypage.cart

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aggim.R
import com.example.aggim.common.Prefs
import com.example.aggim.data.CartItem
import com.example.aggim.mypage.buy.BuyProduct
import com.example.aggim.mypage.updateInfo.UpdateInfoActivity
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_my_page_main.*
import kotlinx.android.synthetic.main.cart_item.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title=""
        val adapter = RecyclerAdapterItems(Prefs.cList, this)
        rv_view.adapter = adapter
        rv_view.layoutManager = LinearLayoutManager(this)
        rv_view.setHasFixedSize(true)
        //xml_cit_button.setOnClickListener{
        //   Prefs.cList.removeAt(pos)
        //}
        var sum = 0
        if(Prefs.cList.size > 0) {
            for(i in 0..Prefs.cList.size-1) {
                sum += Prefs.cList.get(i).price
            }
        }
        if(sum > 0)
            sumMoney.text = sum.toString()+"원"
        buyItem.setOnClickListener{
            val nextIntent = Intent(this, BuyProduct::class.java)
            startActivity(nextIntent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let{
            when(item.itemId){
                android.R.id.home -> onBackPressed()
                else ->{}
            }
        }
        return true
    }
}