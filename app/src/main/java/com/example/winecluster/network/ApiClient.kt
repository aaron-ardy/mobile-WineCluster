import com.example.winecluster.model.WineAttributes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class PredictionResponse(val cluster: Int, val confidence: Double? = null)

suspend fun predictCluster(features: List<Double>): Int {
    val jsonFormatter = Json { prettyPrint = true }
    val payload = WineAttributes(features)

    // 🔍 Log the JSON payload
    val jsonString = jsonFormatter.encodeToString(payload)
    println("Sending JSON to Flask:\n$jsonString")

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(jsonFormatter)
        }
    }

    val response: PredictionResponse = client.post("http://10.0.2.2:5000/predict") {
        contentType(ContentType.Application.Json)
        setBody(payload)
    }.body()

    client.close()
    return response.cluster
}
