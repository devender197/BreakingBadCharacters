package com.test.breakingbadcharacters.webApi


interface OnResponse {
    fun onSuccess(response: Any)
    fun onFailure(errorString : String)
}