import java.time._
import java.time.temporal.ChronoField

val now = Instant.now()
val date = LocalDateTime.ofInstant(now, ZoneId.systemDefault())

date.toLocalDate