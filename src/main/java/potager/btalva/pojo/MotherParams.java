package potager.btalva.pojo;

public class MotherParams {
	
	protected void appendParam(StringBuilder sb, String nomParam, int valParam) {
		sb.append(System.lineSeparator());
		sb.append(nomParam);
		sb.append("=");
		sb.append(valParam);
		
		
	}

}
