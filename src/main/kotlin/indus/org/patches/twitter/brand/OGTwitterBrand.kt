package indus.org.patches.twitter.brand

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "OG Twitter brand",
    description = "Replaces the app name from nonsense X to Twitter and words like post to tweet, repost to retweet, etc.",
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object OGTwitterBrand : ResourcePatch() {

    override fun execute(context: ResourceContext) {

        val manifestFile = context["AndroidManifest.xml"]
        if (!manifestFile.isFile) throw PatchException("Manifest file can not be found.")

        context.xmlEditor["AndroidManifest.xml"].use { editor ->
            val document = editor.file
            val applicationNode = document.getElementsByTagName("application").item(0)
            val appNameAttribute = applicationNode.attributes.getNamedItem("android:label")
            appNameAttribute.textContent = "Twitter"
        }

        updateStrings(context)
    }

    private fun updateStrings(context: ResourceContext) {
        val stringsFile = context["res/values/strings.xml"]
        if (!stringsFile.isFile) throw PatchException("strings.xml file not found.")

        context.xmlEditor["res/values/strings.xml"].use { editor ->
            val document = editor.file

            val replacementMap = mapOf(
                "button_action_add_tweet" to "Add a tweet",
                "button_action_options_tweet" to "Tweet options",
                "button_action_retweet" to "Retweet",
                "button_new_tweet" to "New tweet",
                "button_status_retweeted" to "Retweet (retweeted)",
                "chrome_action_post" to "Tweet",
                "ps__share_post_tweet" to "Tweet",
                "rux_landing_page_title" to "Tweet",
                "tweet_fab_item" to "Tweet",
                "tweet_title" to "Tweet",
                "ps__accessibility_retweet_broadcast_button" to "Retweet",
                "shortcut_retweet_tweet" to "Retweet",
                "social_you_retweeted" to "You retweeted",
                "composer_hint_self_thread" to "Add another tweet",
                "confirm_delete_shared_tweet_description" to "Are you sure you want to remove this tweet?",
                "confirm_delete_shared_tweet_title" to "Remove tweet",
                "conversation_control_reply_restricted_dialog_title" to "Tweet not sent",
                "conversation_control_reply_restricted_error" to "Tweet not sent. Replies are restricted.",
                "conversations_alternative_reply_hint" to "Tweet your reply",
                "conversations_other_tweets" to "Other tweets",
                "curation_i_dont_like_this_tweet" to "I don’t like this tweet",
                "date_posted" to "Date tweeted",
                "post_tweet" to "Tweet",
                "deleted_tweet_title" to "This tweet has been deleted.",
                "dm_sensitive_tweet_interstitial_header" to "This tweet may contain sensitive material",
                "dm_untrusted_tweet_interstitial_header" to "This tweet is hidden",
                "empty_profile_tweets_tab_title" to "You haven’t tweeted yet",
                "feedback_action_report_tweet" to "Report tweet",
                "feedback_tweet_unavailable" to "This tweet is unavailable.",
                "filter_item_tweets" to "Tweets",
                "filter_item_tweets_and_replies" to "Tweets & replies",
                "profile_tab_title_timeline" to "Tweets",
                "users_turn_off_retweets" to "Turn off retweets",
                "tweets_retweet" to "Retweet",
                "tweets_retweeted" to "%s retweeted",
                "tweets_undo_retweet_vertical" to "Undo retweet",
                "users_turn_on_retweets" to "Turn on retweets"
            )

            for ((key, value) in replacementMap) {
                val nodes = document.getElementsByTagName("string")
                for (i in 0 until nodes.length) {
                    val node = nodes.item(i)
                    if (node.attributes.getNamedItem("name")?.nodeValue == key) {
                        node.textContent = value
                        break
                    }
                }
            }
        }
    }
}