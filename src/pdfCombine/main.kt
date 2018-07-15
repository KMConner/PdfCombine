package pdfCombine

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import kotlin.system.exitProcess

/**
 * Parses the specified options.
 *
 * @param args Command line arguments to parse.
 */
fun parseOptions(args: Array<String>): CombineOptions {
    val options = Options()
    options.addOption("r", "reverse", false, "Whether combine the second file with revert order.")
    options.addRequiredOption("f", "first", true, "The name of the first file to combine.")
    options.addRequiredOption("s", "second", true, "The name of the second file to combine.")
    val parser = DefaultParser()
    val result = parser.parse(options, args)
    val revert: Boolean = result.hasOption('r')
    val first: String = result.getOptionValue('f')
    val second: String = result.getOptionValue('s')
    return CombineOptions(revert, first, second)
}

fun main(args: Array<String>) {
    var options: CombineOptions
    try {
        options = parseOptions(arrayOf("--reverse", "-f", "first.pdf", "--second=second.pdf"))
    } catch (e: Exception) {
        System.err.println("Error: " + e.message)
        exitProcess(-1)
    }
    println(options.revert)
    println(options.first)
    println(options.second)
}