package cloudonix.demo.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@UtilityClass
@Slf4j
public class WordUtil {

	public String totalCharacterValue(String text) {

		int S = 0;
		String textLowercase = text.toLowerCase();

		for (int i = 0; i < textLowercase.length(); i++) {
			S += text.charAt(i) - 'a' + 1;
		}
		return Integer.toString(S);
	}

	public BufferedWriter appendWordToFile(String fileName, String str) {

		BufferedWriter out = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if (br.readLine() == null) {
				FileWriter fileWriter = new FileWriter(fileName);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				printWriter.print(str);
				printWriter.close();
				return null;
			} else {
				out = new BufferedWriter(new FileWriter(fileName, true));
				out.write(str);
				out.close();
			}
		} catch (IOException e) {
			log.error("exception occurred" + e);
		}
		return out;
	}

	public String findLexical(String fileName, String str) {

		Set<String> set = new TreeSet<>();
		File file = new File(fileName);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				set.add(line);
			}

			br.close();
			fr.close();
		} catch (IOException e) {
			log.error("exception occurred" + e);
		}

		List<String> list = new ArrayList<>(set);
		var position = list.indexOf(str.toLowerCase());
		if (list.size() == 1) {
			return list.get(0);
		}
		if (position == list.size() - 1) {
			return list.get(position - 1);
		} else {
			if (position == 0) {
				return list.get(position + 1);
			}
		}
		return list.get(position - 1);
	}
}
