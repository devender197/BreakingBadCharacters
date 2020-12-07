package com.test.breakingbadcharacters

import android.app.Application
import android.content.Context
import com.test.breakingbadcharacters.repository.CharacterSearchRepository
import com.test.breakingbadcharacters.viewModel.SearchResultActivityViewModel
import com.test.breakingbadcharacters.webApi.retrofit.ApiCall
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ApplicationClass : Application(), KodeinAware{

    companion object {
       private var application: Context? = null

        fun getInstance(): Context? {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = applicationContext
    }

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ApplicationClass))
        bind() from singleton { ApiCall() }
        bind() from singleton { CharacterSearchRepository(instance()) }
    }
}