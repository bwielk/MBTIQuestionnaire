import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MbtiQuestionnaire {

	private static HashMap<MBTIParam, Integer> scores = new HashMap<>();
	private static HashMap<String, MBTIParam> questionToParam = new HashMap<>();
	private static List<String> questions = new ArrayList<>();
	private static int questionsToBeAnswered = 0;

	public static void main( String[] args ) {
		scoresSetUp();
		readQuestions();
		shuffleQuestions();
		setQuestionsCounter();
		runningQuestionnaire();
		calculateResults();
	}

	private static void runningQuestionnaire(){
		Scanner in = new Scanner(System.in);
		for(int i=0; i<=questions.size()-1; i++){
			System.out.println(String.format( "%s, %s ", i, questionsToBeAnswered ));
			String currentQuestion = questions.get( i );
			System.out.println(String.format("\n\nQUESTION %s/%s", questionsToBeAnswered, questions.size()));
			System.out.println(String.format("I am more likely to:\n\n%s\n\nAnswer Y for 'Yes' or N for 'No' ", currentQuestion));
			String answer = in.nextLine();
			processAnswer( answer, currentQuestion, in );
			questionsToBeAnswered-=1;
		}
		in.close();
	}

	private static void processAnswer(String answer, String question, Scanner in){
		MBTIParam p = questionToParam.get( question );
		if( answer.equalsIgnoreCase("y")){
			scores.put( p, scores.get( p ) + 1 );
		}else if( answer.equalsIgnoreCase("n")){
			scores.put( p, scores.get( p ));
		}else{
			System.out.println("Answer Y for 'Yes' or N for 'No'");
			answer = in.nextLine();
			processAnswer( answer, question, in );
		}
	}

	private static void scoresSetUp(){
		for(MBTIParam p : MBTIParam.values()){
			scores.put( p, 0 );
		}
	}

	private static void shuffleQuestions(){
		Collections.shuffle(questions);
	}

	private static void readQuestions(){
		for(MBTIParam p : MBTIParam.values()){
			readFile( p );
		}
	}

	private static void readFile(MBTIParam mbtiParam){
		ClassLoader classLoader = MbtiQuestionnaire.class.getClassLoader();
		InputStream is = classLoader.getResourceAsStream( mbtiParam.getPathToFile() );
		try( InputStreamReader streamReader = new InputStreamReader( is, StandardCharsets.UTF_8 );
				BufferedReader reader = new BufferedReader( streamReader )) {
			String line;
			while((line = reader.readLine()) != null) {
				questions.add( line );
				questionToParam.put( line, mbtiParam );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	private static void setQuestionsCounter(){
		questionsToBeAnswered = questions.size();
	}

	private static void calculateResults(){
		int resultE = scores.get( MBTIParam.E );
		int resultI = scores.get( MBTIParam.I );
		int resultS = scores.get( MBTIParam.S );
		int resultN = scores.get( MBTIParam.N );
		int resultT = scores.get( MBTIParam.T );
		int resultF = scores.get( MBTIParam.F );
		int resultJ = scores.get( MBTIParam.J );
		int resultP = scores.get( MBTIParam.P );
		System.out.println(String.format("Source of energy: E => %s and I => %s", resultE, resultI ));
		System.out.println(String.format("Taking in information: S => %s and N => %s", resultS, resultN));
		System.out.println(String.format("Decision making: T => %s F => %s", resultT, resultF));
		System.out.println(String.format("Lifestyle: J => %s P => %s", resultJ, resultP));
		String resultEI = resultE >= resultI ? "E" : "I";
		String resultSN = resultS >= resultN ? "S" : "N";
		String resultTF = resultT >= resultF ? "T" : "F";
		String resultJP = resultJ >= resultP ? "J" : "P";
		System.out.println(String.format("\n\nYOUR TYPE: %s%s%s%s", resultEI, resultSN, resultTF, resultJP));
	}
}
