val name = "hullebulle.jpg"
val pattern = """jpg$""".r
//val regex = r".*"

val result = pattern.findFirstIn(name)
result.isDefined
result.isEmpty

pattern.pattern.matcher(name).matches()

name match { case pattern(_*) => true ; case _ => false}
pattern.regex
name.matches(pattern.regex)

