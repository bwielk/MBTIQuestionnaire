public enum MBTIParam {

	E("E", "E.txt"),
	F("F", "F.txt"),
	I("I", "I.txt"),
	J("J", "J.txt"),
	N("N", "N.txt"),
	P("P", "P.txt"),
	S("S", "S.txt"),
	T("T", "T.txt");

	String parameter;
	String pathToFile;

	MBTIParam(String parameter, String pathToFile){
		this.parameter = parameter;
		this.pathToFile = pathToFile;
	}

	public String getParameter() {
		return parameter;
	}

	public String getPathToFile() {
		return pathToFile;
	}
}
