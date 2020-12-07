package com.test.breakingbadcharacters.webApi.retrofit

import com.test.breakingbadcharacters.Utils.logDisplay
import com.test.breakingbadcharacters.webApi.OnResponse
import com.test.breakingbadcharacters.webApi.models.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ApiCall class has all the web api calls
 */
class ApiCall {
    private var mApiInterface = RetrofitClient.getRetrofitClient()?.create(ApiInterface::class.java)
    private var TAG = ApiCall::class.java.simpleName

    /**
     * search function is a get request to get data from the webservice
     * @onResponse is interface to provide callback after getting result
     */
    fun getSearchResult(onResponse: OnResponse) {
        mApiInterface?.getSearchRequest()?.enqueue(object : Callback<Characters> {

            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                val responseCode = response.code();
                if( responseCode == 200) {
                    response.body()?.let { onResponse.onSuccess(it) }
                    logDisplay(TAG, "onSuccess: ${response.isSuccessful}")
                }else{
                    onResponse.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                logDisplay(TAG, "onFailure: ${t.message}")
                onResponse.onFailure("Oops! something went wrong!")
            }
        })
    }
    /**getSearchResult ends here*/

}