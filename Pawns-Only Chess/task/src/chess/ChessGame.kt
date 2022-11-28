package chess

// initialize chess board:
val chessBoard = List(8) {
    MutableList(8) {
        when (it) {
            1 -> 'W'; 6 -> 'B'; else -> ' '
        }
    }
}

fun main() {
    println("Pawns-Only Chess")
    // initialize game:
    println("First Player's name:")
    val whitePlayer = readln()
    println("Second Player's name:")
    val blackPlayer = readln()

    printChessBoard()
    playGame(whitePlayer, blackPlayer)
}

fun playGame(whitePlayer: String, blackPlayer: String) {
    val moveRegex = Regex("[a-h][1-8][a-h][1-8]")
    var player = whitePlayer
    var enPassantFile = 0
    var enPassantRank = 0
    var capturedWhites = 0
    var capturedBlacks = 0

    while (true) {
        println("$player's turn:")
        val move = readln()
        if (move == "exit") break
        if (move.matches(moveRegex)) { // check for valid input
            // decode valid input to move coordinates in 2D-List representation
            val fileStart = move[0].code - 97  // convert from ascii to int: 'a'..'h' -> 0..7
            val rankStart = move[1].digitToInt() - 1  // convert from ascii to int: '1'..'8' -> 0..7
            val fileEnd = move[2].code - 97  // convert from ascii to int: 'a'..'h' -> 0..7
            val rankEnd = move[3].digitToInt() - 1  // convert from ascii to int: '1'..'8' -> 0..7
            val colorCurrent = if (player == whitePlayer) "white" else "black"
            val colorCurrSym: Char = if (player == whitePlayer) 'W' else 'B'
            val direction = if (player == whitePlayer) 1 else -1 // 1: move up, 2: move down

            // no own pawn at starting position:
            if (chessBoard[fileStart][rankStart] != colorCurrSym) {
                println("No $colorCurrent pawn at ${move.substring(0..1)}")
                continue
            }

            // identify move type
            val isForwardMove = fileEnd == fileStart && chessBoard[fileEnd][rankEnd] == ' '
            val isForwardOneRank = isForwardMove && rankEnd == rankStart + direction
            val isForwardTwoRanks = isForwardMove && (rankStart == 1 || rankStart == 6)
                    && rankEnd == rankStart + direction * 2 && chessBoard[fileStart][rankStart + direction] == ' '
            val isCaptureMove = (fileEnd == fileStart + 1 || fileEnd == fileStart - 1)
            val isCaptureRegular = isCaptureMove && rankEnd == rankStart + direction
                    && chessBoard[fileEnd][rankEnd] == if (colorCurrSym == 'W') 'B' else 'W'
            val isCaptureEnPassant =
                isCaptureMove && fileEnd == enPassantFile && rankEnd == enPassantRank && enPassantFile != 0 && enPassantRank != 0
            val isValidMove = isForwardOneRank || isForwardTwoRanks || isCaptureRegular || isCaptureEnPassant

            // perform move and print updated chessboard
            if (isValidMove) {
                if (isCaptureEnPassant) chessBoard[enPassantFile][enPassantRank - direction] = ' '
                if (isForwardTwoRanks) {
                    enPassantFile = fileEnd; enPassantRank = rankEnd - direction
                } else {
                    enPassantFile = 0; enPassantRank = 0
                }
                if (player == blackPlayer && (isCaptureRegular || isCaptureEnPassant)) {
                    capturedWhites++
                } else if (player == whitePlayer && (isCaptureRegular || isCaptureEnPassant)) {
                    capturedBlacks++
                }
                chessBoard[fileStart][rankStart] = ' '
                chessBoard[fileEnd][rankEnd] = colorCurrSym
                printChessBoard()
                if ((player == whitePlayer && rankEnd == 7) || capturedBlacks == 8) {
                    println("White Wins!")
                    break
                } else if ((player == blackPlayer && rankEnd == 0) || capturedWhites == 8) {
                    println("Black Wins!")
                    break
                } else {
                    player = if (player == whitePlayer) blackPlayer else whitePlayer
                }
            } else {
                println("Invalid Input")
            }

        } else println("Invalid Input")
    }
    println("Bye!")
}

fun printChessBoard() {
    val separatorLine = "  +" + "---+".repeat(8)
    for (rank in 7 downTo 0) {
        println(separatorLine)
        print("${rank + 1} |")
        for (file in 0..7) print(" ${chessBoard[file][rank]} |")
        println()
    }
    println(separatorLine)
    print(" ")
    println(('a'..'h').joinToString("   ", "   "))
}