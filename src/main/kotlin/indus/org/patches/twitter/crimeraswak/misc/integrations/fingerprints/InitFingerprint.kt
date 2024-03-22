package indus.org.patches.twitter.crimeraswak.misc.integrations.fingerprints

import indus.org.patches.twitter.crimeraswak.integrations.BaseIntegrationsPatch

internal object InitFingerprint : BaseIntegrationsPatch.IntegrationsFingerprint(
    strings = listOf("builderClass"),
    customFingerprint = { methodDef, _ ->
       methodDef.name == "onCreate"
    }
)