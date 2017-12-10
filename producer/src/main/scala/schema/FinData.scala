package schema

/**
  * Case Class for schema serialization
  *
  * @param ticker
  * @param date
  * @param open
  * @param high
  * @param low
  * @param volume
  * @param exDividend
  * @param splitRatio
  * @param adjOpen
  * @param adjHigh
  * @param adjLow
  * @param adjClose
  * @param adjVolume
  */
case class FinData(ticker: String,
                   date: String,
                   open: Double,
                   high: Double,
                   low: Double,
                   volume: Long,
                   exDividend: Double,
                   splitRatio: Double,
                   adjOpen: Double,
                   adjHigh: Double,
                   adjLow: Double,
                   adjClose: Double,
                   adjVolume: Long)
