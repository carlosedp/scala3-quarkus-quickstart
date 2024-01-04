package org.acme

import io.quarkus.runtime.annotations.QuarkusMain
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.logging.Log

@QuarkusMain
class Main

class MyApp extends QuarkusApplication:
    def run(args: String*): Int =
        Log.debug("Running MyApp... doing startup logic here")
        Log.debug("Startup args: " + args.mkString(", "))
        Quarkus.waitForExit()
        Log.debug("Shutting down MyApp... doing shutdown logic here")
        0

object Main:
    def main(args: Array[String]): Unit =
        Quarkus.run(classOf[MyApp], args*)
