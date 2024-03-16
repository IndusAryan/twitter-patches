package indus.org.patches.twitter.adsandpromotions.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object GAdsFingerprint : MethodFingerprint(
    returnType = "V",
    strings = listOf(
        "ssp_ads_google_dsp_client_context_enabled"
    )
)
