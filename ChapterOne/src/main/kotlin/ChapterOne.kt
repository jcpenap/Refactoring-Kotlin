fun main(args: Array<String>) {
    println("Hello world")
}

class Movie(val title: String, private val type: MoviesTypes) {
    fun getCharge(daysRented: Int): Double {
        var result = 0.0
        when (type) {
            MoviesTypes.CHILDREN -> {
                result += 2
                if (daysRented > 2) {
                    result += (daysRented - 2) * 1.5
                }
            }
            MoviesTypes.NEW_RELEASE -> {
                result += daysRented * 1.5
            }
            MoviesTypes.REGULAR -> {
                result += 1.5
                if (daysRented > 3) {
                    result += (daysRented - 3) * 1.5
                }
            }
        }
        return result
    }

    fun getFrequentRenterPoints(daysRented: Int): Int =
        if ((type == MoviesTypes.NEW_RELEASE) && daysRented > 1) 2 else 1

}

class Rental(val movie:  Movie, private val daysRented: Int) {

    fun getCharge(): Double = movie.getCharge(daysRented)

    fun getFrequentRenterPoints(): Int = movie.getFrequentRenterPoints(daysRented)
}

class Customer(private val name: String, private val rentals: Array<Rental>) {
    fun statement(): String {
        var result = "Rental record for $name \n"
        rentals.map {
            //show figures for this rental
            result += "\t ${it.movie.title} \t ${it.getCharge()} \n"
        }

        //add footer lines
        result += "Amount owed is ${getTotalCharge()} \n"
        result += "You earned ${getTotalFrequentRenterPoints()} frequent renter points"
        return result
    }

    private fun getTotalFrequentRenterPoints(): Int = rentals.sumOf { it.getFrequentRenterPoints() }

    private fun getTotalCharge(): Double = rentals.sumOf { it.getCharge() }
}

enum class MoviesTypes {
    CHILDREN, REGULAR, NEW_RELEASE
}