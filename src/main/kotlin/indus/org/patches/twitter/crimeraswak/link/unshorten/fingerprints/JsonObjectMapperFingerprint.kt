package indus.org.patches.twitter.crimeraswak.link.unshorten.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags


internal object JsonObjectMapperFingerprint : MethodFingerprint(
    // Lcom/twitter/model/json/core/JsonUrlEntity$$JsonObjectMapper;
    customFingerprint = { methodDef, _ -> methodDef.name.contains("parse") && methodDef.definingClass == "Lcom/twitter/model/json/core/JsonUrlEntity\$\$JsonObjectMapper;" }
)
