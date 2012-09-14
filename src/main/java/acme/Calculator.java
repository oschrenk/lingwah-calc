package acme;

import java.math.BigDecimal;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;

public class Calculator {
	static final Parser PARSER = CalculatorGrammar.INSTANCE.expr;

	public static BigDecimal parse(String expression) {
		ParseResults parseResults = ParseContext.parse(PARSER, expression);
		if (!parseResults.success())
			throw parseResults.getError();
		return CalculatorProcessor.process(parseResults);
	}
	
	public static void main(String[] args) {
		BigDecimal parse = parse("1 + 5 * 8");
		System.out.println(parse);
	}
}