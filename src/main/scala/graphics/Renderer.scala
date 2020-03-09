package graphics
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import org.scalajs.dom
import org.scalajs.dom.{Document, EventTarget, html}
import org.scalajs.dom.raw.{HTMLImageElement, MouseEvent}

import scala.scalajs.js
import scala.util.Random

object Renderer {
  class Image(src: String) {
    private var ready: Boolean = false

    val element = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    element.onload = (e: dom.Event) => ready = true
    element.src = src

    def isReady: Boolean = ready
  }

  @JSExportTopLevel("main")
  def main(canvas: html.Canvas): Unit = {
    val h = canvas.height
    val w = canvas.width

    val verticalBlockSize = h/8.0;
    val horizontalBlockSize = w/8.0;

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    var selectedPieceX = -1;
    var selectedPieceY = -1;

    canvas.onmousemove = { (e: dom.MouseEvent) =>
      //println("X: " + e.pageX)
      //println("Y: " + e.pageY)
    }
    canvas.onmousedown = { (e: dom.MouseEvent) =>
      val sx = math.floor(e.pageX / horizontalBlockSize).toInt
      val sy = math.floor(e.pageY / verticalBlockSize).toInt
      if(sx >= 0 && sx <= 8 && sy >= 0 && sy <= 8) {
        selectedPieceX = sx
        selectedPieceY = sy
      }
      else {
        selectedPieceX = -1;
        selectedPieceY = -1;
      }
      println("Selected piece X:" + selectedPieceX + " Y:" + selectedPieceY)
    }

    def clear() = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, h, w)
    }

    val bgImage = new Image("./src/resources/images/ChessPiecesArray.png")

    def drawBoard() = {
      for(i <- 0 until 8) {
        for(j <- 0 until 8) {
          if((i+j)%2==1) ctx.fillStyle = s"rgb(0, 0, 0)"
          else ctx.fillStyle = s"rgb(255, 255, 255)"
          ctx.fillRect(i*horizontalBlockSize, j*verticalBlockSize, verticalBlockSize, horizontalBlockSize)
        }
      }
    }

    def render() {
      if (bgImage.isReady) {
        ctx.drawImage(bgImage.element, 0, 0, 60, 60,
          0, 0, 60 ,60)
      }
    }

    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      //update(delta / 1000)
      drawBoard()
      render()

      prev = now
    }

    dom.window.setInterval(gameLoop, 50)
  }
}