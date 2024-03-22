package indus.org.patches.twitter.crimeraswak.misc.integrations

import app.revanced.patcher.patch.annotation.Patch
import indus.org.patches.twitter.crimeraswak.integrations.BaseIntegrationsPatch
import indus.org.patches.twitter.crimeraswak.misc.integrations.fingerprints.InitFingerprint

@Patch(
    requiresIntegrations = true
)
object IntegrationsPatch: BaseIntegrationsPatch(
    setOf(InitFingerprint)
)