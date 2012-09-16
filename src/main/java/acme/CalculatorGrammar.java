package acme;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

/**
 * The Class CalculatorGrammar.
 */
public class CalculatorGrammar extends Grammar {

	/** The inline_comment. */
	public final Parser inline_comment = seq(str("/*"), zeroOrMore(anyChar()),
			str("*/"));

	/** The ws. */
	public final Parser ws = oneOrMore(cho(oneOrMore(regex("[ \t\n\f\r]")),
			inline_comment));

	/** The digit. */
	public final Parser digit = regex("[0-9]");

	/** The number. */
	public final Parser number = oneOrMore(digit);

	/** The decimal. */
	public final Parser decimal = seq(opt(str('-')),
			seq(number, opt(seq(str('.'), number))));

	/** The expr. */
	public final ParserReference expr = ref();

	/** The addition. */
	public final Parser addition = seq(expr, str('+'), expr).separatedBy(
			opt(ws));

	/** The subtraction. */
	public final Parser subtraction = seq(expr, str('-'), expr).separatedBy(
			opt(ws));

	/** The multiplication. */
	public final Parser multiplication = seq(expr, str('*'), expr).separatedBy(
			opt(ws));

	/** The division. */
	public final Parser division = seq(expr, str('/'), expr).separatedBy(
			opt(ws));

	/** The group. */
	public final Parser group = seq(str('('), expr, str(')')).separatedBy(
			opt(ws));
	{
		expr.set(cho(decimal, addition, subtraction, multiplication,
				division, group));
	}

	/**
	 * The Constructor.
	 */
	private CalculatorGrammar() {
		init();
	}

	/** The Constant INSTANCE. */
	public static final CalculatorGrammar INSTANCE = new CalculatorGrammar();
}