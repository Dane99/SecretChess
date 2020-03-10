package GameLogic

class Bishop extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(vectIsClear(moveX, moveY)) {
      moveX != _posX && moveY != _posY && math.abs(moveX - _posX) == math.abs(moveY - _posY) && !board.hasFriend(moveX, moveY, team)
    } else false
  }
}