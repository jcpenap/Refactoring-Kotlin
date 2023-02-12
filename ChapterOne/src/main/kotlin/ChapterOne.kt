fun main(args: Array<String>) {
    println("Hello world")
}

class Movie(val title: String, val typeCode: MoviesTypes, private var type: Type?) {

    fun getCharge(daysRented: Int): Double? = type?.getCharge(daysRented)

    fun getMovieType(): MoviesTypes? = type?.getCode()

    fun setMovieType(typeCode: MoviesTypes) {
        type = when (typeCode) {
            MoviesTypes.CHILDREN -> {
                ChildrenType()
            }
            MoviesTypes.REGULAR -> {
                RegularType()
            }
            MoviesTypes.NEW_RELEASE -> {
                NewReleaseType()
            }
        }
    }

    fun getFrequentRenterPoints(daysRented: Int): Int? = type?.getFrequentRenterPoints(daysRented)

}

class Rental(val movie:  Movie, private val daysRented: Int) {

    fun getCharge(): Double? = movie.getCharge(daysRented)

    fun getFrequentRenterPoints(): Int? = movie.getFrequentRenterPoints(daysRented)
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

    private fun getTotalFrequentRenterPoints(): Int? = rentals.sumOf { it.getFrequentRenterPoints() ?: 0 }

    private fun getTotalCharge(): Double = rentals.sumOf { it.getCharge() ?: 0.0  }
}

enum class MoviesTypes {
    CHILDREN, REGULAR, NEW_RELEASE
}

abstract class Type {
    abstract fun getCode() : MoviesTypes
    abstract fun getCharge(daysRented: Int) : Double
    abstract fun getFrequentRenterPoints(daysRented: Int): Int
}

class ChildrenType : Type() {
    override fun getCode() = MoviesTypes.CHILDREN
    override fun getCharge(daysRented: Int): Double {
        var result = 2.0
        if (daysRented > 2) {
            result += (daysRented - 2) * 1.5
        }
        return result
    }
    override fun getFrequentRenterPoints(daysRented: Int): Int =
        if ((getCode() == MoviesTypes.NEW_RELEASE) && daysRented > 1) 2 else 1
}

class RegularType : Type() {
    override fun getCode() = MoviesTypes.REGULAR
    override fun getCharge(daysRented: Int): Double {
        var result = 1.5
        if (daysRented > 3) {
            result += (daysRented - 3) * 1.5
        }
        return result
    }
    override fun getFrequentRenterPoints(daysRented: Int): Int =
        if ((getCode() == MoviesTypes.NEW_RELEASE) && daysRented > 1) 2 else 1

}

class NewReleaseType : Type() {
    override fun getCode() = MoviesTypes.NEW_RELEASE
    override fun getCharge(daysRented: Int): Double = daysRented * 1.5
    override fun getFrequentRenterPoints(daysRented: Int): Int =
        if ((getCode() == MoviesTypes.NEW_RELEASE) && daysRented > 1) 2 else 1
}