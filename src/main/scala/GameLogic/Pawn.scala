package GameLogic

class Pawn extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(!board.hasPiece(moveX, moveY)) {
      // Empty space move
      val moveLimit = if(hasMoved) 1 else 2
      team match {
        case Team.White => (moveX == posX && (posY - moveY <= moveLimit)
                          && (posY - moveY > 0) && isPathClear(moveX, moveY, false))
        case Team.Black => (moveX == posX && (moveY - posY <= moveLimit)
                          && (moveY - posY > 0) && isPathClear(moveX, moveY, false))
      }
    }
    else if(board.hasEnemy(moveX, moveY, team)){
      // Attack move
      team match {
        case Team.White => (posY - moveY == 1 && math.abs(posX - moveX) == 1)
        case Team.Black => (moveY - posY == 1 && math.abs(posX - moveX) == 1)
      }
    }
    else {
      // Move to tile with friendly piece
      false
    }
  }
}