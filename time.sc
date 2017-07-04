import java.time._

val now = Instant.now()
val dateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault())

dateTime.toLocalDate
dateTime.getYear
dateTime.getMonthValue