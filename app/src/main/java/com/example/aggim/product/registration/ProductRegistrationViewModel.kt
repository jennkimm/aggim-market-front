package com.example.aggim.product.registration
/*
    Created by Jin Lee on 2021/01/31
 */
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aggim.api.request.ProductRegistrationRequest
import com.example.aggim.api.response.ApiResponse
import com.example.aggim.api.response.ProductImageUploadResponse
import com.example.aggim.product.category.categoryList
import kotlinx.coroutines.launch
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import retrofit2.Response

class ProductRegistrationViewModel(app:Application):
    BaseViewModel(app){

    val imageUrls: List<MutableLiveData<String?>> = listOf(
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?),
        MutableLiveData(null as String?)//사진 5장
    )

    val imageIds:MutableList<Long?> =
        mutableListOf(null, null, null, null, null)

    val productName = MutableLiveData("")
    val description = MutableLiveData("")
    val price = MutableLiveData("")
    val categories = MutableLiveData(categoryList.map{it.name})
    var categoryIdSelected: Int? = categoryList[0].id//기본값 첫 번째 값--error나면 얘가 범인임

    val descriptionLimit = 500
    val productNameLimit = 40

    val productNameLength = MutableLiveData("0/$productNameLimit")
    val descriptionLength = MutableLiveData("0/$descriptionLimit")

    var currentImageNum = 0 //오류나면 이놈이 범인

    fun checkProductNameLength(){
        productName.value?.let{
            if (it.length > productNameLimit){
                productName.value = it.take(productNameLimit)
            }
            productNameLength.value =
                "${productName.value?.length}/$productNameLimit"
        }
    }

    fun checkDescriptionLength(){
        description.value?.let{
            if (it.length > descriptionLimit){
                description.value = it.take(descriptionLimit)
            }
            descriptionLength.value =
                "${description.value?.length}/$descriptionLimit"
        }
    }

    fun onCategorySelect(position: Int){
        categoryIdSelected = categoryList[position].id
    }

    fun pickImage(imageNum : Int){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply{
            type = "image/*"
        }

        intent.resolveActivity(app.packageManager)?.let {
            startActivityForResult(intent, REQUEST_PICK_IMAGES)
        }
        currentImageNum = imageNum
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (resultCode != RESULT_OK)
            return

        when (requestCode){
            REQUEST_PICK_IMAGES -> data?.let { uploadImage(it) }
        }
    }

    private fun uploadImage(intent: Intent)=
        getContent(intent.data)?.let{ imageFile ->
            viewModelScope.launch {
                val response = ProductImageUploader().upload(imageFile)
                onImageUploadResponse(response)
            }
        }
    private  fun onImageUploadResponse(
        response: ApiResponse<ProductImageUploadResponse>
    ){
        if (response.success && response.data != null){
            imageUrls[currentImageNum].value = response.data.filePath
            imageIds[currentImageNum] = response.data.productImageId
        }else{
            toast(response.message?:"An unknown error has occurred.")
        }
    }

    suspend fun register(){
        val request = extractRequest()
        val response = ProductRegistrar().register(request)
        onRegistrationResponse(response as ApiResponse<Response<Void>>)
    }

    private fun extractRequest(): ProductRegistrationRequest =
        ProductRegistrationRequest(
            productName.value,
            description.value,
            price.value?.toIntOrNull(),
            categoryIdSelected,
            imageIds
        )

    private fun onRegistrationResponse(
        response: ApiResponse<Response<Void>>
    ){
        if(response.success){
            confirm("Product has registered."){
                finishActivity()
            }
        } else {
            toast(response.message ?: "An unknown error has occurred.")
        }
    }

    companion object{
        const val REQUEST_PICK_IMAGES = 0
    }

}

