package Quoridor.model.boardComponent.boardBaseImpl

sealed trait Player

case class Player1() extends Player

case class Player2() extends Player

case class Player3() extends Player

case class Player4() extends Player
