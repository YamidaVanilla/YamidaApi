package xyz.yamida.paper.api.ext

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import kotlinx.serialization.encodeToString
import kotlinx.serialization.serializer
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

val yaml: Yaml
    get() = Yaml(
        configuration = YamlConfiguration(
            strictMode = false
        )
    )

inline fun <reified T> Yaml.loadConfigFromFile(file: File): T {
    val text = file.readText(Charsets.UTF_8)
    return decodeFromString(serializer(), text)
}

fun JavaPlugin.loadFile(name: String, folder: File = dataFolder): File {
    val file = File(folder, name)
    if (!file.exists()) {
        file.parentFile?.mkdirs()
        file.createNewFile()
    }
    return file
}

inline fun <reified T> JavaPlugin.loadConfig(
    configName: String,
    requireFilled: Boolean = false,
    folder: File = dataFolder
): T {
    // Определяем файл (если расширение не указано, добавляем ".yml")
    val file = if (!configName.endsWith(".yml"))
        loadFile("$configName.yml", folder)
    else
        loadFile(configName, folder)

    if (file.length() == 0L) {
        check(!requireFilled) {
            "Config file `$configName` is empty while it should be filled."
        }
        val config = try {
            T::class.java.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            throw IllegalArgumentException(
                "Cannot create a default instance of ${T::class.simpleName}. " +
                        "Make sure the class has a no-args constructor or enable `requireFilled`.",
                e
            )
        }
        saveConfig(configName, config, folder)
        return config
    }

    return yaml.loadConfigFromFile<T>(file)
}

inline fun <reified T> JavaPlugin.saveConfig(
    configName: String,
    config: T,
    folder: File = dataFolder
) {
    val file = if (!configName.endsWith(".yml"))
        loadFile("$configName.yml", folder)
    else
        loadFile(configName, folder)
    file.writeText(yaml.encodeToString(config))
}


fun JavaPlugin.loadDir(name: String): File {
    val dir = File(dataFolder, name)
    if (!dir.exists()) {
        dir.mkdirs()
    }
    return dir
}