package com.example.aggim.mypage.main
/*
Updated by Jin Lee on 2021/02/04
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.aggim.R
import com.example.aggim.mypage.cart.CartItemsListActivity
import com.example.aggim.mypage.order.OrderListActivity
import com.example.aggim.mypage.stamp.StampActivity
import com.example.aggim.mypage.updateInfo.UpdateInfoActivity
import kotlinx.android.synthetic.main.activity_my_page_main.*
import kotlinx.android.synthetic.main.activity_my_page_main.toolbar

class MyPageMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_main)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Page"

        val pages = ArrayList<String>()

        pages.add("Reset member info")
        pages.add("Stamp")
        pages.add("Cart")
        pages.add("Orders")

        val pages_Adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pages)
        listPages.adapter = pages_Adapter

        listPages.choiceMode = ListView.CHOICE_MODE_SINGLE

        listPages.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, cities.get(position) + "를 클릭하셨습니다.", Toast.LENGTH_SHORT).show() // 선택한 데이터 출력
            if(position == 0) {
                val nextIntent = Intent(this, UpdateInfoActivity::class.java)
                startActivity(nextIntent)
            } else if (position == 1) {
                val nextIntent = Intent(this, StampActivity::class.java)
                startActivity(nextIntent)
            } else if(position == 2) {
                val nextIntent = Intent(this, CartItemsListActivity::class.java)
                startActivity(nextIntent)
            } else {
                val nextIntent = Intent(this, OrderListActivity::class.java)
                startActivity(nextIntent)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when(item.itemId) {
                android.R.id.home -> onBackPressed()
                else -> {}
            }
        }
        return true
    }
}
