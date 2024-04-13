package potager.btalva.mng;

public class MotherInputDataPotagerBernard {
	
	protected static final String ESPACE_ENTRE_DATA = "    ";
	
	
	
	
	protected void appendIntegerValue(StringBuilder pSb, String name, 
			int val, String suffix) {
		pSb.append(name);
		pSb.append(" = ");
		pSb.append(String.valueOf(val));
		if (suffix.length() > 0) {
			pSb.append(suffix);
		}
		pSb.append(System.lineSeparator());
	}
	
	

	protected void appendStringValue(StringBuilder pSb, String val) {
		pSb.append(val);
		
		pSb.append(System.lineSeparator());
	}
	
	protected void appendStringValueSansFinDeLigne(StringBuilder pSb, String val) {
		pSb.append(val);
		
		
	}
	
	protected void appendEspace(StringBuilder pSb) {
		pSb.append(ESPACE_ENTRE_DATA);
		
		
	}

}
