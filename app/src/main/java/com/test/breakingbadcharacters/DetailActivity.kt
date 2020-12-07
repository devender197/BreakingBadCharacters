package com.test.breakingbadcharacters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.test.breakingbadcharacters.databinding.DetailDescriptionBinding
import com.test.breakingbadcharacters.webApi.models.CharactersItem


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.detail_description)
        val character = intent.getSerializableExtra(Utils.INTENT_CHARACTER_KEY)
        if (character != null && character is CharactersItem) {
            binding.character = character
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_from_left_to_center_anim,
            R.anim.slide_from_center_to_right_anim
        )
    }
}