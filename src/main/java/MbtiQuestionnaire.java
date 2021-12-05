import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MbtiQuestionnaire {

	private static HashMap<MBTIParam, Integer> scores = new HashMap<>();
	private static HashMap<String, MBTIParam> questionToParam = new HashMap<>();
	private static List<String> questions = new ArrayList<>();

	public static void main( String[] args ) {
		scoresSetUp();
		readQuestions();
		shuffleQuestions();
		System.out.println(scores);
		System.out.println(questions);
		System.out.println(questionToParam);
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
}
