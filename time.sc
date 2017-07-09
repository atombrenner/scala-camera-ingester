import java.time._
import java.time.chrono.IsoChronology
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder, ResolverStyle}

val now = Instant.now()
val dateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault())

dateTime.toLocalDate
dateTime.getYear
dateTime.getMonthValue

val slot: Int = 1793
f"$slot%03d"

//val ISO_INSTANT = new DateTimeFormatterBuilder().parseCaseInsensitive.appendInstant.toFormatter(ResolverStyle.STRICT, null)

//val ISO_DATE = new DateTimeFormatterBuilder().parseCaseInsensitive.appendInstant.optionalStart.appendOffsetId.toFormatter(ResolverStyle.STRICT, IsoChronology.INSTANCE)