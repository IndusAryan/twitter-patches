package indus.org.patches.twitter.crimeraswak.ads.trends.fingerprints

import app.revanced.patcher.fingerprint.MethodFingerprint

object HidePromotedTrendFingerprint : MethodFingerprint(
    returnType = "Ljava/lang/Object;",
    customFingerprint = {it,_->
        it.definingClass == "Lcom/twitter/model/json/timeline/urt/JsonTimelineTrend;"
    }
)