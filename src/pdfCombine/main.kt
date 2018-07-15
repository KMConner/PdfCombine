package pdfCombine

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import kotlin.system.exitProcess
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.File

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
    options.addRequiredOption("o", "out", true, "The name of the output file.")
    val parser = DefaultParser()
    val result = parser.parse(options, args)
    val revert: Boolean = result.hasOption('r')
    val first: String = result.getOptionValue('f')
    val second: String = result.getOptionValue('s')
    val output: String = result.getOptionValue('o')
    return CombineOptions(revert, first, second, output)
}

fun combine(options: CombineOptions) {
    val firstDocument: PDDocument
    val secondDocument: PDDocument
    try {
        firstDocument = PDDocument.load(File(options.first))
        secondDocument = PDDocument.load(File(options.second))
    } catch (e: Exception) {
        println(e)
        return
    }
    var f = 0
    var s = 0
    val out = PDDocument()
    while (f < firstDocument.numberOfPages && s < secondDocument.numberOfPages) {
        if (f > s) {
            out.addPage(firstDocument.getPage(f))
            f++
        } else {
            out.addPage(secondDocument.getPage(if (options.revert) secondDocument.numberOfPages - s - 1 else s))
            s++
        }
    }

    while (f < firstDocument.numberOfPages) {
        out.addPage(firstDocument.getPage(f))
        f++
    }

    while (s < secondDocument.numberOfPages) {
        out.addPage(secondDocument.getPage(if (options.revert) secondDocument.numberOfPages - s - 1 else s))
        s++
    }
    out.save(options.output)
}

fun main(args: Array<String>) {
    val options: CombineOptions
    try {
        options = parseOptions(args)
    } catch (e: Exception) {
        println("Error: " + e.message)
        exitProcess(-1)
    }
    combine(options)
}