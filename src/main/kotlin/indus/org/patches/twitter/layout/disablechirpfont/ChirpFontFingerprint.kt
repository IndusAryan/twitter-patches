package indus.org.patches.twitter.layout.disablechirpfont

import app.revanced.patcher.fingerprint.MethodFingerprint

object ChirpFontFingerprint: MethodFingerprint(
    strings = listOf("af_ui_chirp_enabled")
)