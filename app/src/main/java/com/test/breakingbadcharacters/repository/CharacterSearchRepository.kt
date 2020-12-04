package com.test.breakingbadcharacters.repository

import com.test.breakingbadcharacters.viewModel.OnResponseViewModel
import com.test.breakingbadcharacters.webApi.OnResponse
import com.test.breakingbadcharacters.webApi.models.Characters
import com.test.breakingbadcharacters.webApi.retrofit.ApiCall
import com.test.breakingbadcharacters.webApi.retrofit.ApiConstant.FAILURE_RESPONSE


/**
 * Repository class to access the web data source for character Search
 * @apiCall:  instance of apiCall class to get data from web api
 * reference to constructor to avoid dependency and will get it from application reference
 */
class CharacterSearchRepository(apiCall: ApiCall) {
    val mApiCall = apiCall
    /**
     * @onResponseViewModel: interface implementation to response back to viewModel
     * function is used to call api to get character response from the web api. we cast the response
     * to @Character as web api onResponse provides with generic response
     */
    fun getBadCharacters(onResponseViewModel: OnResponseViewModel) {
        mApiCall.getSearchResult(object : OnResponse {
            override fun onSuccess(response: Any) {
                // Smart Cast
                if (response is Characters) {
                    onResponseViewModel.onSuccess(response)
                }else{
                    onResponseViewModel.onFailure(FAILURE_RESPONSE)
                }
            }

            override fun onFailure(errorString: String) {
                onResponseViewModel.onFailure(errorString)
            }

        })
    }


}