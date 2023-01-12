package cloudonix.demo.util;

import cloudonix.demo.service.RestService;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import static cloudonix.demo.constant.WordConstant.EMPTY_STRING;

@UtilityClass
public class WordUtil {

	private static final Logger LOG = LogManager.getLogger(RestService.class);

	public int totalCharacterValue(String text) {

		LOG.debug(TOTAL_CHARCATER_VALUE, text);
		int S = 0;
		String textLowercase = text.toLowerCase();

		for (int i = 0; i < textLowercase.length(); i++) {
			S += text.charAt(i) - A + 1;
		}
		return S;
	}

	public BufferedWriter appendWordToFile(String fileName, String str) {

		LOG.debug(WRITE_WORD_TO_FILE, str, fileName);
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
			LOG.error(WRITE_WORD_TO_FILE_EXCEPTION, e, str, fileName);
		}
		return out;
	}

	public String findLexical(String fileName, String str) {

		LOG.debug(FIND_LEXICAL, fileName, str);
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
			LOG.error(FIND_LEXICAL_EXCEPTION, e, fileName, str);
		}

		List<String> list = new ArrayList<>(set);
		var position = list.indexOf(str.toLowerCase());

		return list.stream()
				.map(word -> {
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
				})
				.findFirst()
				.orElse(EMPTY_STRING);

	}
}
