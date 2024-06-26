package indus.org.patches.twitter.viewcount.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object ViewCountsEnabledFingerprint : MethodFingerprint(
    returnType = "Z",
    strings = listOf("view_counts_public_visibility_enabled")
)