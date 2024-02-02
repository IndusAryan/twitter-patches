package indus.org.utils


import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import java.io.File
import java.nio.file.Files
import java.io.IOException
import java.nio.file.StandardCopyOption

fun ResourceContext.copyRawIcons(vararg iconFiles: File) {
    val targetDirectory = this["res"]
    if (!targetDirectory.isDirectory) throw PatchException("The res folder can not be found.")

    val rawDirectory = targetDirectory.resolve("raw")
    if (!rawDirectory.isDirectory) Files.createDirectories(rawDirectory.toPath())

    for (iconFile in iconFiles) {
        val destinationFile = rawDirectory.resolve(iconFile.name)

        try {
            Files.copy(iconFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            throw PatchException("Failed to copy the icon file: ${iconFile.name}", e)
        }
    }
}

