package indus.org.patches.twitter.hook.json.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

internal object LoganSquareFingerprint : MethodFingerprint(
    customFingerprint = { methodDef, _ -> methodDef.definingClass.endsWith("LoganSquare;") }
)