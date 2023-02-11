fun main(args: Array<String>) {
    println("Hello world")
}

class Movie(val title: String, val movieType: MoviesType) {
}

class Rental(val movie:  Movie, val daysRented: Int) {

}

class Customer(private val name: String, private val rentals: Array<Rental>) {
    fun statement(): String {
        var totalAmount: Double = 0.0
        var frequentRenterPoints: Int = 0
        var result = "Rental record for $name \n"
        rentals.map {
            var thisAmount: Double = amountFor(it)
            //add frequent renter points
            frequentRenterPoints ++
            //add bonus for a two day new release rental
            if ((it.movie.movieType == MoviesType.NEW_RELEASE) && it.daysRented > 1)
                frequentRenterPoints ++

            //show figures for this rental
            result += "\t ${it.movie.title} \t $thisAmount \n"
            totalAmount += thisAmount
        }

        //add footer lines
        result += "Amount owed is $totalAmount \n"
        result += "You earned $frequentRenterPoints frequent renter points"
        return result
    }

    private fun amountFor(it: Rental): Double {
        var thisAmount = 0.0
        when (it.movie.movieType) {
            MoviesType.CHILDREN -> {
                thisAmount += 2
                if (it.daysRented > 2) {
                    thisAmount += (it.daysRented - 2) * 1.5
                }
            }
            MoviesType.NEW_RELEASE -> {
                thisAmount += it.daysRented * 1.5
            }
            MoviesType.REGULAR -> {
                thisAmount += 1.5
                if (it.daysRented > 3) {
                    thisAmount += (it.daysRented - 3) * 1.5
                }
            }
        }
        return thisAmount
    }
}

enum class MoviesType {
    CHILDREN, REGULAR, NEW_RELEASE
}