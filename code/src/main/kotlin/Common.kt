import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

val objectMapper = ObjectMapper().registerKotlinModule()

val csvMapper = CsvMapper().apply {
    registerKotlinModule()
}
