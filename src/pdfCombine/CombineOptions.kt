package pdfCombine

/**
 * This class represents options parsed from command line arguments.
 *
 * @constructor Creates a new instance of [CombineOptions] class.
 */
class CombineOptions(val revert: Boolean, val first: String, val second: String, val output: String) {
}