import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.util.*

class CargoStatistics(
    private val file: File
) {
    private val statistics = HashMap<UUID, Int>()
    private val gson = Gson()

    init {
        if (!file.exists())
            save()

        val type = object : TypeToken<Map<UUID, Int>>() {}.type
        statistics.putAll(file.reader().use { gson.fromJson(it, type) })
    }

    fun get(uuid: UUID) = statistics[uuid]

    fun increment(uuid: UUID) {
        statistics.merge(uuid, 1) { n, _ -> n + 1 }
        save()
    }

    private fun save() {
        file.writer().use { gson.toJson(statistics, it) }
    }
}
