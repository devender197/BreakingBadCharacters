package com.test.breakingbadcharacters.webApi.retrofit


import com.test.breakingbadcharacters.webApi.retrofit.ApiConstant
import com.test.breakingbadcharacters.webApi.models.Characters
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET(ApiConstant.SEARCH_CHARACTER)
    fun getSearchRequest(): Call<Characters>

}