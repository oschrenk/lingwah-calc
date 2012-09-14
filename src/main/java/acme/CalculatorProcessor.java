package acme;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.googlecode.lingwah.AbstractProcessor;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.annotations.Processes;

@Processes(CalculatorGrammar.class)
public class CalculatorProcessor extends AbstractProcessor {

	static final CalculatorGrammar grammar = CalculatorGrammar.INSTANCE;

	public void completeAddition(Match addition) {
		List<Match> children = addition.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.add(right));
	}

	public void completeSubtraction(Match subtraction) {
		List<Match> children = subtraction.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.subtract(right));
	}

	public void completeMultiplication(Match multiplication) {
		List<Match> children = multiplication.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.multiply(right));
	}

	public void completeDivision(Match division) {
		List<Match> children = division.getChildrenByType(grammar.expr);
		BigDecimal left = getResult(children.get(0));
		BigDecimal right = getResult(children.get(1));
		putResult(left.divide(right, 28, RoundingMode.HALF_UP));
	}

	public void completeGroup(Match group) {
		putResult(getResult(group.getChildByType(grammar.expr)));
	}

	public void completeDecimal(Match decimal) {
		putResult(new BigDecimal(decimal.getText()));
	}

	public void completeExpr(Match expr) {
		putResult(getResult(expr.getFirstChild()));
	}

	public static BigDecimal process(ParseResults results) {
		CalculatorProcessor processor = new CalculatorProcessor();
		results.getLongestMatch().accept(processor);
		return processor.getResult(results.getLongestMatch());
	}

}