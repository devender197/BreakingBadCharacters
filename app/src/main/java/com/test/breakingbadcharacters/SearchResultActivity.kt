package com.test.breakingbadcharacters


import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.android.material.snackbar.Snackbar
import com.test.breakingbadcharacters.repository.CharacterSearchRepository
import com.test.breakingbadcharacters.viewModel.SearchResultActivityViewModel
import com.test.breakingbadcharacters.viewModel.SearchResultActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class SearchResultActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private lateinit var viewModel: SearchResultActivityViewModel
    private val mAdapter = RecyclerViewAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        /**viewModel and viewModelFactory  instance  creation*/
        val characterSearchRepository: CharacterSearchRepository by instance()
        val viewModelFactory = SearchResultActivityViewModelFactory(characterSearchRepository)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(SearchResultActivityViewModel::class.java)

        viewModel.getCharacterData()

        setUpProgressBar()

        setVisiblitySearchBar()

        setUpCharacterMutableLiveData()

        setUpErrorMutableLiveData()

        setUpFloatingOptions()

        setUpProgressBar()

        setUpRecyclerView()

        setUpRemoveFilter()

        setUpAutoCompleteTextView()

    }

    private fun setUpAutoCompleteTextView(){
       autoCompleteTxtView.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                filterCharacter()
                Utils.showKeyBoard(autoCompleteTxtView, false)
                return false
            }
        })
    }

    /*** function to setup progress bar*/
    private fun setUpProgressBar() {
        progress_bar.indeterminateDrawable = ThreeBounce()
    }

    /*** function to setup recyclerView*/
    private fun setUpRecyclerView() {
        recycler_view.also {
            val layoutManager = GridLayoutManager(this@SearchResultActivity, 2)
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.setHasFixedSize(true)
        }
    }

    /*** function to setup auto complete textview*/
    private fun setAutoCompleteTextView(listName: ArrayList<String>) {
        val adapterString =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, listName)
        autoCompleteTxtView.apply {
            threshold = 1
            setAdapter(adapterString)
        }
    }

    /*** function execute on search bar click*/
    fun onSearch(view: View) {
        filterCharacter()
    }

    fun filterCharacter(){
        viewModel.isSearchBarVisible = !viewModel.isSearchBarVisible
        val searchString = autoCompleteTxtView.text.toString()
        if (searchString.isNotEmpty()) {
            viewModel.getFilteredListSearch(searchString)
            closeSearch.visibility = View.VISIBLE
        }
        autoCompleteTxtView.setText("")
        setVisiblitySearchBar()
    }

    /*** function to setup visibility of search bar, search bar will visible when click on
     * search button in toolbar*/
   private fun setVisiblitySearchBar() {
        if (viewModel.isSearchBarVisible) {
            autoCompleteTxtView.visibility = View.VISIBLE
            Utils.showKeyBoard(autoCompleteTxtView, true)
            bblogImgView.visibility = View.GONE
            searchListImgView.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_arrow_forward_24
                )
            )
        } else {
            autoCompleteTxtView.visibility = View.GONE
            Utils.showKeyBoard(autoCompleteTxtView, false)
            bblogImgView.visibility = View.VISIBLE
            searchListImgView.setImageDrawable(ContextCompat.
                getDrawable(this, R.drawable.ic_baseline_search_24))
        }
    }

    /**function to setup floating button option. It is third party library.*/
   private fun setUpFloatingOptions() {
        fab_l.setMiniFabsColors(
            R.color.breaking_bad_color,
            R.color.breaking_bad_color,
            R.color.breaking_bad_color,
            R.color.breaking_bad_color,
            R.color.breaking_bad_color
        )
        fab_l.setMainFabOnClickListener {
            //closing menus
            fab_l.closeOptionsMenu()
        }

        fab_l.setMiniFabSelectedListener {
            when (it.itemId) {
                R.id.season1 -> {
                    viewModel.getFilteredList(1)
                    fab_l.closeOptionsMenu()
                }
                R.id.season2 -> {
                    viewModel.getFilteredList(2)
                    fab_l.closeOptionsMenu()
                }
                R.id.season3 -> {
                    viewModel.getFilteredList(3)
                    fab_l.closeOptionsMenu()
                }
                R.id.season4 -> {
                    viewModel.getFilteredList(4)
                    fab_l.closeOptionsMenu()
                }
                R.id.season5 -> {
                    viewModel.getFilteredList(5)
                    fab_l.closeOptionsMenu()
                }
            }
            closeSearch.visibility = View.VISIBLE
        }
    }

    /*** function to setup character mutable live data. On any data change due to filter and webdata
     * below code will be executed*/
    private fun setUpCharacterMutableLiveData() {
        // observe character data get by calling web api
        viewModel.characterMutableLiveData.observe(this, {
            Utils.logDisplay("CHARACTER DATA", it.toString())
            if (it != null && it.isNotEmpty()) {
                recycler_view.visibility = View.VISIBLE
                txtNoSearchResult.visibility = View.GONE
                progress_bar_layout.visibility = View.GONE
                val anim =
                    AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom_to_center_anim)
                recycler_view.startAnimation(anim)
                if (mAdapter.itemCount == 0) {
                    recycler_view.adapter = mAdapter
                }
                mAdapter.addAll(it)
                mAdapter.notifyDataSetChanged()
            } else {
                recycler_view.visibility = View.GONE
                txtNoSearchResult.visibility = View.VISIBLE
            }
            setAutoCompleteTextView(viewModel.autoTextSearchArrayList)
        })
    }

    /**function to setup error mutable live data. On any error below code will execute*/
    private fun setUpErrorMutableLiveData() {
        // observe error generate during web api call
        viewModel.errorMutableLiveData.observe(this, {
            Utils.logDisplay("CHARACTER ERROR", it.toString())
            txtNoSearchResult.visibility = View.VISIBLE
            progress_bar_layout.visibility = View.GONE
            Snackbar.make(
                parentRelativeLayout,
                getString(R.string.networkError),
                Snackbar.LENGTH_LONG
            ).show()
        })
    }

    /** function to setup close search button. On click the clear any filter apply and populate
     * list with original data*/
    private fun setUpRemoveFilter() {
        closeSearch.setOnClickListener {
            viewModel.isSearchBarVisible = false
            setVisiblitySearchBar()
            viewModel.getFilteredList(6)
            closeSearch.visibility = View.GONE
        }
    }

}