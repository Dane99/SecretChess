package graphics
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.util.Random

object Board {
  @JSExportTopLevel("main")
  def main(canvas: html.Canvas): Unit = {
    val h = canvas.height
    val w = canvas.width

    val verticalBlockSize = h/8.0;
    val horizontalBlockSize = w/8.0;

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, 255, 255)
    }

    def run = {
      for(i <- 0 until 8) {
        for(j <- 0 until 8) {
          if((i+j)%2==1) ctx.fillStyle = s"rgb(0, 0, 0)"
          else ctx.fillStyle = s"rgb(255, 255, 255)"
          ctx.fillRect(i*horizontalBlockSize, j*verticalBlockSize, verticalBlockSize, horizontalBlockSize)
        }
      }

    }

    dom.window.setInterval(() => run, 50)
  }
}