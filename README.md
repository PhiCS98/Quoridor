
# Quoridor
[![Scala CI](https://github.com/PhiCS98/Quoridor/actions/workflows/scala.yml/badge.svg)](https://github.com/PhiCS98/Quoridor/actions/workflows/scala.yml)
[![codecov](https://codecov.io/gh/PhiCS98/Quoridor/branch/master/graph/badge.svg?token=UC46PW3DMN)](https://codecov.io/gh/PhiCS98/Quoridor)
![RepoSize](https://img.shields.io/github/repo-size/PhiCS98/Quoridor)
![Lines of Code](https://tokei.rs/b1/github/PhiCS98/Quoridor)

Quoridor is an implementation of the popular board game of the same name, written in Scala 3 and using Scalafx for the GUI. The architecture of the program follows the Model-View-Controller pattern.

## Installation

1. Clone the repository: `git clone https://github.com/PhiCS98/Quoridor.git`
2. Navigate to the project directory: `cd Quoridor`
3. Compile the code: `sbt compile`
4. Run the game: `sbt run`

## How to Play

The goal of the game is to reach the opposite side of the board before your opponent, while also blocking their progress with walls. Each player starts on opposite sides of the board and takes turns moving their pawn and placing walls.

## Game rules
-  Each player starts with 10 walls
-  A pawn can move horizontally or vertically to an adjacent cell that is not blocked by a wall.
-  A wall can be placed on any unoccupied space on the board, as long as it does not block the pawns from reaching their respective goals.
-  A player cannot move their pawn through a wall, or move their pawn to a space that is blocked by a wall.
-  A player wins if they reach the opposite side of the board before their opponent

## Game Screenshots

![screenshot1](https://i.imgur.com/screenshot1.png)
![screenshot2](https://i.imgur.com/screenshot2.png)

## Contributing

1. Fork the repository
2. Create a new branch for your changes (`git checkout -b my-changes`)
3. Commit your changes (`git commit -am 'Added some feature'`)
4. Push to the branch (`git push origin my-changes`)
5. Create a new pull request

## Acknowledgments

* [ScalaFX](https://www.scalafx.org/) - The GUI library used in this project
* [Circe](https://circe.github.io/circe/) - The json library used in this project
* [sbt](https://www.scala-sbt.org) - The build tool used in this project
* [sbt-assembly](https://github.com/sbt/sbt-assembly) - The sbt plugin used to build fat jars in this project
* [scoverage](https://github.com/scoverage/sbt-scoverage) - The code coverage tool used in this project
* [ScalaTest](https://www.scalatest.org) - The testing library used in this project
* [Docker](https://www.docker.com) - The container tool used in this project
* [Scala-xml](https://github.com/scala/scala-xml) - The xml library used in this project 
* [Quoridor](https://en.wikipedia.org/wiki/Quoridor) - The board game that inspired this project
