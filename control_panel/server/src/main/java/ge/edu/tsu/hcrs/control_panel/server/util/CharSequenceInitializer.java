package ge.edu.tsu.hcrs.control_panel.server.util;

import ge.edu.tsu.hcrs.control_panel.model.exception.ControlPanelException;
import ge.edu.tsu.hcrs.control_panel.model.network.CharSequence;

import java.util.HashMap;
import java.util.Map;

public class CharSequenceInitializer {

	private static final char sequenceStartSymbol = '[';

	private static final char divisorSymbol = '-';

	private static final char sequenceEndSymbol = ']';

	private static final char transferSymbol = '/';

	public static void initializeCharSequence(CharSequence charSequence) throws ControlPanelException {
		if (charSequence == null || charSequence.getCharactersRegex() == null || charSequence.getCharactersRegex().isEmpty()) {
			throw new ControlPanelException("Cant initialize CharSequence!");
		}
		try {
			int index = 0;
			Map<Character, Integer> charToIndexMap = new HashMap<>();
			Map<Integer, Character> indexToCharMap = new HashMap<>();
			String regex = charSequence.getCharactersRegex();
			for (int i = 0; i < regex.length(); i++) {
				char c = regex.charAt(i);
				if (c == transferSymbol) {
					i++;
					c = regex.charAt(i);
					charToIndexMap.put(c, index);
					indexToCharMap.put(index, c);
					index++;
				} else if (c == sequenceStartSymbol) {
					if (regex.charAt(i + 2) != divisorSymbol || regex.charAt(i + 4) != sequenceEndSymbol) {
						throw new ControlPanelException("Not supported regex!");
					}
					char first = regex.charAt(i + 1);
					char last = regex.charAt(i + 3);
					for (int j = first; j <= last; j++) {
						charToIndexMap.put((char) j, index);
						indexToCharMap.put(index, (char) j);
						index++;
					}
				} else {
					charToIndexMap.put(c, index);
					indexToCharMap.put(index, c);
					index++;
				}
			}
			charSequence.setCharToIndexMap(charToIndexMap);
			charSequence.setIndexToCharMap(indexToCharMap);
			charSequence.setNumberOfChars(charToIndexMap.keySet().size());
		} catch (Exception ex) {
			throw new ControlPanelException("Not supported regex!");
		}
	}
}
