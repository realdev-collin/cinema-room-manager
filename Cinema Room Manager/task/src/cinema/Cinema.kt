package cinema

import kotlin.math.ceil

const val standardPrice = 10
const val cheapPrice = 8
var income = 0
var totalIncome = 0
var rowBound = 0
var seatBound = 0

class Cinema (private val rows: Int,private val seatsPerRow: Int) {

    private val bookedSeats: MutableList<Pair<Int, Int>> = mutableListOf()

    fun bookSeat() {

        var selectedRow = 0
        var selectedSeatInRow = 0
        println()

        while (true) {
            println("Enter a row number:")
            selectedRow = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            selectedSeatInRow = readLine()!!.toInt()

            // check if the selected seat is available
            if (bookedSeats.contains(Pair(selectedRow, selectedSeatInRow))) {
                println("That ticket has already been purchased!\n")
                continue
            } else if (selectedRow > rowBound || selectedSeatInRow > seatBound) {
                println("Wrong input!\n")
                continue
            }
            break
        }

        val price = if (seatsPerRow * rows <= 60 || selectedRow <= rows / 2)
            standardPrice else cheapPrice
        println("Ticket price: $$price\n")

        bookedSeats.add(Pair(selectedRow, selectedSeatInRow))
        income += price
    }

    fun showSeats() {
        println()
        println("Cinema:")
        print(" ")
        for (column in 1..seatsPerRow){
            print(" $column")
        }
        println()
        // generate a 2D array with S
        val seats = Array(rows) { CharArray(seatsPerRow) {'S'} }
        for (selectedSeat in bookedSeats) {
            seats[selectedSeat.first - 1][selectedSeat.second - 1] = 'B'
        }
        for (row in 0 until rows) {
            println("${row + 1} " + seats[row].joinToString(" "))
        }
        println()
    }

    // display stats
    fun showStats() {
        //val percentage = String.format("%.2f", (bookedSeats.size / (rows * seatsPerRow)))
        val percentage = 100 * (bookedSeats.size / (rows * seatsPerRow).toDouble())
        val rounded = String.format("%.2f", percentage)

        println("Number of purchased tickets: ${bookedSeats.size} ")
        println("Percentage: $rounded%")
        println("Current income: $$income")
        println("Total income: $$totalIncome")
    }
}

fun main() {
    //Size of Cinema
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readLine()!!.toInt()
    println()
    val cinema = Cinema(rows, seatsPerRow)

    // calculate total price
    totalIncome = if (rows * seatsPerRow <= 60) {
        (rows * seatsPerRow * 10)
    } else {
        val frontRows = rows / 2
        val backRows = ceil(rows / 2.0)
        (10 * frontRows * seatsPerRow + 8 * backRows * seatsPerRow).toInt()
    }

    // make bounds for rows and seats
    rowBound = rows
    seatBound = seatsPerRow

    // Show menu
    do {
        println("\n1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val input = readLine()!!.toInt()
        when(input) {
            1 -> cinema.showSeats()
            2 -> cinema.bookSeat()
            3 -> cinema.showStats()
            0 -> {}
            else -> println("Invalid input")
        }
    } while (input != 0)
}
