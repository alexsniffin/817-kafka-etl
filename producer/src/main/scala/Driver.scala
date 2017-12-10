import java.util.InvalidPropertiesFormatException

/**
  * Driver class for starting the application
  */
object Driver {

  val usage = """
    Usage: producer [options]

      --topic <value>
          the topic to produce to
      --broker <value>
          the address on where the broker cluster is located
      --delay <value>
          the delay on which to simulate for a single day's process
      --help
          this dialog
  """

  def main(args: Array[String]): Unit = {

    val arglist = args.toList

    // OptionMap to store command-line args
    type OptionMap = Map[Symbol, Any]

    // If help, display usage and exit
    arglist.foreach(item => {
      if (item.equals("--help")) {
        println(usage)
        sys.exit(1)
      }
    })

    /**
      * Recursive function to read in command-line parameters into a Map
      * @param map Map of options
      * @param list Tail
      * @return OptionMap object
      */
    def nextOption(map : OptionMap, list: List[String]): OptionMap = {
      try {
        list match {
          case Nil => map
          case "--topic" :: value :: tail =>
            nextOption(map ++ Map('topic -> value), tail)
          case "--broker" :: value :: tail =>
            nextOption(map ++ Map('broker -> value), tail)
          case "--delay" :: value :: tail =>
            nextOption(map ++ Map('delay -> value.toLong), tail)
          case option :: tail => {
            println("Ignoring unknown option specified: " + option)
            map
          }
        }
      } catch {
        // Exit if invalid parameter
        case e: InvalidPropertiesFormatException => {
          println(e.getMessage)
          println(usage)
          sys.exit(1)
        }
      }
    }

    val options = nextOption(Map(), arglist)

    println("Starting FinProducer")
    val producer = FinProducer(options('topic), options('broker), options('delay))

    println("Starting Datafeed")
    producer.simulateDataFeed()
  }

}
