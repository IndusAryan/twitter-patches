package indus.org.patches.twitter.crimeraswak.misc.viewcount.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object RemoveViewCountPatchFingerprint: MethodFingerprint (
    returnType = "Z",
    strings = listOf(
        "view_counts_public_visibility_enabled",
    )
)