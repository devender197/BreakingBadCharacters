package com.test.breakingbadcharacters.viewModel

import com.test.breakingbadcharacters.webApi.models.Characters

interface OnResponseViewModel {
    fun onSuccess(response: Characters)
    fun onFailure(errorString : String)
}