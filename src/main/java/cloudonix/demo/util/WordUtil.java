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
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import static cloudonix.demo.constant.LoggerConstant.FIND_LEXICAL;
import static cloudonix.demo.constant.LoggerConstant.FIND_LEXICAL_EXCEPTION;
import static cloudonix.demo.constant.LoggerConstant.TOTAL_CHARCATER_VALUE;
import static cloudonix.demo.constant.LoggerConstant.WRITE_WORD_TO_FILE;
import static cloudonix.demo.constant.LoggerConstant.WRITE_WORD_TO_FILE_EXCEPTION;
import static cloudonix.demo.constant.WordConstant.A;

@UtilityClass
@Slf4j
public class WordUtil {

	public int totalCharacterValue(String text) {

		log.debug(TOTAL_CHARCATER_VALUE, text);
		int S = 0;
		String textLowercase = text.toLowerCase();

		for (int i = 0; i < textLowercase.length(); i++) {
			S += text.charAt(i) - A + 1;
		}
		return S;
	}

	public BufferedWriter appendWordToFile(String fileName, String str) {

		log.debug(WRITE_WORD_TO_FILE, str, fileName);
		BufferedWriter out = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			if (Objects.isNull(br.readLine())) {
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
			log.error(WRITE_WORD_TO_FILE_EXCEPTION, e, str, fileName);
		}
		return out;
	}

	public String findLexical(String fileName, String str) {

		log.debug(FIND_LEXICAL, fileName, str);
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
			log.error(FIND_LEXICAL_EXCEPTION, e, fileName, str);
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
