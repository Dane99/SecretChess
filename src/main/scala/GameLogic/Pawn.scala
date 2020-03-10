package GameLogic

class Pawn extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(vectIsClear(moveX, moveY)) {
      if (moveX == _posX && !board.hasPiece(moveX, moveY)) team match {
        case Team.Black => moveY - _posY == 2 || moveY - _posY == 1
        case Team.White => _posY - moveY == 2 || _posY - moveY == 1
        case _ => throw new Exception("Team must be Black or White.")
      }
      else if (math.abs(moveX - _posX) == 1
        && (board.hasEnemy(moveX, moveY, team)
        || (if (team == Team.White) board.hasEnemy(moveX, math.min(7, moveY + 1), team)
      else board.hasEnemy(moveX, math.max(0, moveY -  1), team) && !board.hasFriend(moveX, moveY, team)))) team match {
        case Team.Black => moveY - _posY == 1
        case Team.White => _posY - moveY == 1
        case _ => throw new Exception("Team must be Black or White.")
      } else {
        false
      }
    } else false
  }
}