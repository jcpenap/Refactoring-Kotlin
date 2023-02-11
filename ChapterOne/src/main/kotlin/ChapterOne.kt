fun main(args: Array<String>) {
    println("Hello world")
}

class Movie(val title: String, val type: MoviesTypes) {
}

class Rental(val movie:  Movie, val daysRented: Int) {
    fun getCharge(): Double {
        var result = 0.0
        when (movie.type) {
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
}

class Customer(private val name: String, private val rentals: Array<Rental>) {
    fun statement(): String {
        var totalAmount: Double = 0.0
        var frequentRenterPoints: Int = 0
        var result = "Rental record for $name \n"
        rentals.map {
            var thisAmount: Double = it.getCharge()
            //add frequent renter points
            frequentRenterPoints ++
            //add bonus for a two day new release rental
            if ((it.movie.type == MoviesTypes.NEW_RELEASE) && it.daysRented > 1)
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
}

enum class MoviesTypes {
    CHILDREN, REGULAR, NEW_RELEASE
}