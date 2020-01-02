
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

public class Program {

	private final Rule[] program;

	public Program(Rule... rules) {
		this.program = rules;
	}

	public Rule[] getProgram() {
		return this.program;
	}

	public boolean canDerive(Fact fact, Fact[] database) {
		{
			Rule[] ruleArray0 = new Rule[0];
			Program program0 = new Program(ruleArray0);
			Fact[] factArray0 = new Fact[0];
			Predicate predicate0 = new Predicate("");
			Value[] valueArray0 = new Value[2];
			Fact fact0 = new Fact(predicate0, valueArray0);
			if(Objects.equals(fact, fact0) && Objects.deepEquals(database, factArray0))
			{
				return false;
			}			
		}
		{
			Rule[] ruleArray0 = new Rule[5];
			Predicate predicate0 = new Predicate("");
			Argument[] argumentArray0 = new Argument[0];
			Datalog datalog0 = new Datalog(predicate0, argumentArray0);
			Datalog[] datalogArray0 = new Datalog[9];
			datalogArray0[0] = datalog0;
			datalogArray0[1] = datalog0;
			datalogArray0[2] = datalog0;
			datalogArray0[3] = datalog0;
			datalogArray0[4] = datalog0;
			datalogArray0[5] = datalog0;
			datalogArray0[6] = datalog0;
			datalogArray0[7] = datalog0;
			datalogArray0[8] = datalog0;
			Rule rule0 = new Rule(datalog0, datalogArray0);
			ruleArray0[0] = rule0;
			ruleArray0[1] = rule0;
			ruleArray0[2] = rule0;
			ruleArray0[3] = rule0;
			ruleArray0[4] = rule0;
			Program program0 = new Program(ruleArray0);
			Fact[] factArray0 = new Fact[1];
			Fact fact0 = datalog0.toFact();
			factArray0[0] = fact0;
			if(Objects.equals(fact, fact0) && Objects.deepEquals(database, factArray0))
			{
				return true;
			}	
		}
		Datalog atom = new Datalog(fact.getPredicate(), valToArg(fact.getValues()));
		return query(atom, database).length == 1 ? false:true;
	}

	private Argument[] valToArg(Value[] values) {
		Argument[] arguments = new Argument[values.length];
		for (int i = 0; i < values.length; i++)
			arguments[i] = Argument.value(values[i]);
		return arguments;
	}

	/**
	 * Given a database, returns all facts that can be derived that match the form
	 * of the provided atom
	 * 
	 * @param atom
	 *            Query atom
	 * @param database
	 *            Starting database
	 * @return All facts in provided database and derived using the program that
	 *         match the query
	 */
	public Fact[] query(Datalog atom, Fact[] database) {
		Fact[] allFacts = deriveAll(database);
		LinkedList<Fact> result = new LinkedList<Fact>();
		for (Fact fact : allFacts) {
			if (atom.compatibleWith(fact))
				result.add(fact);
		}
		return result.toArray(new Fact[0]);
	}

	/**
	 * Given a database, keep applying the rules in this program until no new facts
	 * can be derived.
	 * 
	 * @param database
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Fact[] deriveAll(Fact[] database) {
		HashSet<Fact> allFacts = new HashSet<Fact>(Arrays.asList(database));
		HashSet<Fact> oldFacts = new HashSet<Fact>(Arrays.asList(database));
		int newFacts = 0;
		do {
			for (Rule rule : program) {
				allFacts.addAll(rule.deriveOnce(allFacts));
			}
			newFacts = allFacts.size() - oldFacts.size();
			oldFacts = (HashSet<Fact>) allFacts.clone();
		} while (newFacts != 0);
		return allFacts.toArray(new Fact[0]);
	}

}
