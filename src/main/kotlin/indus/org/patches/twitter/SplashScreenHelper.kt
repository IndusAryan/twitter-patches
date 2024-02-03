package indus.org.patches.twitter

import app.revanced.patcher.data.ResourceContext

class SplashScreenHelper {

    fun changeSplashScreen(context: ResourceContext) {

        context.xmlEditor["res/values/styles.xml"].use { editor ->
            val document = editor.file
            val styles = document.getElementsByTagName("style")
            for (i in 0 until styles.length) {
                val styleNode = styles.item(i)
                if (styleNode.attributes.getNamedItem("name")?.nodeValue == "Theme.LaunchScreen") {

                    val styleItems = setOf(
                        "windowSplashScreenAnimatedIcon" to "@drawable/ic_vector_twitter_white",
                    )

                    styleItems.forEach { (k, v) ->
                        val styleElement = document.createElement("item")
                        styleElement.setAttribute("name", k)
                        styleElement.textContent = v
                        styleNode.appendChild(styleElement)
                    }

                    break
                }
            }
        }
    }
}
