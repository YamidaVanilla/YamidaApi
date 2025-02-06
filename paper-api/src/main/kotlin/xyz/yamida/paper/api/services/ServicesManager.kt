package xyz.megadev.mafia.server.components

import xyz.yamida.paper.api.services.Service

object ServicesManager {
    val services = HashSet<Service>()

    fun registerService(service: Service) {
        services.add(service)
    }

    fun unregisterService(service: Service) {
        services.remove(service)
    }

    fun getService(name: String): Service? {
        return services.find { it::class.simpleName == name }
    }

    inline fun <reified T : Service> getService(): T? {
        return services.find { it is T } as? T
    }

    inline fun <reified T : Service> inject(): Lazy<T> {
        return lazy {
            getService<T>() ?: throw IllegalStateException("Сервис ${T::class.simpleName} не найден!")
        }
    }
}
