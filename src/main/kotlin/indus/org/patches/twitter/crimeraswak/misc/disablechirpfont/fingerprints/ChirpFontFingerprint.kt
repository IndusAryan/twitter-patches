package indus.org.patches.twitter.crimeraswak.misc.disablechirpfont.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object ChirpFontFingerprint: MethodFingerprint(
    strings = listOf("af_ui_chirp_enabled")
)