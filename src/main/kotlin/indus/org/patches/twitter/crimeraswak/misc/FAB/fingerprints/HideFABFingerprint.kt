package indus.org.patches.twitter.crimeraswak.misc.FAB.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object HideFABFingerprint: MethodFingerprint(
    strings = listOf(
        "android_compose_fab_menu_enabled"
    )
)