package GameLogic

class Queen extends Piece {

  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(isPathClear(moveX, moveY)) {
      (moveX == _posX && moveY != _posY) || (moveX != _posX && moveY == _posY) && !board.hasFriend(moveX, moveY, team) ||
        moveX != _posX && moveY != _posY && math.abs(moveX - _posX) == math.abs(moveY - _posY) && !board.hasFriend(moveX, moveY, team)
    } else false
  }
}