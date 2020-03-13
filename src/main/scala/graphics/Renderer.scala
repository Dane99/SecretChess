package graphics
import org.scalajs.dom
import org.scalajs.dom.html
import org.scalajs.dom.raw.HTMLImageElement

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import GameLogic._

object Renderer {
  class Image(src: String) {
    private var ready: Boolean = false

    val element: HTMLImageElement = dom.document.createElement("img").asInstanceOf[HTMLImageElement]
    element.onload = (e: dom.Event) => ready = true
    element.src = src

    def isReady: Boolean = ready
  }
  val board = new Board

  @JSExportTopLevel("main")
  def main(canvas: html.Canvas): Unit = {
    val h = canvas.height
    val w = canvas.width

    val verticalBlockSize = h/8.0
    val horizontalBlockSize = w/8.0

    val ctx = canvas.getContext("2d")
      .asInstanceOf[dom.CanvasRenderingContext2D]

    var selectedPieceX = -1
    var selectedPieceY = -1

    //canvas.onmousemove = { (e: dom.MouseEvent) =>
      //println("X: " + e.pageX)
      //println("Y: " + e.pageY)
    //}
    canvas.onmousedown = { e: dom.MouseEvent =>
      val nx = math.floor(e.pageX / horizontalBlockSize).toInt
      val ny = math.floor(e.pageY / verticalBlockSize).toInt
      // Ensure the move in within the board
      if(nx >= 0 && nx <= 8 && ny >= 0 && ny <= 8) {
        // Check that there is a selected piece
        if(selectedPieceX != -1 && selectedPieceY != -1){
          // Select piece
          val piece = board.tiles(selectedPieceX)(selectedPieceY)
          // Empty tiles are null
          if(piece.occupant != null) {
            if (piece.occupant.makeMove(nx, ny)) {
              println("Move was executed!")
            }
          }
        }

        selectedPieceX = nx
        selectedPieceY = ny
      }
      else {
        selectedPieceX = -1
        selectedPieceY = -1
      }


      println("Selected piece X:" + selectedPieceX + " Y:" + selectedPieceY)
    }

    def clear(): Unit = {
      ctx.fillStyle = "black"
      ctx.fillRect(0, 0, h, w)
    }

    val bgImage = new Image("./src/resources/images/ChessPiecesArray.png")

    def drawQueen(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 0, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }

    def drawKing(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 60, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }

    def drawRook(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 120, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }

    def drawKnight(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 180, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }

    def drawBishop(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 240, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }

    def drawPawn(x: Double, y: Double, white: Boolean): Unit ={
      ctx.drawImage(bgImage.element, 300, if(white) 60 else 0, 60, 60,
        x, y, 60 ,60)
    }


    def drawBoard(): Unit = {
      for(i <- 0 until 8) {
        for(j <- 0 until 8) {
          if((i+j)%2==1) ctx.fillStyle = s"rgb(50, 100, 50)"
          else ctx.fillStyle = s"rgb(255, 255, 255)"
          ctx.fillRect(i*horizontalBlockSize, j*verticalBlockSize, verticalBlockSize, horizontalBlockSize)
        }
      }
    }

    def updateBoardPieces() {
      val random = new scala.util.Random
      if (bgImage.isReady) {
        for(x <- 0 until 8) {
          for (y <- 0 until 8) {
//            val rnd = new scala.util.Random
//            val p = rnd.nextInt(6)
//            val w = rnd.nextInt(2) == 1
            val piece = board.tiles(x)(y)
            if(piece.occupant != null) {
              val w = piece.occupant.team == Team.White
              piece.occupant match {
                case p: Queen => drawQueen(x * horizontalBlockSize, y * verticalBlockSize, w)
                case p: King => drawKing(x * horizontalBlockSize, y * verticalBlockSize, w)
                case p: Rook => drawRook(x * horizontalBlockSize, y * verticalBlockSize, w)
                case p: Knight => drawKnight(x * horizontalBlockSize, y * verticalBlockSize, w)
                case p: Bishop => drawBishop(x * horizontalBlockSize, y * verticalBlockSize, w)
                case p: Pawn => drawPawn(x * horizontalBlockSize, y * verticalBlockSize, w)
                case _ => {}
              }
            }
          }
        }
      }
    }

    var prev = js.Date.now()
    // The main game loop
    val gameLoop = () => {
      val now = js.Date.now()
      val delta = now - prev

      //update(delta /
      clear()
      drawBoard()
      updateBoardPieces()

      prev = now
    }

    dom.window.setInterval(gameLoop, 50)
  }
}