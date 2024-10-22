package helper

import java.util.concurrent.CompletionStage

import scala.jdk.FutureConverters.*

import zio.*

// Helper methods to run ZIO effects and return a CompletionStage
object ZIOHelper:
  /**
   * Run a ZIO effect and return a Java CompletionStage
   *
   * @param zio
   *   ZIO[Any, Throwable, A]
   * @tparam A
   *   The type of the ZIO effect
   * @return
   *   CompletionStage[A]
   */
  def runZio[A](zio: ZIO[Any, Throwable, A]): CompletionStage[A] =
    val runtime = Runtime.default
    val run = Unsafe.unsafe { implicit unsafe =>
      runtime.unsafe.runToFuture(zio)
    }
    run.asJava

  extension [A](zio: ZIO[Any, Throwable, A])
    /**
     * Convert a ZIO effect to a Java CompletionStage
     *
     * @return
     *   CompletionStage[A]
     */
    def fromZio: CompletionStage[A] = runZio(zio)
end ZIOHelper
