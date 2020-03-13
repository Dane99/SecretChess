package GameLogic

class Queen extends Piece {

  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if((moveX != posX || moveY != posY) &&
      (moveX == posX || moveY == posY || math.abs(moveX - posX) == math.abs(moveY - posY))) {
      isPathClear(moveX, moveY, true)
    }
    else {
      false
    }
  }
}