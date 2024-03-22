package indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object SettingsFingerprint: MethodFingerprint(
    returnType = "V",
    strings = listOf(
        "pref_proxy"
    ),
    customFingerprint = { it, _ ->
        it.name == "<clinit>"
    }
)
