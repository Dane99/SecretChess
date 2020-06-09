package GameLogic

class Bishop extends Piece {
  def getValue(): Int = 3
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if((moveX != posX || moveY != posY) && math.abs(moveX - posX) == math.abs(moveY - posY)) {
      isPathClear(moveX, moveY, true)
    } else {
      false
    }
  }
}