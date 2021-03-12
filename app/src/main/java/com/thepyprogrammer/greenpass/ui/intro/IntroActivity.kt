package com.thepyprogrammer.greenpass.ui.intro

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.os.Bundle
import com.thepyprogrammer.greenpass.R


class IntroActivity : MaterialIntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSlide(
                SlideFragmentBuilder()
                        .backgroundColor(R.color.fourth_slide_background)
                        .buttonsColor(R.color.fourth_slide_buttons)
                        .title("That's it")
                        .description("Would you join us?")
                        .build()
        )
    }
}