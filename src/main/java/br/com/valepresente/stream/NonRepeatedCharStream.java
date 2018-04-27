package br.com.valepresente.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Implementação de <code>Stream</code>.
 * 
 * <p>A partir de uma String, mantém uma estrutura com <code>0</code> ou <code>n</code> caracteres que <strong>não se repetem</strong>
 * na String original.</p>
 * 
 * <p>Caso exista mais de um caracter que não se repetem, eles serão organizados respeitando a ordem da String original.</p>
 * 
 * <p><strong>Importante:</strong></strong> uma <code>IllegalArgumentException</code> será lançada pelo construtor na tentativa
 * de criar uma instância de <code>NonRepeatedCharStream</code> com uma string vazia ou nula.</p>
 *  
 * 
 */
public class NonRepeatedCharStream implements Stream {

	private Iterator<Character> itNonRepeated;
	
	public NonRepeatedCharStream(String content) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(content), 
				"Informe o conteúdo corretamente!");
		Map<Character, Integer> caracteresQtde = new LinkedHashMap<>();
		for (char c: content.toCharArray()) {
			int qtd = caracteresQtde.containsKey(c) ? caracteresQtde.get(c) + 1 : 1;
			caracteresQtde.put(c, Integer.valueOf(qtd));
		}
		itNonRepeated = caracteresQtde
				.keySet()
				.stream()
				.filter(c -> caracteresQtde.get(c) == 1)
				.iterator();
	}
	
	@Override
	public char getNext() {
		return itNonRepeated.next();
	}
	
	@Override
	public boolean hasNext() {
		return itNonRepeated.hasNext();
	}
		
}
