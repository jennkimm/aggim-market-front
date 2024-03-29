package com.example.aggim.product.detail
/*
    Created by Jin Lee on 2021/01/31
    Updated by Jin Lee on 2021/02/04
 */
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView
import kotlin.reflect.KClass

class ProductDetailActivity : BaseActivity<ProductDetailViewModel>(){

    override val viewModelType= ProductDetailViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F1F2F9")))
        supportActionBar?.setElevation(0f)

        val viewModel=getViewModel()
        val productId = intent.getLongExtra(PRODUCT_ID, -1)

        viewModel.loadDetail(productId)
        ProductDetailUI(getViewModel()).setContentView(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let{
            when(item.itemId){
                android.R.id.home -> onBackPressed()
                else -> {}
            }
        }
        return true
    }

    companion object{
        val PRODUCT_ID = "productId"
    }
}

