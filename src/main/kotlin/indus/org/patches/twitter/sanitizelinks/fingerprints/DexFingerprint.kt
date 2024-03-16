package indus.org.patches.twitter.sanitizelinks.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object DexFingerprint {

    object AddSessionTokenFingerprint: MethodFingerprint(
        strings = listOf(
            "<this>",
            "shareParam",
            "sessionToken"
        )
    )

    internal object JsonObjectMapperFingerprint : MethodFingerprint(
        // Lcom/twitter/model/json/core/JsonUrlEntity$$JsonObjectMapper;
        customFingerprint = { methodDef, _ -> methodDef.name.contains("parse") && methodDef.definingClass == "Lcom/twitter/model/json/core/JsonUrlEntity\$\$JsonObjectMapper;" }
    )
}