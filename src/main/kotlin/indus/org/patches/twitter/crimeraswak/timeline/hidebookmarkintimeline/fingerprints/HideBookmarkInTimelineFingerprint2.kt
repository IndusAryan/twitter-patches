package indus.org.patches.twitter.crimeraswak.timeline.hidebookmarkintimeline.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object HideBookmarkInTimelineFingerprint2 :MethodFingerprint(
    strings = listOf("bookmarks_in_timelines_enabled"),
    returnType = "Ljava/util/List;"

)