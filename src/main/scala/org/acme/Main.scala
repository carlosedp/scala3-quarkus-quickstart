package org.acme

import io.quarkus.runtime.annotations.QuarkusMain
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.logging.Log

@QuarkusMain
class Main
object Main:
    def main(args: Array[String]): Unit =
        Quarkus.run(classOf[MainApp], args*)

class MainApp extends QuarkusApplication:
    def run(args: String*): Int =
        Log.debug("Running MainApp... processing startup logic")
        Log.debug("Startup args: " + args.mkString(", "))
        Quarkus.waitForExit()
        Log.debug("Shutting down MainApp... processing shutdown logic")
        0
