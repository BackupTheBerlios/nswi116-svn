package mi_swe.jena.builtin;

import java.util.Calendar;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.reasoner.rulesys.RuleContext;
import com.hp.hpl.jena.reasoner.rulesys.builtins.BaseBuiltin;

public class AddDaysToDateTime extends BaseBuiltin {
	@Override // Name used in rules
	public String getName() { return "addDaysToDateTime"; }
	@Override // Body call implementation
	public boolean bodyCall(Node[] args, int length, RuleContext context) {
		// Second argument contains the old date 
		XSDDateTime orig_date_val = (XSDDateTime) args[1].getLiteralValue();
		// Convert the value to Calendar instance 
		Calendar calendar = orig_date_val.asCalendar();
		// Add number of days (third argument)
		calendar.add(Calendar.DAY_OF_MONTH, (Integer) args[2].getLiteralValue());
		// Create a XSDDateTime value from the calendar  
		XSDDateTime new_date_val = new XSDDateTime(calendar);
		// Create new node from the value 		
		Node new_date_literal_node =                 //lang = null 
			Node.createUncachedLiteral(new_date_val, null, XSDDatatype.XSDdateTime);
		// bind the variable of the first argument with the new date node
		context.getEnv().bind(args[0], new_date_literal_node);
		return true;
	}
}