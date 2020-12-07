package com.test.breakingbadcharacters.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.breakingbadcharacters.repository.CharacterSearchRepository
import com.test.breakingbadcharacters.webApi.models.Characters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 * @characterSearchRepository instance of repository, will be provided by kodein
 */
class SearchResultActivityViewModel(characterSearchRepository: CharacterSearchRepository) :
    ViewModel() {

    val mCharacterSearchRepository = characterSearchRepository
    var characterMutableLiveData = MutableLiveData<Characters>()
    var errorMutableLiveData = MutableLiveData<String>()
    var originalCharacterList: Characters? = null
    var isSearchBarVisible = false
    var autoTextSearchArrayList = ArrayList<String>()

    /**
     * function responsible for call repository function to get value of the web api
     * onResponse from repository we will going to update the @characterMutableLiveData and in case
     * of error @errorMutableLiveData variable will be updated
     */
    fun getCharacterData() {
        mCharacterSearchRepository.getBadCharacters(object : OnResponseViewModel {
            override fun onSuccess(response: Characters) {
                characterMutableLiveData.value = response
                originalCharacterList = response
                setAutoAdapterString(response)
            }

            override fun onFailure(errorString: String) {
                errorMutableLiveData.value = errorString
            }
        })
    }


    /**function to filter the character data appeared given season and changes to live data*/
    fun getFilteredList(season: Int) {
        if (season == 6) {
            characterMutableLiveData.value = originalCharacterList
        } else {
            //In case of long list, setting the sorting on separate thread
            CoroutineScope(Dispatchers.Main).launch {
                val filterCharacter = originalCharacterList?.filter {
                    if (it.appearance != null) {
                        it.appearance.contains(season)
                    } else {
                        false
                    }
                }
                val characters = Characters()
                if (filterCharacter != null) {
                    for (item in filterCharacter) {
                        characters.add(item)
                    }
                }
                characterMutableLiveData.value = characters
            }.start()
        }
    }

    /** function to filter the list on the basis of character name*/
    fun getFilteredListSearch(searchKey: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val filterCharacter = originalCharacterList?.filter {
                if (it.name != null) {
                    it.name.toLowerCase(Locale.ROOT).contains(searchKey.toLowerCase())
                } else {
                    false
                }
            }
            val characters = Characters()
            if (filterCharacter != null) {
                for (item in filterCharacter) {
                    characters.add(item)
                }
            }
            characterMutableLiveData.value = characters
        }.start()
    }

    /**function to maintain list of name in character. This list used by autoCompleteTextView in
     * activity to provide suggestion on typing*/
    fun setAutoAdapterString(characters: Characters?) {
        if (characters != null) {
            for (item in characters) {
                autoTextSearchArrayList.add(item.name)
            }
        }
    }


}