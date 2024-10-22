package org.acme

import io.quarkus.logging.Log
import io.quarkus.runtime.annotations.QuarkusMain
import io.quarkus.runtime.{Quarkus, QuarkusApplication}

@QuarkusMain
class Main
object Main:
  def main(args: Array[String]): Unit =
    Quarkus.run(classOf[MainApp], args*)

class MainApp extends QuarkusApplication:
  def run(args: String*): Int =
    Log.debug("Running MainApp... processing startup logic")
    Log.debug(s"Startup args: ${if args.isEmpty then "none" else args.mkString(", ")}")
    Quarkus.waitForExit()
    Log.debug("Shutting down MainApp... processing shutdown logic")
    0
