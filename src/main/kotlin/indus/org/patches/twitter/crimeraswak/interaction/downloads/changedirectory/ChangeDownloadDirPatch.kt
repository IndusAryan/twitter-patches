package indus.org.patches.twitter.crimeraswak.interaction.downloads.changedirectory

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import indus.org.patches.twitter.crimeraswak.interaction.downloads.changedirectory.fingerprints.SetDownloadDestinationFingerprint
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch
import indus.org.patches.twitter.crimeraswak.misc.settings.SettingsPatch.PREF_DESCRIPTOR
import indus.org.patches.twitter.crimeraswak.misc.settings.fingerprints.SettingsStatusLoadFingerprint

@Patch(
    name = "Custom download folder",
    description = "Change the download directory for video downloads",
    dependencies = [SettingsPatch::class],
    compatiblePackages = [CompatiblePackage("com.twitter.android")]
)
@Suppress("unused")
object ChangeDownloadDirPatch: BytecodePatch(
    setOf(SetDownloadDestinationFingerprint, SettingsStatusLoadFingerprint)
) {
    private const val GETFOLDER_DESCRIPTOR =
        "invoke-static {p1}, $PREF_DESCRIPTOR;->getVideoFolder(Ljava/lang/String;)Ljava/lang/String;"
    private const val PUBLICFOLDER_DESCRIPTOR =
        "invoke-static {}, $PREF_DESCRIPTOR;->getPublicFolder()Ljava/lang/String;"

    override fun execute(context: BytecodeContext) {
        val result = SetDownloadDestinationFingerprint.result
            ?: throw PatchException("Could not find fingerprint")

        val method = result.mutableMethod

        val insertAt = method.getInstructions()
            .first { it.opcode == Opcode.INVOKE_VIRTUAL }.location.index

        val publicFolderRegister = method.getInstruction<OneRegisterInstruction>(insertAt-1).registerA
        method.addInstructions(insertAt, """
            $PUBLICFOLDER_DESCRIPTOR
            move-result-object v$publicFolderRegister
            
            $GETFOLDER_DESCRIPTOR
            move-result-object p1
        """.trimIndent())

        SettingsStatusLoadFingerprint.result!!.mutableMethod.addInstruction(
            0,
            "${SettingsPatch.SSTS_DESCRIPTOR}->enableDownloadFolder()V"
        )
    }
}