package com.test.breakingbadcharacters.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.breakingbadcharacters.repository.CharacterSearchRepository

/**
 * ViewModel factory class to pass variable using viewModelProvider
 */
class SearchResultActivityViewModelFactory(private val repository: CharacterSearchRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchResultActivityViewModel(repository) as T
    }
}