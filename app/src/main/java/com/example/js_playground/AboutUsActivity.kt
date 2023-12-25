package com.example.js_playground

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mehdi.sakout.aboutpage.Element
import mehdi.sakout.aboutpage.AboutPage
import java.util.Calendar

class AboutUsActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val adsElement = Element()
    adsElement.title = "Advertise with us"

    val aboutPage = AboutPage(this)
      .isRTL(false)
      .setDescription(getString(R.string.about_us))
      .addItem(Element().setTitle("Version 1.0"))
      .addItem(adsElement)
      .addGroup("Connect with us")
      .addEmail("td090833@gmail.com")
      .addWebsite("https://github.com/EunoiaSyndicate/JS-Playground")
      .addTwitter("stbasarab")
      .addYoutube("UCfs1OuF4pi9073ZZNssl4Sg")
      .addPlayStore("com.ideashower.readitlater.pro")
      .addInstagram("franzuz_oleksiy_90")
      .addGitHub("EunoiaSyndicate/JS-Playground")
      .addItem(getCopyRightsElement())
      .create()

    setContentView(aboutPage)

  }


  private fun getCopyRightsElement(): Element {
    val copyRightsElement = Element()
    val copyrights = getString(R.string.copy_right, Calendar.getInstance().get(Calendar.YEAR))
    copyRightsElement.title = copyrights
    copyRightsElement.setIconNightTint(android.R.color.white)
    copyRightsElement.gravity = Gravity.CENTER
    copyRightsElement.setOnClickListener {
      Toast.makeText(this@AboutUsActivity, copyrights, Toast.LENGTH_SHORT).show()
    }
    return copyRightsElement
  }
}